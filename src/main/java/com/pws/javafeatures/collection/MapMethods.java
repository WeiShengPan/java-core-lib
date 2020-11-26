package com.pws.javafeatures.collection;

import com.pws.javafeatures.util.PrintUtil;

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
		PrintUtil.println(sortedMap);

		String low = sortedMap.firstKey();
		String high = sortedMap.lastKey();
		PrintUtil.println(low);
		PrintUtil.println(high);

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
		PrintUtil.println(low);
		PrintUtil.println(high);

		PrintUtil.println(sortedMap.subMap(low, high));
		PrintUtil.println(sortedMap.headMap(high));
		PrintUtil.println(sortedMap.tailMap(low));

		PrintUtil.println(sortedMap.higherKey(low));
		PrintUtil.println(sortedMap.lowerKey(high));

	}
}

/**
 * LinkedHashMap 以元素插入顺序排列，或使用LRU(Least Recently Used)算法排列
 */
class LinkedHashMapTest {

	public static void main(String[] args) {

		LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<>(Countries.capitals());
		PrintUtil.println(linkedHashMap);

		//创建一个以access order排序的LinkedHashMap
		linkedHashMap = new LinkedHashMap<>(16, 0.75f, true);   
		linkedHashMap.putAll(Countries.capitals());
		PrintUtil.println(linkedHashMap);

		linkedHashMap.get("FRANCE");
		linkedHashMap.get("GREECE");
		//基于LRU算法，最近最少使用的元素移至队列前面
		PrintUtil.println(linkedHashMap);
		linkedHashMap.get("JAPAN");
		PrintUtil.println(linkedHashMap);
	}
}
