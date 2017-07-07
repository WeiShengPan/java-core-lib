package guavatest;

import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import com.google.common.collect.TreeRangeMap;

import java.util.Map;

/**
 * RangeMap代表了非连续非空的range对应的集合。不像RangeSet，RangeMap不会合并相邻的映射，甚至相邻的range对应的是相同的值。
 *
 * @author panws
 * @since 2017-07-05
 */
public class RangeMapTest {

	public static void main(String[] args) {

		RangeMap<Integer, String> rangeMap = TreeRangeMap.create();
		rangeMap.put(Range.closed(1, 10), "foo");
		System.out.println("rangeMap:" + rangeMap);
		rangeMap.put(Range.open(3, 6), "bar");
		System.out.println("rangeMap:" + rangeMap);
		rangeMap.put(Range.open(10, 20), "foo");
		System.out.println("rangeMap:" + rangeMap);
		rangeMap.remove(Range.closed(5, 11));
		System.out.println("rangeMap:" + rangeMap);

		/**
		 * RangeMap提供了两种视图：
		 *　　asMapOfRanges():返回Map<Range<K>, V>类型的视图。这个操作可以被用作迭代操作。
		 *　　subRangeMap(Range<K>)提供给定Range的交集。这个操作可以推广到传统的headMap, subMap, 和tailMap。
		 */
		for (Map.Entry<Range<Integer>, String> entry : rangeMap.asMapOfRanges().entrySet()) {
			System.out.println(entry);
		}

		System.out.println(rangeMap.subRangeMap(Range.closed(12,14)));
		System.out.println(rangeMap.subRangeMap(Range.closed(7,14)));
		System.out.println(rangeMap.subRangeMap(Range.closed(4,14)));
	}
}
