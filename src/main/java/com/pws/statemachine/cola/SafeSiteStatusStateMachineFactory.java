package com.pws.statemachine.cola;

import com.alibaba.cola.statemachine.StateMachine;
import com.alibaba.cola.statemachine.StateMachineFactory;
import com.alibaba.cola.statemachine.builder.StateMachineBuilder;
import com.alibaba.cola.statemachine.builder.StateMachineBuilderFactory;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Maps;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class SafeSiteStatusStateMachineFactory {

    private static final Map<String, StateMachineFlowConfig> flowConfigMap = Maps.newHashMap();

    @PostConstruct
    public static void init() {
        try {
            List<StateMachineFlowConfig> flowConfigs = JSONObject.parseObject(loadConfig(), new TypeReference<List<StateMachineFlowConfig>>() {});

            flowConfigs.forEach(flowConfig -> {
                String flowId = flowConfig.getFlowId();
                List<StateMachineFlowConfig.TransitionConfig> transitions = flowConfig.getTransitions();

                // build state machine
                buildStateMachine(transitions, flowId);

                // persist flow config cache
                flowConfigMap.put(flowId, flowConfig);
            });

        } catch (Exception e) {
            log.error("【状态机】初始化失败", e);
            throw e;
        }
    }

    public static SiteStatusState getInitialState(String flowId) {
        StateMachineFlowConfig flowConfig = flowConfigMap.get(flowId);

        if (flowConfig == null) {
            rebuild(flowId);
            throw new IllegalStateException("【状态机】不存在：machineId=" + flowId);
        }
        return SiteStatusState.valueOf(flowConfig.getInitial());
    }


    public static SafeStateMachineExecutor<SiteStatusState, SiteStatusEvent, Map<String, String>> safeGet(String flowId) {

        StateMachine<SiteStatusState, SiteStatusEvent, Map<String, String>> stateMachine = null;

        try {
            stateMachine = StateMachineFactory.get(flowId);
        } catch (Exception e) {
            log.error("【状态机】获取失败：machineId=" + flowId, e);
            rebuild(flowId);
        }

        if (stateMachine == null) {
            throw new IllegalStateException("【状态机】不存在：machineId=" + flowId);
        }
        return new SafeStateMachineExecutor<>(stateMachine);
    }

    public static void buildStateMachine(List<StateMachineFlowConfig.TransitionConfig> transitionConfigs, String flowId) {
        StateMachineBuilder<SiteStatusState, SiteStatusEvent, Map<String, String>> builder = StateMachineBuilderFactory.create();

        for (StateMachineFlowConfig.TransitionConfig tstCfg : transitionConfigs) {
            builder.externalTransition()
                    .from(SiteStatusState.valueOf(tstCfg.getFrom()))
                    .to(SiteStatusState.valueOf(tstCfg.getTo()))
                    .on(SiteStatusEvent.valueOf(tstCfg.getEvent()))
                    .when(context -> true)
                    .perform((from1, to1, event1, context) ->
                            log.info("perform action, from = {}, to = {}, event = {}", from1.getName(), to1.getName(), event1.getName()));
        }

        builder.build(flowId);
        log.info("COLA StateMachineFactory register flow success! flowId = {}", flowId);
    }

    public synchronized static void rebuild(String flowId) {
        try {
            List<StateMachineFlowConfig> flowConfigs = JSONObject.parseObject(loadConfig(), new TypeReference<List<StateMachineFlowConfig>>() {});

            flowConfigs.forEach(flowConfig -> {
                if (StringUtils.equalsIgnoreCase(flowConfig.getFlowId(), flowId)) {
                    buildStateMachine(flowConfig.getTransitions(), flowId);
                    flowConfigMap.put(flowId, flowConfig);
                }
            });

        } catch (Exception e) {
            log.error("【状态机】重新初始化失败：targetFlowId=" + flowId, e);
        }
    }

    public synchronized static void rebuild() {
        try {
            init();
        } catch (Exception e) {
            log.error("【状态机】重新初始化失败", e);
        }
    }


    private static String loadConfig() {
        return "[\n" +
                "    {\n" +
                "        \"flowId\": \"SITE_STATE_FLOW_COMMON\",\n" +
                "        \"initial\": \"STAY_SAMPLING\",\n" +
                "        \"endStates\": [\n" +
                "            \"COMPLETE\"\n" +
                "        ],\n" +
                "        \"states\": [\n" +
                "            {\n" +
                "                \"id\": \"STAY_SAMPLING\",\n" +
                "                \"name\": \"待扦样\",\n" +
                "                \"code\": \"10\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": \"STAY_QUALITY_TESTING\",\n" +
                "                \"name\": \"待质检\",\n" +
                "                \"code\": \"20\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": \"STAY_WEIGH\",\n" +
                "                \"name\": \"待过磅\",\n" +
                "                \"code\": \"30\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": \"STAY_TWO_WEIGH\",\n" +
                "                \"name\": \"待二次过磅\",\n" +
                "                \"code\": \"40\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": \"STAY_SETTLEMENT\",\n" +
                "                \"name\": \"待结算\",\n" +
                "                \"code\": \"50\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": \"COMPLETE\",\n" +
                "                \"name\": \"已完成\",\n" +
                "                \"code\": \"60\"\n" +
                "            }\n" +
                "        ],\n" +
                "        \"events\": [\n" +
                "            {\n" +
                "                \"id\": \"DO_SAMPLE\",\n" +
                "                \"name\": \"扦样\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": \"DO_QUALITY_TEST\",\n" +
                "                \"name\": \"质检\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": \"DO_WEIGHT\",\n" +
                "                \"name\": \"首次过磅\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": \"DO_TWO_WEIGHT\",\n" +
                "                \"name\": \"二次过磅\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": \"DO_SETTLEMENT\",\n" +
                "                \"name\": \"结算\"\n" +
                "            }\n" +
                "        ],\n" +
                "        \"transitions\": [\n" +
                "            {\n" +
                "                \"from\": \"STAY_SAMPLING\",\n" +
                "                \"to\": \"STAY_QUALITY_TESTING\",\n" +
                "                \"event\": \"DO_SAMPLE\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"from\": \"STAY_QUALITY_TESTING\",\n" +
                "                \"to\": \"STAY_WEIGH\",\n" +
                "                \"event\": \"DO_QUALITY_TEST\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"from\": \"STAY_WEIGH\",\n" +
                "                \"to\": \"STAY_TWO_WEIGH\",\n" +
                "                \"event\": \"DO_WEIGHT\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"from\": \"STAY_TWO_WEIGH\",\n" +
                "                \"to\": \"STAY_SETTLEMENT\",\n" +
                "                \"event\": \"DO_TWO_WEIGHT\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"from\": \"STAY_SETTLEMENT\",\n" +
                "                \"to\": \"COMPLETE\",\n" +
                "                \"event\": \"DO_SETTLEMENT\"\n" +
                "            }\n" +
                "        ]\n" +
                "    },\n" +
                "    {\n" +
                "        \"flowId\": \"SITE_STATE_FLOW_WEIGHT_FIRST\",\n" +
                "        \"initial\": \"STAY_SAMPLING\",\n" +
                "        \"endStates\": [\n" +
                "            \"COMPLETE\"\n" +
                "        ],\n" +
                "        \"states\": [\n" +
                "            {\n" +
                "                \"id\": \"STAY_SAMPLING\",\n" +
                "                \"name\": \"待扦样\",\n" +
                "                \"code\": \"10\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": \"STAY_QUALITY_TESTING\",\n" +
                "                \"name\": \"待质检\",\n" +
                "                \"code\": \"20\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": \"STAY_WEIGH\",\n" +
                "                \"name\": \"待过磅\",\n" +
                "                \"code\": \"30\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": \"STAY_TWO_WEIGH\",\n" +
                "                \"name\": \"待二次过磅\",\n" +
                "                \"code\": \"40\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": \"STAY_SETTLEMENT\",\n" +
                "                \"name\": \"待结算\",\n" +
                "                \"code\": \"50\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": \"COMPLETE\",\n" +
                "                \"name\": \"已完成\",\n" +
                "                \"code\": \"60\"\n" +
                "            }\n" +
                "        ],\n" +
                "        \"events\": [\n" +
                "            {\n" +
                "                \"id\": \"DO_SAMPLE\",\n" +
                "                \"name\": \"扦样\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": \"DO_QUALITY_TEST\",\n" +
                "                \"name\": \"质检\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": \"DO_WEIGHT\",\n" +
                "                \"name\": \"首次过磅\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": \"DO_TWO_WEIGHT\",\n" +
                "                \"name\": \"二次过磅\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": \"DO_SETTLEMENT\",\n" +
                "                \"name\": \"结算\"\n" +
                "            }\n" +
                "        ],\n" +
                "        \"transitions\": [\n" +
                "            {\n" +
                "                \"from\": \"STAY_SAMPLING\",\n" +
                "                \"to\": \"STAY_WEIGH\",\n" +
                "                \"event\": \"DO_SAMPLE\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"from\": \"STAY_WEIGH\",\n" +
                "                \"to\": \"STAY_QUALITY_TESTING\",\n" +
                "                \"event\": \"DO_WEIGHT\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"from\": \"STAY_QUALITY_TESTING\",\n" +
                "                \"to\": \"STAY_TWO_WEIGH\",\n" +
                "                \"event\": \"DO_QUALITY_TEST\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"from\": \"STAY_TWO_WEIGH\",\n" +
                "                \"to\": \"STAY_SETTLEMENT\",\n" +
                "                \"event\": \"DO_TWO_WEIGHT\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"from\": \"STAY_SETTLEMENT\",\n" +
                "                \"to\": \"COMPLETE\",\n" +
                "                \"event\": \"DO_SETTLEMENT\"\n" +
                "            }\n" +
                "        ]\n" +
                "    }\n" +
                "]";
    }
}
