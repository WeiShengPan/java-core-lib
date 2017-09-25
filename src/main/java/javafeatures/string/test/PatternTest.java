package javafeatures.string.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author panws
 * @see Pattern
 * @see Matcher
 * @since 2017-09-22
 */
public class PatternTest {

	public static void main(String[] args) {

		Matcher matcher = Pattern.compile("[frb][aiu][gx]").matcher("fix the rug with bags");
		while (matcher.find()) {
			System.out.print(matcher.group() + " ");
		}

		System.out.println();

		//调用带参数的reset()，可以将Matcher应用于一个新的String
		matcher.reset("fux the rig with rag");
		while (matcher.find()) {
			System.out.print(matcher.group() + " ");
		}

		System.out.println();

		//调用不带参数的reset()，可以将Matcher对象重新设置到当前字符序列的起始位置
		matcher.reset();
		while (matcher.find()) {
			System.out.print(matcher.group() + " ");
		}
	}
}
