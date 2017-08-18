package javafeatures.iotest.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 使用FileChannel进行文件复制
 *
 * @author panws
 * @since 2017-08-16
 */
public class ChannelCopy {

	private static final int B_SIZE = 1024;

	public static void main(String[] args) throws IOException {

		try (FileChannel in = new FileInputStream("src/main/resources/io/input1.txt").getChannel()) {

			try (FileChannel out = new FileOutputStream("src/main/resources/io/output6.txt").getChannel()) {

				//两者等价
				nativeCopy(in, out);
				transfer(in, out);
			}
		}

	}

	private static void nativeCopy(FileChannel in, FileChannel out) throws IOException {

		ByteBuffer byteBuffer = ByteBuffer.allocate(B_SIZE);

		while (in.read(byteBuffer) != -1) {    //FileChannel.read()返回-1表示到达输入末尾
			byteBuffer.flip();    //准备缓冲器，以便信息可被write读取
			out.write(byteBuffer);    //write之后，信息仍在缓冲器内
			byteBuffer.clear();    //对所有内部指针重新安排，以便缓冲器在另一个read操作期间能够做好接受数据的准备
		}
	}

	private static void transfer(FileChannel in, FileChannel out) throws IOException {

		in.transferTo(0, in.size(), out);
		//				out.transferFrom(in, 0, in.size());
	}
}
