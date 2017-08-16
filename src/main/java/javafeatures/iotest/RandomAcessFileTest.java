package javafeatures.iotest;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * 读写随机访问文件
 *
 * @author panws
 * @since 2017-08-16
 */
public class RandomAcessFileTest {

	public static void main(String[] args) throws IOException {

		RandomAccessFile raf = new RandomAccessFile("src/main/resources/io/output3.txt", "rw");

		for (int i = 0; i < 7; i++) {
			raf.writeDouble(i * 1.4);
		}
		raf.writeUTF("End of this file.");

		display();

		raf.seek(5 * 8);
		raf.writeDouble(1000);

		display();

		raf.close();

	}

	private static void display() throws IOException {

		RandomAccessFile raf = new RandomAccessFile("src/main/resources/io/output3.txt", "r");

		for (int i = 0; i < 7; i++) {
			System.out.println("Value " + i + ":" + raf.readDouble());
		}

		System.out.println(raf.readUTF());

		raf.close();
	}

}
