package com.pws.javafeatures.generic;

import java.util.Collection;

/**
 * @author panws
 * @since 2017-10-12
 */
public class Generators {

	public static <T> Collection<T> fill(Collection<T> coll, Generator<T> gen, int n) {

		for (int i = 0; i < n; i++) {
			coll.add(gen.next());
		}

		return coll;
	}
}

interface Generator<T> {

	/**
	 * next
	 *
	 * @return
	 */
	T next();
}


