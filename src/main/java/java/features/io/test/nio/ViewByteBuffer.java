package java.features.io.test.nio;

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
		ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[] { 0, 0, 0, 0, 0, 0, 0, 'a' });    //赋值为8位的byte数组
		byteBuffer.rewind();
		System.out.print("\nByte   Buffer: ");
		while (byteBuffer.hasRemaining()) {
			System.out.print(byteBuffer.position() + "->" + byteBuffer.get() + ", ");
		}
		byteBuffer.rewind();

		//char buffer
		CharBuffer charBuffer = byteBuffer.asCharBuffer();
		System.out.print("\nChar   Buffer: ");
		while (charBuffer.hasRemaining()) {
			System.out.print(charBuffer.position() + "->" + charBuffer.get() + ", ");
		}
		byteBuffer.rewind();

		//short buffer
		ShortBuffer shortBuffer = byteBuffer.asShortBuffer();
		System.out.print("\nShort  Buffer: ");
		while (shortBuffer.hasRemaining()) {
			System.out.print(shortBuffer.position() + "->" + shortBuffer.get() + ", ");
		}
		byteBuffer.rewind();

		//int buffer
		IntBuffer intBuffer = byteBuffer.asIntBuffer();
		System.out.print("\nInt    Buffer: ");
		while (intBuffer.hasRemaining()) {
			System.out.print(intBuffer.position() + "->" + intBuffer.get() + ", ");
		}
		byteBuffer.rewind();

		//float buffer
		FloatBuffer floatBuffer = byteBuffer.asFloatBuffer();
		System.out.print("\nFloat  Buffer: ");
		while (floatBuffer.hasRemaining()) {
			System.out.print(floatBuffer.position() + "->" + floatBuffer.get() + ", ");
		}
		byteBuffer.rewind();

		//long buffer
		LongBuffer longBuffer = byteBuffer.asLongBuffer();
		System.out.print("\nLong   Buffer: ");
		while (longBuffer.hasRemaining()) {
			System.out.print(longBuffer.position() + "->" + longBuffer.get() + ", ");
		}
		byteBuffer.rewind();

		//double buffer
		DoubleBuffer doubleBuffer = byteBuffer.asDoubleBuffer();
		System.out.print("\nDouble Buffer: ");
		while (doubleBuffer.hasRemaining()) {
			System.out.print(doubleBuffer.position() + "->" + doubleBuffer.get() + ", ");
		}
		byteBuffer.rewind();

	}
}
