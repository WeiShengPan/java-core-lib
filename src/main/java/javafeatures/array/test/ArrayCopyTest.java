package javafeatures.array.test;

import javafeatures.util.PrintUtil;

import java.util.Arrays;

/**
 * System.arraycopy()用于复制数组
 *
 * @author panws
 * @since 2017-10-27
 */
public class ArrayCopyTest {

	public static void main(String[] args) {

		//primitive
		int[] i = new int[7];
		int[] j = new int[10];
		Arrays.fill(i, 47);
		Arrays.fill(j, 99);
		PrintUtil.println("i = " + Arrays.toString(i));
		PrintUtil.println("j = " + Arrays.toString(j));
		System.arraycopy(i, 0, j, 0, i.length);
		PrintUtil.println("j = " + Arrays.toString(j));
		int[] k = new int[5];
		Arrays.fill(k, 103);
		System.arraycopy(i, 0, j, 0, k.length);
		PrintUtil.println("k = " + Arrays.toString(k));
		Arrays.fill(k, 103);
		System.arraycopy(k, 0, i, 0, k.length);
		PrintUtil.println("i = " + Arrays.toString(i));

		//Objects
		Integer[] u = new Integer[10];
		Integer[] v = new Integer[5];
		Arrays.fill(u, 47);
		Arrays.fill(v, 99);
		PrintUtil.println("u = " + Arrays.toString(u));
		PrintUtil.println("v = " + Arrays.toString(v));
		System.arraycopy(v, 0, u, u.length / 2, v.length);
		PrintUtil.println("u = " + Arrays.toString(u));

	}
}
