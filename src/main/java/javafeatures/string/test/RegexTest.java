package javafeatures.string.test;

import com.google.common.collect.Lists;
import javafeatures.util.PrintUtil;

/**
 * @author panws
 * @since 2017-09-21
 */
public class RegexTest {

	public static void main(String[] args) {

		String str = "51314+6752-342431-1749183+12839-17345";

		// 需要两个反斜杠
		String regex = "-?\\d";

		PrintUtil.println(String.format("String: %s", str));

		PrintUtil.println(str.matches("\\d+"));
		PrintUtil.println("abcd".matches("bc"));

		PrintUtil.println(Lists.newArrayList(str.split("(-|\\+)")));
		//限制分隔次数
		PrintUtil.println(Lists.newArrayList(str.split("(-|\\+)", 3)));

		//正则匹配替换
		PrintUtil.println(Lists.newArrayList(str.replaceAll("(-|\\+)", "|")));    

	}
}
