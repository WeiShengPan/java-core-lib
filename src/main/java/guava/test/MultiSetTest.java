package guava.test;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import javafeatures.util.PrintUtil;

/**
 * Multiset: 允许把重复的元素放入集合.
 *
 * @author panws
 * @since 2017-06-23
 */
public class MultiSetTest {

	private static final String GREEN = "GREEN";
	private static final String RED = "RED";
	private static final String YELLOW = "YELLOW";
	private static final String BLACK = "BLACK";

	public static void main(String[] args) {

		/**
		 * Multiset: 允许把重复的元素放入集合.
		 * 普通的 Set 就像这样 :[car, ship, bike]，而 Multiset 会是这样 : [car x 2, ship x 6, bike x 3]。
		 */
		HashMultiset<String> hashMultiset = HashMultiset.create();
		hashMultiset.add(RED);
		hashMultiset.add(RED);
		hashMultiset.add(GREEN);
		hashMultiset.add(GREEN);
		hashMultiset.add(GREEN);
		hashMultiset.add(YELLOW);
		hashMultiset.add(BLACK, 5);

		PrintUtil.println(hashMultiset.count(RED));
		PrintUtil.println(hashMultiset.count(GREEN));
		PrintUtil.println(hashMultiset.count(YELLOW));
		PrintUtil.println(hashMultiset.count(BLACK));
		// [RED x 2, YELLOW, GREEN x 3]
		PrintUtil.println(hashMultiset);

		for (Multiset.Entry entry : hashMultiset.entrySet()) {
			PrintUtil.printSep();
			PrintUtil.println(entry);
			PrintUtil.println(entry.getElement());
			PrintUtil.println(entry.getCount());
		}
	}
}
