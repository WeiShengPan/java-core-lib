package guava.test;

import com.google.common.collect.Range;
import com.google.common.collect.RangeSet;
import com.google.common.collect.TreeRangeSet;
import javafeatures.util.PrintUtil;

/**
 * RangeSet用来处理一系列不连续，非空的range。当添加一个range到一个RangeSet之后，任何有连续的range将被自动合并，而空的range将被自动去除
 *
 * @author panws
 * @since 2017-07-04
 */
public class RangeSetTest {

	public static void main(String[] args) {
		RangeSet<Integer> rangeSet1 = TreeRangeSet.create();
		rangeSet1.add(Range.closed(1, 10));
		PrintUtil.println("rangeSet1:" + rangeSet1);
		rangeSet1.add(Range.closedOpen(11, 15));
		PrintUtil.println("rangeSet1:" + rangeSet1);
		rangeSet1.add(Range.open(15, 20));
		PrintUtil.println("rangeSet1:" + rangeSet1);
		rangeSet1.add(Range.openClosed(0, 0));
		PrintUtil.println("rangeSet1:" + rangeSet1);
		rangeSet1.remove(Range.open(5, 10));
		PrintUtil.println("rangeSet1:" + rangeSet1);

		RangeSet<Integer> rangeSet2 = TreeRangeSet.create();
		rangeSet2.add(Range.open(1, 5));
		PrintUtil.println("rangeSet2:" + rangeSet2);
		rangeSet2.add(Range.open(5, 10));
		PrintUtil.println("rangeSet2:" + rangeSet2);
		rangeSet2.add(Range.openClosed(2, 5));
		PrintUtil.println("rangeSet2:" + rangeSet2);
		rangeSet2.add(Range.open(12, 15));
		PrintUtil.println("rangeSet2:" + rangeSet2);
		rangeSet2.add(Range.openClosed(9, 13));
		PrintUtil.println("rangeSet2:" + rangeSet2);
		rangeSet2.add(Range.closed(15, 20));
		PrintUtil.println("rangeSet2:" + rangeSet2);
		rangeSet2.add(Range.closed(20, 25));
		PrintUtil.println("rangeSet2:" + rangeSet2);

		/**
		 * Queries
		 * contains(C):这是RangeSet最基本的操作，它能查询给定的元素是否在RangeSet里。
		 * rangeContaining(C): 返回包含给定的元素的Range，如果不存在就返回null。
		 * encloses(Range<C>): 用来判断给定的Range是否包含在RangeSet里面。
		 * span():返回一个包含在这个RangeSet的所有Range的并集。
		 */
		PrintUtil.println(rangeSet2.contains(21));
		PrintUtil.println(rangeSet2.contains(26));
		PrintUtil.println(rangeSet2.rangeContaining(21));
		PrintUtil.println(rangeSet2.rangeContaining(26));
		PrintUtil.println(rangeSet2.encloses(Range.closed(20, 25)));
		PrintUtil.println(rangeSet2.encloses(Range.closedOpen(1, 20)));
		PrintUtil.println(rangeSet1.span());
		PrintUtil.println(rangeSet2.span());
	}
}
