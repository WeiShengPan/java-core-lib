package com.pws.jvm;

import java.lang.reflect.Field;

import sun.misc.Unsafe;

/**
 * 本机直接内存溢出
 * <p>
 * 本机直接内存通过-XX:MaxDirectMemorySize指定，如果不指定，默认与java堆最大值（-Xmx）一样。
 * 由DirectMemory导致的内存溢出，一个明显的特征是在Heap Dump文件中不会看见明显的异常，如果
 * 发现OOM之后Dump文件很小，而程序中又直接或间接使用了NIO，可以考虑下是否为这方面原因。
 * <p>
 * vm args：-Xmx20M -XX:MaxDirectMemorySize=10M
 *
 * @author panws
 * @since 2017-12-21
 */
public class DirectMemoryOom {

	private static final int ONE_MB = 1024 * 1024;

	public static void main(String[] args) throws IllegalAccessException {
		Field unsafeField = Unsafe.class.getDeclaredFields()[0];
		unsafeField.setAccessible(true);
		Unsafe unsafe = (Unsafe) unsafeField.get(null);
		while (true) {
			unsafe.allocateMemory(ONE_MB);
		}
	}
}
