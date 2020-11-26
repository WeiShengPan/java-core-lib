package com.pws.javafeatures.generic;

/**
 * 擦除：泛型使用擦除实现
 *
 * @author panws
 * @since 2017-10-12
 */
public class ErasedTest {
}

class Erased<T> {

	private final int SIZE = 100;

	public void f(Object arg) {

		//		if (arg instanceof T) {}
		//		T temp = new T();

		T temp = (T) new Object();
	}
}

