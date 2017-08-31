package javafeatures.collection.test;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.TreeMap;

/**
 * @author panws
 * @since 2017-08-31
 */
public class MapMethods {
}

/**
 * SortedMap 可以确保键处于排序状态
 */
class SortedMapTest {

	public static void main(String[] args) {

		TreeMap<String, String> sortedMap = new TreeMap<>(Countries.capitals());
		System.out.println(sortedMap);

		String low = sortedMap.firstKey();
		String high = sortedMap.lastKey();
		System.out.println(low);
		System.out.println(high);

		Iterator<String> iterator = sortedMap.keySet().iterator();
		for (int i = 0; i <= 6; i++) {
			switch (i) {
				case 3:
					low = iterator.next();
					break;
				case 6:
					high = iterator.next();
					break;
				default:
					iterator.next();
			}
		}
		System.out.println(low);
		System.out.println(high);

		System.out.println(sortedMap.subMap(low, high));
		System.out.println(sortedMap.headMap(high));
		System.out.println(sortedMap.tailMap(low));

		System.out.println(sortedMap.higherKey(low));
		System.out.println(sortedMap.lowerKey(high));

	}
}

/**
 * LinkedHashMap 以元素插入顺序排列，或使用LRU(Least Recently Used)算法排列
 */
class LinkedHashMapTest {

	public static void main(String[] args) {

		LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<>(Countries.capitals());
		System.out.println(linkedHashMap);

		linkedHashMap = new LinkedHashMap<>(16, 0.75f, true);    //创建一个以access order排序的LinkedHashMap
		linkedHashMap.putAll(Countries.capitals());
		System.out.println(linkedHashMap);

		linkedHashMap.get("FRANCE");
		linkedHashMap.get("GREECE");
		System.out.println(linkedHashMap);    //基于LRU算法，最近最少使用的元素移至队列前面
		linkedHashMap.get("JAPAN");
		System.out.println(linkedHashMap);
	}
}
