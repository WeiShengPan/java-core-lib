package guava.test;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

/**
 * Multiset: 允许把重复的元素放入集合.
 *
 * @author panws
 * @since 2017-06-23
 */
public class MultiSetTest {

	public static void main(String[] args) {

		/**
		 * Multiset: 允许把重复的元素放入集合.
		 * 普通的 Set 就像这样 :[car, ship, bike]，而 Multiset 会是这样 : [car x 2, ship x 6, bike x 3]。
		 */
		HashMultiset<String> hashMultiset = HashMultiset.create();
		hashMultiset.add("RED");
		hashMultiset.add("RED");
		hashMultiset.add("GREEN");
		hashMultiset.add("GREEN");
		hashMultiset.add("GREEN");
		hashMultiset.add("YELLOW");
		hashMultiset.add("BLACK", 5);

		System.out.println(hashMultiset.count("RED"));
		System.out.println(hashMultiset.count("GREEN"));
		System.out.println(hashMultiset.count("YELLOW"));
		System.out.println(hashMultiset.count("BLACK"));
		System.out.println(hashMultiset);    // [RED x 2, YELLOW, GREEN x 3]

		for (Multiset.Entry entry : hashMultiset.entrySet()) {
			System.out.println("***");
			System.out.println(entry);
			System.out.println(entry.getElement());
			System.out.println(entry.getCount());
		}
	}
}
