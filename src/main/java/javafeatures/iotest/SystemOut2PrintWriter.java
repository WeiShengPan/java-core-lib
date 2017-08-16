package javafeatures.iotest;

import java.io.PrintWriter;

/**
 * 标准输出转换为PrintWriter
 *
 * @author panws
 * @since 2017-08-16
 */
public class SystemOut2PrintWriter {

	public static void main(String[] args) {

		PrintWriter out = new PrintWriter(System.out, true);

		out.println("hello world!");
	}
}
