package com.pws.javafeatures.enumeration;

import com.pws.javafeatures.util.PrintUtil;

import java.util.Random;

/**
 * 随机返回枚举类中的任意一个实例
 *
 * @author panws
 * @since 2017-08-18
 */
public class RandomEnumTest {

	private static Random rand = new Random(47);

	public static void main(String[] args) {

		for (int i = 0; i < 7; i++) {
			PrintUtil.println(random(EnumWeek.class));
		}
	}

	public static <T extends Enum<T>> T random(Class<T> ec) {
		return random(ec.getEnumConstants());
	}

	public static <T> T random(T[] values) {
		return values[rand.nextInt(values.length)];
	}
}
