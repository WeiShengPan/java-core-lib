package javafeatures.io.test.baseio;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

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

		StringBuilder stringBuilder = new StringBuilder();

		//使用BufferReader做缓冲，提高速度
		try (BufferedReader in = new BufferedReader(new FileReader(filename))) {

			String tempStr;

			while ((tempStr = in.readLine()) != null) {
				stringBuilder.append(tempStr).append("\n");
			}

		}

		return stringBuilder.toString();
	}
}
