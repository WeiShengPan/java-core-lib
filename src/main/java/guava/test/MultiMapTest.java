package guava.test;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.HashMultiset;
import javafeatures.util.PrintUtil;

/**
 * Multimap: 在 Map 的 value 里面放多个元素,Map : {k1=v1, k2=v2,...} Muitimap : {k1=[v1, v2, v3], k2=[v7, v8],....}
 * 替代 Map<K,List<V>> 或 Map<K,Set<V>>
 *
 * @author panws
 * @since 2017-06-23
 */
public class MultiMapTest {

	public static void main(String[] args) {

		/**
		 *  Muitimap 接口的主要实现类有：
		 *  HashMultimap: key 放在 HashMap，而 value 放在 HashSet，即一个 key 对应的 value 不可重复
		 *  ArrayListMultimap: key 放在 HashMap，而 value 放在 ArrayList，即一个 key 对应的 value 有顺序可重复
		 *  LinkedHashMultimap: key 放在 LinkedHashMap，而 value 放在 LinkedHashSet，即一个 key 对应的 value 有顺序不可重复
		 *  TreeMultimap: key 放在 TreeMap，而 value 放在 TreeSet，即一个 key 对应的 value 有排列顺序
		 *  ImmutableMultimap: 不可修改的 Multimap
		 */
		HashMultimap<String, String> hashMultimap1 = HashMultimap.create();
		hashMultimap1.put("President", "Tom");
		hashMultimap1.put("President", "Mike");
		hashMultimap1.put("President", "John");
		hashMultimap1.put("President", "Tom");
		hashMultimap1.put("Vice President", "Tom");
		PrintUtil.println(hashMultimap1);

		HashMultiset<String> hashMultiset1 = HashMultiset.create();
		hashMultiset1.add("Tom");
		hashMultiset1.add("Tom");
		hashMultiset1.add("Mike");
		hashMultiset1.add("Mike");
		hashMultiset1.add("Mike");
		hashMultiset1.add("John");
		HashMultiset<String> hashMultiset2 = HashMultiset.create();
		hashMultiset2.add("Chen");
		hashMultiset2.add("Chen");
		hashMultiset2.add("Li");
		hashMultiset2.add("Li");
		hashMultiset2.add("Li");
		hashMultiset2.add("Zhou");
		HashMultimap<String, HashMultiset<String>> hashMultimap2 = HashMultimap.create();
		hashMultimap2.put("President", hashMultiset1);
		hashMultimap2.put("President", hashMultiset2);
		PrintUtil.println(hashMultimap2);
	}
}
