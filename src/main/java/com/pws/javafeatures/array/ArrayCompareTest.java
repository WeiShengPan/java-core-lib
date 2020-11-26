package com.pws.javafeatures.array;

import com.pws.javafeatures.util.PrintUtil;

import java.util.Arrays;

/**
 * Arrays.equals() 用于比较整个数组，此方法针对所有基本类型与Object都做了重载。
 * 数组相等的条件是元素个数必须相等，并且对应位置的元素也必须相等，通过对每一个
 * 元素使用呢 equals() 作比较来判断。（对于基本类型，需要使用基本类型的包装器
 * 的 equals() 方法）
 *
 * @author panws
 * @since 2017-10-27
 */
public class ArrayCompareTest {

	public static void main(String[] args) {

		int[] a1 = new int[10];
		int[] a2 = new int[10];
		Arrays.fill(a1, 47);
		Arrays.fill(a2, 47);
		PrintUtil.println(Arrays.equals(a1, a2));

		a2[3] = 11;
		PrintUtil.println(Arrays.equals(a1, a2));

		String[] s1 = new String[4];
		Arrays.fill(s1, "Hi");
		String[] s2 = { "Hi", "Hi", "Hi", "Hi" };
		PrintUtil.println(Arrays.equals(s1, s2));
	}
}
