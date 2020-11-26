package com.pws.javafeatures.innerclass;

/**
 * @author panws
 * @since 2017-09-05
 */
public class InnerObj implements InnerInterface {

	private int n;

	public InnerObj(int n) {
		this.n = n;
	}

	@Override public int value() {
		return n;
	}

	@Override public String read() {
		return String.valueOf(n);
	}
}
