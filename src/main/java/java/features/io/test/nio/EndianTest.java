package java.features.io.test.nio;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

/**
 * @author panws
 * @since 2017-08-17
 */
public class EndianTest {

	public static void main(String[] args) {

		ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[12]);

		byteBuffer.asCharBuffer().put("abcdef");    //默认采用高位优先
		System.out.println(Arrays.toString(byteBuffer.array()));
		byteBuffer.rewind();

		byteBuffer.order(ByteOrder.BIG_ENDIAN);        //采用高位优先
		byteBuffer.asCharBuffer().put("abcdef");
		System.out.println(Arrays.toString(byteBuffer.array()));
		byteBuffer.rewind();

		byteBuffer.order(ByteOrder.LITTLE_ENDIAN);        //采用低位优先
		byteBuffer.asCharBuffer().put("abcdef");
		System.out.println(Arrays.toString(byteBuffer.array()));

	}
}
