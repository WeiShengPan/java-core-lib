package com.pws.javafeatures.collection;

import com.pws.javafeatures.util.PrintUtil;

import java.util.*;

/**
 * @author panws
 * @since 2017-08-31
 */
public class SetMethods {

	public static void main(String[] args) {

		/*
		 * 正常操作
		 */
		test(new HashSet<>(), HashType.class);
		test(new LinkedHashSet<>(), HashType.class);
		test(new TreeSet<>(), TreeType.class);

		/*
		 * 异常操作
		 */
		//SetType没有恰当hashCode()方法，set中会有重复元素
		test(new HashSet<>(), SetType.class);
		//TreeType没有恰当hashCode()方法，set中会有重复元素
		test(new HashSet<>(), TreeType.class);
		//SetType没有实现Comparable接口，无法加入TreeSet中
		try {
			test(new TreeSet<>(), SetType.class);
		} catch (Exception e) {
			PrintUtil.println(e.getMessage());
		}
		//HashType没有实现Comparable接口，无法加入TreeSet中
		try {
			test(new TreeSet<>(), HashType.class);
		} catch (Exception e) {
			PrintUtil.println(e.getMessage());
		}
	}

	private static <T> void test(Set<T> set, Class<T> type) {

		fill(set, type);

		fill(set, type);

		fill(set, type);

		PrintUtil.println(set);

	}

	private static <T> void fill(Set<T> set, Class<T> type) {

		try {
			for (int i = 0; i < 10; i++) {
				set.add(type.getConstructor(int.class).newInstance(i));
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}

class SetType {

	int i;

	public SetType(int i) {
		this.i = i;
	}

	@Override public boolean equals(Object obj) {
		return obj instanceof SetType && (i == ((SetType) obj).i);
	}

	@Override public String toString() {
		return Integer.toString(i);
	}
}

class HashType extends SetType {

	public HashType(int i) {
		super(i);
	}

	@Override public int hashCode() {
		return i;
	}
}

class TreeType extends SetType implements Comparable<TreeType> {

	public TreeType(int i) {
		super(i);
	}

	@Override public int compareTo(TreeType o) {
		return (o.i < i ? -1 : (o.i == i ? 0 : 1));
	}
}

class SortedSetTest {

	public static void main(String[] args) {

		SortedSet<String> sortedSet = new TreeSet<>();

		Collections.addAll(sortedSet, "ONE TWO THREE FOUR FIVE SIX SEVEN EIGHT".split(" "));

		PrintUtil.println(sortedSet);

		String low = sortedSet.first();
		String high = sortedSet.last();
		PrintUtil.println(low);
		PrintUtil.println(high);

		Iterator<String> iterator = sortedSet.iterator();
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

		PrintUtil.println(sortedSet.subSet(low, high));
		PrintUtil.println(sortedSet.headSet(high));
		PrintUtil.println(sortedSet.tailSet(low));
	}
}


