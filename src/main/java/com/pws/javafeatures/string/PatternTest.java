package com.pws.javafeatures.string;

import com.pws.javafeatures.util.PrintUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author panws
 * @see Pattern
 * @see Matcher
 * @since 2017-09-22
 */
public class PatternTest {

	private static final String REGEX = "[frb][aiu][gx]";

	private static Pattern pattern = Pattern.compile(REGEX);

	public static void main(String[] args) {

		Matcher matcher = pattern.matcher("fix the rug with bags");
		while (matcher.find()) {
			PrintUtil.print(matcher.group() + " ");
		}

		PrintUtil.println();

		//调用带参数的reset()，可以将Matcher应用于一个新的String
		matcher.reset("fux the rig with rag");
		while (matcher.find()) {
			PrintUtil.print(matcher.group() + " ");
		}

		PrintUtil.println();

		//调用不带参数的reset()，可以将Matcher对象重新设置到当前字符序列的起始位置
		matcher.reset();
		while (matcher.find()) {
			PrintUtil.print(matcher.group() + " ");
		}
	}
}
