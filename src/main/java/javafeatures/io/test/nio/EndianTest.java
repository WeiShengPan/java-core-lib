package javafeatures.io.test.nio;

import javafeatures.util.PrintUtil;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

/**
 * @author panws
 * @since 2017-08-17
 */
public class EndianTest {

	private static final String VALUE = "abcdef";

	public static void main(String[] args) {

		ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[12]);

		//默认采用高位优先
		byteBuffer.asCharBuffer().put(VALUE);
		PrintUtil.println(Arrays.toString(byteBuffer.array()));
		byteBuffer.rewind();

		//手动指定采用高位优先
		byteBuffer.order(ByteOrder.BIG_ENDIAN);
		byteBuffer.asCharBuffer().put(VALUE);
		PrintUtil.println(Arrays.toString(byteBuffer.array()));
		byteBuffer.rewind();

		//手动指定采用低位优先
		byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
		byteBuffer.asCharBuffer().put(VALUE);
		PrintUtil.println(Arrays.toString(byteBuffer.array()));

	}
}
