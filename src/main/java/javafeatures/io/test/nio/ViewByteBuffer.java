package javafeatures.io.test.nio;

import javafeatures.util.PrintUtil;

import java.nio.*;

/**
 * 理解ByteBuffer的视图缓冲器
 *
 * @author panws
 * @since 2017-08-17
 */
public class ViewByteBuffer {

	public static void main(String[] args) {

		//byte buffer
		//赋值为8位的byte数组
		ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[] { 0, 0, 0, 0, 0, 0, 0, 'a' });    
		byteBuffer.rewind();
		PrintUtil.print("\nByte   Buffer: ");
		while (byteBuffer.hasRemaining()) {
			PrintUtil.print(byteBuffer.position() + "->" + byteBuffer.get() + ", ");
		}
		byteBuffer.rewind();

		//char buffer
		CharBuffer charBuffer = byteBuffer.asCharBuffer();
		PrintUtil.print("\nChar   Buffer: ");
		while (charBuffer.hasRemaining()) {
			PrintUtil.print(charBuffer.position() + "->" + charBuffer.get() + ", ");
		}
		byteBuffer.rewind();

		//short buffer
		ShortBuffer shortBuffer = byteBuffer.asShortBuffer();
		PrintUtil.print("\nShort  Buffer: ");
		while (shortBuffer.hasRemaining()) {
			PrintUtil.print(shortBuffer.position() + "->" + shortBuffer.get() + ", ");
		}
		byteBuffer.rewind();

		//int buffer
		IntBuffer intBuffer = byteBuffer.asIntBuffer();
		PrintUtil.print("\nInt    Buffer: ");
		while (intBuffer.hasRemaining()) {
			PrintUtil.print(intBuffer.position() + "->" + intBuffer.get() + ", ");
		}
		byteBuffer.rewind();

		//float buffer
		FloatBuffer floatBuffer = byteBuffer.asFloatBuffer();
		PrintUtil.print("\nFloat  Buffer: ");
		while (floatBuffer.hasRemaining()) {
			PrintUtil.print(floatBuffer.position() + "->" + floatBuffer.get() + ", ");
		}
		byteBuffer.rewind();

		//long buffer
		LongBuffer longBuffer = byteBuffer.asLongBuffer();
		PrintUtil.print("\nLong   Buffer: ");
		while (longBuffer.hasRemaining()) {
			PrintUtil.print(longBuffer.position() + "->" + longBuffer.get() + ", ");
		}
		byteBuffer.rewind();

		//double buffer
		DoubleBuffer doubleBuffer = byteBuffer.asDoubleBuffer();
		PrintUtil.print("\nDouble Buffer: ");
		while (doubleBuffer.hasRemaining()) {
			PrintUtil.print(doubleBuffer.position() + "->" + doubleBuffer.get() + ", ");
		}
		byteBuffer.rewind();

	}
}
