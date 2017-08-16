package javafeatures.iotest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.Buffer;

/**
 * 缓冲输入文件
 *
 * @author panws
 * @since 2017-08-15
 */
public class BufferReaderTest {

	public static void main(String[] args) throws IOException {

		System.out.println(BufferReaderTest.read("src/main/resources/io/input1.txt"));

	}

	public static String read(String filename) throws IOException {

		//使用BufferReader做缓冲，提高速度
		BufferedReader in = new BufferedReader(new FileReader(filename));

		StringBuilder stringBuilder = new StringBuilder();

		String tempStr;

		while ((tempStr = in.readLine()) != null) {
			stringBuilder.append(tempStr).append("\n");
		}

		in.close();

		return stringBuilder.toString();
	}
}
