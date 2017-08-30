package java.features.io.test.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.StandardCharsets;

/**
 * 使用FileChannel提高读写性能
 *
 * @author panws
 * @since 2017-08-16
 */
public class FileChannelTest {

	private static final int B_SIZE = 1024;

	private static final String FILE_PATH = "src/main/resources/io/output5.txt";

	public static void main(String[] args) throws IOException {

		//write from FileOutputStream
		try (FileChannel fc = new FileOutputStream(FILE_PATH).getChannel()) {

			fc.write(ByteBuffer.wrap("你好 \n".getBytes()));
		}

		//write from RandomAccessFile
		try (FileChannel fc = new RandomAccessFile(FILE_PATH, "rw").getChannel()) {

			fc.position(fc.size());    //move to the end of the file

			fc.write(ByteBuffer.wrap("Some text in the end \n".getBytes()));
		}

		//read from FileInputStream
		try (FileChannel fc = new FileInputStream(FILE_PATH).getChannel()) {

			//只读访问必须分配ByteBuffer的大小，更高速度可用ByteBuffer.allocateDirect()产生直接缓冲器，但开支更大
			ByteBuffer byteBuffer = ByteBuffer.allocate(B_SIZE);

			fc.read(byteBuffer);
			byteBuffer.flip();

			CharsetDecoder decoder = StandardCharsets.UTF_8.newDecoder();    //解决编码问题

			CharBuffer charBuffer = CharBuffer.allocate(B_SIZE);
			decoder.decode(byteBuffer, charBuffer, false);
			charBuffer.flip();

			System.out.println(charBuffer);
		}
	}
}
