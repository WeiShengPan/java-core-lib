package javafeatures.iotest;

import java.io.*;

/**
 * @author panws
 * @since 2017-08-16
 */
public class FileOutputTest {

	public static void main(String[] args) throws IOException {

		BufferedReader in = new BufferedReader(new FileReader("src/main/resources/io/input1.txt"));

		PrintWriter out = new PrintWriter("src/main/resources/io/output1.txt");

		int lineCount = 1;

		String s;

		while ((s = in.readLine()) != null) {
			out.println(lineCount++ + ":" + s);
		}

		out.close();

	}
}
