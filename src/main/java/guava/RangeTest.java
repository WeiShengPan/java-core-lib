package guava;

import com.google.common.collect.BoundType;
import com.google.common.collect.Range;
import com.google.common.primitives.Ints;
import javafeatures.util.PrintUtil;

/**
 * Range ：定义了连续跨度的范围边界，这个连续跨度是一个可以比较的类型(Comparable type)
 * <p>
 * <p>
 * 概念            表示范围             Range对应功能方法
 * -------------------------------------------------------
 * (a..b)       {x | a < x < b}        open(C, C)
 * [a..b]       {x | a <= x <= b}      closed(C, C)
 * [a..b)       {x | a <= x < b}       closedOpen(C, C)
 * (a..b]       {x | a < x <= b}       openClosed(C, C)
 * (a..+∞)      {x | x > a}            greaterThan(C)
 * [a..+∞)      {x | x >= a}           atLeast(C)
 * (-∞..b)      {x | x < b}            lessThan(C)
 * (-∞..b]      {x | x <= b}           atMost(C)
 * (-∞..+∞)     all values             all()
 *
 * @author panws
 * @since 2017-06-23
 */
public class RangeTest {

	public static void main(String[] args) {

		PrintUtil.println("open:" + Range.open(1, 10));
		PrintUtil.println("closed:" + Range.closed(1, 10));
		PrintUtil.println("closedOpen:" + Range.closedOpen(1, 10));
		PrintUtil.println("openClosed:" + Range.openClosed(1, 10));
		PrintUtil.println("greaterThan:" + Range.greaterThan(10));
		PrintUtil.println("atLeast:" + Range.atLeast(10));
		PrintUtil.println("lessThan:" + Range.lessThan(10));
		PrintUtil.println("atMost:" + Range.atMost(10));
		PrintUtil.println("all:" + Range.all());
		PrintUtil.println("closed:" + Range.closed(10, 10));
		PrintUtil.println("closedOpen:" + Range.closedOpen(10, 10));

		//		PrintUtil.println("open:" + Range.open(10, 10)); //会抛出异常

		PrintUtil.println("downTo:" + Range.downTo(4, BoundType.OPEN));
		PrintUtil.println("upTo:" + Range.upTo(4, BoundType.CLOSED));
		PrintUtil.println("range:" + Range.range(1, BoundType.CLOSED, 4, BoundType.OPEN));
		PrintUtil.println("***********************************************");

		/**
		 * contains：判断值是否在当前Range内
		 */
		PrintUtil.println("contains：判断值是否在当前Range内");
		PrintUtil.println(Range.closed(1, 3).contains(2));
		PrintUtil.println(Range.closed(1, 3).contains(4));
		PrintUtil.println(Range.atMost(5).contains(5));
		PrintUtil.println(Range.lessThan(5).contains(5));
		PrintUtil.println(Range.upTo(4, BoundType.OPEN).contains(3));
		PrintUtil.println(Range.upTo(4, BoundType.OPEN).contains(4));
		PrintUtil.println(Range.range(1, BoundType.CLOSED, 4, BoundType.OPEN).contains(1));
		PrintUtil.println(Range.range(1, BoundType.CLOSED, 4, BoundType.OPEN).contains(4));
		PrintUtil.println(Range.closed(1, 4).containsAll(Ints.asList(1, 2, 3)));
		PrintUtil.println("***********************************************");

		/**
		 * Endpoint BoundType相关查询方法
		 */
		PrintUtil.println("Endpoint BoundType相关查询方法");
		PrintUtil.println("hasLowerBound:" + Range.closedOpen(4, 4).hasLowerBound());
		PrintUtil.println("hasUpperBound:" + Range.closedOpen(4, 4).hasUpperBound());
		PrintUtil.println(Range.closedOpen(4, 4).isEmpty());
		PrintUtil.println(Range.openClosed(4, 4).isEmpty());
		PrintUtil.println(Range.closed(4, 4).isEmpty());
		//		PrintUtil.println(Range.open(4, 4).isEmpty());	//异常 Invalid range: (4‥4)
		PrintUtil.println(Range.closed(3, 10).lowerEndpoint());
		PrintUtil.println(Range.open(3, 10).lowerEndpoint());
		PrintUtil.println(Range.closed(3, 10).upperEndpoint());
		PrintUtil.println(Range.open(3, 10).upperEndpoint());
		PrintUtil.println(Range.closed(3, 10).lowerBoundType());
		PrintUtil.println(Range.open(3, 10).upperBoundType());
		PrintUtil.println("***********************************************");

		/**
		 * encloses：encloses(Range range)中的range是否包含在需要比较的range中
		 */
		PrintUtil.println("操作方法 encloses：encloses(Range range)中的range是否包含在需要比较的range中");
		Range<Integer> baseRange = Range.open(1, 4);
		PrintUtil.println(baseRange.encloses(Range.closed(2, 3)));
		PrintUtil.println(baseRange.encloses(Range.closedOpen(2, 4)));
		PrintUtil.println(baseRange.encloses(Range.closed(2, 4)));
		PrintUtil.println(baseRange.encloses(Range.open(2, 5)));
		PrintUtil.println("***********************************************");

		/**
		 * isConnected：range是否可连接上
		 */
		PrintUtil.println("操作方法 isConnected：range是否可连接上");
		PrintUtil.println(Range.closed(3, 5).isConnected(Range.open(5, 10)));
		PrintUtil.println(Range.open(3, 5).isConnected(Range.open(5, 10)));
		PrintUtil.println(Range.closed(0, 9).isConnected(Range.closed(3, 4)));
		PrintUtil.println(Range.open(0, 9).isConnected(Range.closed(3, 4)));
		PrintUtil.println(Range.closed(0, 5).isConnected(Range.closed(3, 9)));
		PrintUtil.println(Range.closed(1, 5).isConnected(Range.closed(6, 10)));
		PrintUtil.println("***********************************************");

		/**
		 * intersection：如果两个range相连时，返回最大交集，如果不相连时，直接抛出异常
		 */
		PrintUtil.println("intersection：如果两个range相连时，返回最大交集，如果不相连时，直接抛出异常");
		PrintUtil.println(Range.closed(3, 5).intersection(Range.open(5, 10)));
		PrintUtil.println(Range.closed(0, 9).intersection(Range.closed(3, 4)));
		PrintUtil.println(Range.closed(0, 5).intersection(Range.closed(3, 9)));
		PrintUtil.println(Range.closedOpen(0, 5).intersection(Range.openClosed(3, 9)));
		//		PrintUtil.println(Range.open(3, 5).intersection(Range.open(5, 10)));	//集合不想连，抛出异常
		//		PrintUtil.println(Range.closed(1, 5).intersection(Range.closed(6, 10)));//集合不想连，抛出异常
		PrintUtil.println("***********************************************");

		/**
		 * span：获取两个range间的最大区间，如果两个range是两连的，则是其最小range
		 */
		PrintUtil.println("span：获取两个range的最大区间");
		PrintUtil.println(Range.closed(3, 5).span(Range.closed(5, 10)));
		PrintUtil.println(Range.closed(3, 5).span(Range.open(5, 10)));
		PrintUtil.println(Range.closedOpen(3, 5).span(Range.open(5, 10)));
		PrintUtil.println(Range.open(3, 5).span(Range.open(5, 10)));
		PrintUtil.println(Range.closed(0, 9).span(Range.closed(3, 4)));
		PrintUtil.println(Range.closed(0, 5).span(Range.closed(3, 9)));
		PrintUtil.println(Range.closed(1, 5).span(Range.closed(6, 10)));
		PrintUtil.println(Range.closed(1, 5).span(Range.closed(7, 10)));
		PrintUtil.println(Range.closed(1, 3).span(Range.closed(7, 10)));
		PrintUtil.println(Range.closed(7, 10).span(Range.closed(1, 3)));
		PrintUtil.println("***********************************************");

	}
}
