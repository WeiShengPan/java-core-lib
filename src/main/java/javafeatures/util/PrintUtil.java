package javafeatures.util;

/**
 * @author panws
 * @since 2017-08-31
 */
public class PrintUtil {

	private static final String LINE_SEPERATOR = "********************";

	public static void println(Object obj) {
		System.out.println(obj);
	}

	public static void println(String prefix, Object obj) {
		System.out.println(prefix + obj);
	}

	public static void printSep() {
		System.out.println(LINE_SEPERATOR);
	}
}
