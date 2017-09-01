package javafeatures.collection.test;

import javafeatures.util.PrintUtil;

import java.util.*;

/**
 * java.util.Collections中卓越的方法
 *
 * @author panws
 * @since 2017-09-01
 */
public class CollectionUtility {

	private static List<String> list = Arrays.asList("one TWO THREE FOUR FIVE SIX ONE".split(" "));
	private static List<String> subList = Arrays.asList("FOUR FIVE SIX".split(" "));

	public static void main(String[] args) {

		common();

		sychronization();

	}

	public static void common() {
		PrintUtil.println(list);

		PrintUtil.println(Collections.disjoint(list, Collections.singletonList("FOUR")));

		PrintUtil.println(Collections.max(list));
		PrintUtil.println(Collections.min(list));
		PrintUtil.println(Collections.max(list, String.CASE_INSENSITIVE_ORDER));
		PrintUtil.println(Collections.min(list, String.CASE_INSENSITIVE_ORDER));

		PrintUtil.println(Collections.indexOfSubList(list, subList));
		PrintUtil.println(Collections.lastIndexOfSubList(list, subList));

		Collections.replaceAll(list, "ONE", "YO");
		PrintUtil.println(list);

		Collections.reverse(list);
		PrintUtil.println(list);

		Collections.rotate(list, 3);
		PrintUtil.println(list);

		List<String> source = Arrays.asList("in the matrix".split(" "));
		Collections.copy(list, source);
		PrintUtil.println(list);

		Collections.swap(list, 0, list.size() - 1);
		PrintUtil.println(list);

		Collections.shuffle(list, new Random(47));
		PrintUtil.println(list);

		Collections.fill(list, "pop");
		PrintUtil.println(list);
		PrintUtil.println(Collections.frequency(list, "pop"));

		List<String> dups = Collections.nCopies(3, "snap");
		PrintUtil.println(dups);
		PrintUtil.println(Collections.disjoint(list, dups));
	}

	public static void sychronization() {

		List<String> list = Collections.synchronizedList(new ArrayList<>());

		Set<String> set = Collections.synchronizedSet(new HashSet<>());

		SortedSet<String> sortedSet = Collections.synchronizedSortedSet(new TreeSet<>());

		Map<String, String> map = Collections.synchronizedMap(new HashMap<>());

		SortedMap<String, String> sortedMap = Collections.synchronizedSortedMap(new TreeMap<>());

	}

}
