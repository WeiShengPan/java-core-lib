package com.pws.javafeatures.java8;

import java.util.Arrays;
import java.util.Comparator;

import com.pws.javafeatures.util.PrintUtil;

/**
 * @author panws
 * @since 2018-01-02
 */
public class LambdaTest {

	public static void main(String[] args) {

		/*
		 * 以下等价
		 */
		Arrays.asList("a", "b", "c", "\n").forEach(operator -> PrintUtil.print(operator));
		Arrays.asList("a", "b", "c", "\n").forEach(operator -> {
			PrintUtil.print(operator);
		});
		Arrays.asList("a", "b", "c", "\n").forEach((String operator) -> PrintUtil.print(operator));
		Arrays.asList("a", "b", "c", "\n").forEach(PrintUtil::print);

		/*
		 * 以下等价
		 */
		Arrays.asList("a", "c", "b").sort(new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return o1.compareTo(o2);
			}
		});
		//单行不需要return语句
		Arrays.asList("a", "c", "b").sort((o1, o2) -> o1.compareTo(o2));
		//多行需要有return语句
		Arrays.asList("a", "c", "b").sort((o1, o2) -> {
			int result = o1.compareTo(o2);
			PrintUtil.println(result);
			return result;
		});

	}
}
