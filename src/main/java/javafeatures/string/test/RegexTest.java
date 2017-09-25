package javafeatures.string.test;

import com.google.common.collect.Lists;

/**
 * @author panws
 * @since 2017-09-21
 */
public class RegexTest {

	public static void main(String[] args) {

		String str = "51314+6752-342431-1749183+12839-17345";

		// 需要两个反斜杠
		String regex = "-?\\d";

		System.out.println(String.format("String: %s", str));

		System.out.println(str.matches("\\d+"));
		System.out.println("abcd".matches("bc"));

		System.out.println(Lists.newArrayList(str.split("(-|\\+)")));
		System.out.println(Lists.newArrayList(str.split("(-|\\+)", 3)));    //限制分隔次数

		System.out.println(Lists.newArrayList(str.replaceAll("(-|\\+)", "|")));    //正则匹配替换

	}
}
