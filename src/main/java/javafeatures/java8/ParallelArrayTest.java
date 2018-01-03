package javafeatures.java8;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

import javafeatures.util.PrintUtil;

/**
 * Java8版本新增了很多新的方法，用于支持并行数组处理。最重要的方法是parallelSort()，可以显著加快多核机器上的数组排序。
 * 
 * @author panws
 * @since 2018-01-03
 */
public class ParallelArrayTest {
	
	public static void main(String[] args) {
		
		long[] array = new long[20000];
		
		Arrays.parallelSetAll(array, index -> ThreadLocalRandom.current().nextInt(1000000));
		Arrays.stream(array).limit(10).forEach(number -> PrintUtil.print(number + " "));
		PrintUtil.println();
		
		Arrays.parallelSort(array);
		Arrays.stream(array).limit(10).forEach(number -> PrintUtil.print(number + " "));
		PrintUtil.println();
	}
}
