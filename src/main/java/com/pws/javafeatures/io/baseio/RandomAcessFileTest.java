package com.pws.javafeatures.io.baseio;

import com.pws.javafeatures.util.PrintUtil;

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

		try (RandomAccessFile raf = new RandomAccessFile("src/main/resources/io/output3.txt", "rw")) {

			for (int i = 0; i < 7; i++) {
				raf.writeDouble(i * 1.4);
			}
			raf.writeUTF("End of this file.");

			display();

			//double 总是为8字节长，5*8表示查找第5个double值的位置
			raf.seek(5 * 8);
			raf.writeDouble(1000);

			display();
		}

	}

	private static void display() throws IOException {

		try (RandomAccessFile raf = new RandomAccessFile("src/main/resources/io/output3.txt", "r")) {

			for (int i = 0; i < 7; i++) {
				PrintUtil.println("Value " + i + ":" + raf.readDouble());
			}

			PrintUtil.println(raf.readUTF());
		}

	}

}
