package com.pws.javafeatures.typeinfo;

import com.pws.javafeatures.util.PrintUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author panws
 * @since 2017-09-27
 */
public class FilledList<T> {

	private Class<T> type;

	public FilledList(Class<T> type) {
		this.type = type;
	}

	public List<T> create(int element) {

		List<T> result = new ArrayList<>();

		try {
			for (int i = 0; i < element; i++) {
				result.add(type.newInstance());
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return result;
	}

	public static void main(String[] args) {

		FilledList<CountedInteger> list = new FilledList<>(CountedInteger.class);

		PrintUtil.println(list.create(10));
	}
}

class CountedInteger {

	private static long counter = 5;

	private final long id = counter++;

	@Override public String toString() {
		return Long.toString(id);
	}
}