package javafeatures.io.test.baseio;

import javafeatures.util.PrintUtil;

import java.io.*;

/**
 * 标准输入输出重定向
 *
 * @author panws
 * @since 2017-08-16
 */
public class RedirectingTest {

	public static void main(String[] args) throws IOException {

		PrintStream console = System.out;

		try (BufferedInputStream in = new BufferedInputStream(
				new FileInputStream("src/main/resources/io/input1.txt"))) {

			try (PrintStream out = new PrintStream(
					new BufferedOutputStream(new FileOutputStream("src/main/resources/io/output4.txt")))) {

				//将标准输入重定向到文件中
				System.setIn(in);
				//将标准输出重定向到文件中
				System.setOut(out);
				System.setErr(out);

				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

				String s;

				while ((s = br.readLine()) != null) {
					PrintUtil.println(s);
				}

			}
		}

		System.setOut(console);
	}
}
