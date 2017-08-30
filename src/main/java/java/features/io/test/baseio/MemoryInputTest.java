package java.features.io.test.baseio;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.StringReader;

/**
 * 从内存输入
 *
 * @author panws
 * @since 2017-08-15
 */
public class MemoryInputTest {

	public static void main(String[] args) throws IOException {

		/**
		 * memory input
		 */
		try (StringReader in = new StringReader(BufferReaderTest.read("src/main/resources/io/input1.txt"))) {

			int c;

			while ((c = in.read()) != -1) {
				System.out.println((char) c);
			}

		}

		/**
		 * formatted memory input
		 */
		try (DataInputStream dataInputStream = new DataInputStream(
				new ByteArrayInputStream(BufferReaderTest.read("src/main/resources/io/input1.txt").getBytes()))) {

			while (dataInputStream.available() != 0) {
				System.out.print((char) dataInputStream.readByte());
			}

		}

	}
}

