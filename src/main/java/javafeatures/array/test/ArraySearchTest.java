package javafeatures.array.test;

import javafeatures.util.PrintUtil;

import java.util.Arrays;
import java.util.Random;

/**
 * Arrays.binarySearch() 快速查找数组中的元素
 * <p>
 * 前提：必须对Arrays进行排序，如果对未排序的数组使用binarySearch()，将产生不可预料的结果
 * <p>
 * 如果找到目标，函数返回值为在数组中的位置（大于或等于0），否则产生负返回值；如果数组中包
 * 含重复的元素，无法保证找到的是哪一个
 *
 * @author panws
 * @since 2017-10-27
 */
public class ArraySearchTest {

	private static Random random = new Random(47);

	public static void main(String[] args) {

		int[] a = new int[20];
		for (int i = 0; i < a.length; i++) {
			a[i] = random.nextInt(100);
		}
		PrintUtil.println(Arrays.toString(a));

		Arrays.sort(a);
		PrintUtil.println(Arrays.toString(a));

		while (true) {
			int ele = random.nextInt(100);
			int location = Arrays.binarySearch(a, ele);
			if (location >= 0) {
				PrintUtil.println("\nElement Found " + ele + " at Position a[" + location + "]");
				break;
			} else {
				PrintUtil.print(ele + " ");
			}
		}

	}
}
