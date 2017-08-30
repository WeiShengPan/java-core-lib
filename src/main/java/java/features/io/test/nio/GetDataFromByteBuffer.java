package java.features.io.test.nio;

import java.nio.ByteBuffer;

/**
 * 从ByteBuffer中获取基本类型
 *
 * @author panws
 * @since 2017-08-17
 */
public class GetDataFromByteBuffer {

	private static final int B_SIZE = 1024;

	public static void main(String[] args) {

		ByteBuffer byteBuffer = ByteBuffer.allocate(B_SIZE);    //缓冲器分配自动置零

		int i = 0;
		while (i++ < byteBuffer.limit()) {
			if (byteBuffer.get() != 0) {
				System.out.println("nonzero");
			}
		}
		System.out.println("i = " + i);

		byteBuffer.rewind();    //Rewinds this buffer.  The position is set to zero and the mark is discarded.

		byteBuffer.asCharBuffer().put("Hello world!");
		System.out.println(byteBuffer.asCharBuffer());

		byteBuffer.asIntBuffer().put(1123123);
		System.out.println(byteBuffer.getInt());

		byteBuffer.asDoubleBuffer().put(12312.123123123);
		System.out.println(byteBuffer.getDouble());

	}
}
