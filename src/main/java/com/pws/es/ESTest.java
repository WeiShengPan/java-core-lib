package com.pws.es;

import co.elastic.clients.elasticsearch._types.FieldSort;
import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.aggregations.*;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.json.JsonData;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchAggregations;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.Query;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class ESTest {

    @Resource
    private ElasticsearchOperations elasticsearchOperations;

    public void test() {
        Query query = getQuery();

        SearchHits<ESRecord> hits = elasticsearchOperations.search(query, ESRecord.class);

        analyzeRst(hits);
    }

    private Query getQuery() {
        NativeQueryBuilder builder = NativeQuery.builder();

        // build query
        BoolQuery.Builder bool = buildBoolQuery();
        builder.withQuery(bool.build()._toQuery());

        // build aggregation
        Aggregation agg = buildAggregation();
        builder.withAggregation("sum_ele_today_bucket", agg);

        // sort
        builder.withSort(SortOptions.of(sort -> sort.field(FieldSort.of(fieldSort -> fieldSort.field("createTime").order(SortOrder.Desc)))));

        Query query = builder.build();
        query.setTrackTotalHits(true); // 是否返回实际数据总量
        return query;
    }

    private BoolQuery.Builder buildBoolQuery() {
        BoolQuery.Builder bool = QueryBuilders.bool();

//        bool.must(QueryBuilders.wildcard(m -> m.field("sn").value("*" + param.getSn() + "*")));

        List<String> sns = Lists.newArrayList("R6I4403J2332C25523", "R6I4503J2351C38145");
        List<FieldValue> snFieldValues = sns.stream().map(e -> FieldValue.of(e.toLowerCase(Locale.ROOT))).distinct().collect(Collectors.toList());
        bool.must(QueryBuilders.terms(m -> m.field("sn").terms(t -> t.value(snFieldValues))));

        bool.must(QueryBuilders.range(m -> m.field("createTime")
                .gte(JsonData.of(Timestamp.valueOf(LocalDateTime.of(2024, 9, 2, 14, 35))))
                .lte(JsonData.of(Timestamp.valueOf(LocalDateTime.of(2024, 9, 2, 15, 5))))));

        return bool;
    }

    private Aggregation buildAggregation() {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("sum", "sum_ele_today");
        paramMap.put("avg", "avg_ele_today");
        Aggregation scriptAgg = Aggregation.of(b1 ->
                b1.bucketScript(b5 ->
                        b5.bucketsPath(b2 -> b2.dict(paramMap))
                                .script(b3 -> b3.inline(b4 -> b4.source("params.sum - params.avg")))));

        Aggregation sumAggregation = Aggregation.of(a -> a
                .sum(s -> s.field("eleToday"))
        );
        Aggregation avgAggregation = Aggregation.of(a -> a
                .avg(s -> s.field("eleToday"))
        );
        Aggregation statsAggregation = Aggregation.of(a -> a
                .extendedStats(ExtendedStatsAggregation.of(s -> s.field("eleToday")))
        );

        return Aggregation.of(bd -> bd.terms(TermsAggregation.of(t -> t
                        .field("sn.keyword")
                ))
                .aggregations("sum_ele_today", sumAggregation)
                .aggregations("avg_ele_today", avgAggregation)
                .aggregations("stat_ele_today", statsAggregation)
                .aggregations("script_ele_today", scriptAgg));
    }


    private void analyzeRst(SearchHits<ESRecord> hits) {
        // analyze query result set
        List<ESRecord> records = hits.get().map(SearchHit::getContent).collect(Collectors.toList());

        // analyze aggregation
        ElasticsearchAggregations egs = (ElasticsearchAggregations) hits.getAggregations();
        StringTermsAggregate stAgg = egs.get("sum_ele_today_bucket").aggregation().getAggregate().sterms();
        List<StringTermsBucket> stBuckets = stAgg.buckets().array();
        for (StringTermsBucket bucket : stBuckets) {
            Map<String, Aggregate> aggMap = bucket.aggregations();
            // sum
            Aggregate sumAgg = aggMap.get("sum_ele_today");
            BigDecimal sum = BigDecimal.valueOf(sumAgg.sum().value());
            // avg
            Aggregate agvAgg = aggMap.get("avg_ele_today");
            BigDecimal avg = BigDecimal.valueOf(agvAgg.avg().value());
            // extend_stats
            Aggregate statAgg = aggMap.get("stat_ele_today");
            ExtendedStatsAggregate extendedStats = statAgg.extendedStats();
            BigDecimal variancePopulation = BigDecimal.valueOf(extendedStats.variancePopulation());
            BigDecimal stdDeviationPopulation = BigDecimal.valueOf(extendedStats.stdDeviationPopulation());
            // script result
            Aggregate scriptAgg = aggMap.get("script_ele_today");
            BigDecimal scriptRst = BigDecimal.valueOf(scriptAgg.simpleValue().value());
            log.info("sum: {}, avg: {}, variancePopulation: {}, stdDeviationPopulation: {}, scriptResult: {}", sum, avg, variancePopulation, stdDeviationPopulation, scriptRst);
        }
    }

}
