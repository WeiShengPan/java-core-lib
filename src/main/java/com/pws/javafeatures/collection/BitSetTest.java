package com.pws.javafeatures.collection;

import com.pws.javafeatures.util.PrintUtil;

import java.util.BitSet;
import java.util.Random;

/**
 * BitSet 适用于高效率地大量存储大量“开/关”信息，这里的效率仅对空间而言；如果需要高效的访问时间，BitSet比本地数组稍慢
 * BitSet 的最小容量是long：64位，如果存储内容较小（例如8位），那么BitSet会浪费一些空间。
 * BitSet 会随着元素的加入而扩充容量
 *
 * @author panws
 * @since 2017-09-01
 */
public class BitSetTest {

	public static void main(String[] args) {

		Random random = new Random(47);


		/*
		 * byte
		 */
		byte bt = (byte) random.nextInt();
		BitSet bb = new BitSet();
		for (int i = 7; i >= 0; i--) {
			if (((1 << i) & bt) != 0) {
				bb.set(i);
			} else {
				bb.clear(i);
			}
		}
		PrintUtil.println("byte value: " + bt);
		printBitSet(bb);


		/*
		 * short
		 */
		short st = (short) random.nextInt();
		BitSet bs = new BitSet();
		for (int i = 15; i >= 0; i--) {
			if (((1 << i) & st) != 0) {
				bs.set(i);
			} else {
				bs.clear(i);
			}
		}
		PrintUtil.println("short value: " + st);
		printBitSet(bs);


		/*
		 * int
		 */
		int it = random.nextInt();
		BitSet bi = new BitSet();
		for (int i = 31; i >= 0; i--) {
			if (((1 << i) & it) != 0) {
				bi.set(i);
			} else {
				bi.clear(i);
			}
		}
		PrintUtil.println("int value: " + it);
		printBitSet(bi);


		/*
		 * bitsets >= 64 bits
		 */
		BitSet b127 = new BitSet();
		b127.set(127);
		PrintUtil.println("set bit 127: " + b127);

		BitSet b255 = new BitSet(65);
		b255.set(255);
		PrintUtil.println("set bit 255: " + b255);

		BitSet b1023 = new BitSet(512);
		b1023.set(1023);
		b1023.set(1024);
		PrintUtil.println("set bit 1023: " + b1023);
	}

	private static void printBitSet(BitSet bitSet) {

		PrintUtil.println(bitSet);

		StringBuilder stringBuilder = new StringBuilder();

		for (int j = 0; j < bitSet.size(); j++) {
			stringBuilder.append(bitSet.get(j) ? "1" : "0");
		}

		PrintUtil.println("bit pattern: " + stringBuilder);
	}
}
