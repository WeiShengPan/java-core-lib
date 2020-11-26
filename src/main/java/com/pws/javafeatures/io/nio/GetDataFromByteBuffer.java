package com.pws.javafeatures.io.nio;

import com.pws.javafeatures.util.PrintUtil;

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

		//缓冲器分配自动置零
		ByteBuffer byteBuffer = ByteBuffer.allocate(B_SIZE);

		int i = 0;
		while (i++ < byteBuffer.limit()) {
			if (byteBuffer.get() != 0) {
				PrintUtil.println("nonzero");
			}
		}
		PrintUtil.println("i = " + i);

		byteBuffer.rewind();    //Rewinds this buffer.  The position is set to zero and the mark is discarded.

		byteBuffer.asCharBuffer().put("Hello world!");
		PrintUtil.println(byteBuffer.asCharBuffer());

		byteBuffer.asIntBuffer().put(1123123);
		PrintUtil.println(byteBuffer.getInt());

		byteBuffer.asDoubleBuffer().put(12312.123123123);
		PrintUtil.println(byteBuffer.getDouble());

	}
}
