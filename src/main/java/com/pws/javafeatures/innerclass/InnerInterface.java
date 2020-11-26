package com.pws.javafeatures.innerclass;

import com.pws.javafeatures.util.PrintUtil;

/**
 * 示例内部类实现的接口
 *
 * @author panws
 * @since 2017-09-04
 */
public interface InnerInterface {

	/**
	 * read
	 * @return
	 */
	String read();

	/** value
	 * @return
	 */
	int value();

	/**
	 * 嵌套类还可以在接口内定义，由于存在于接口内，自动为public和static
	 */
	class ClassInInterface implements InnerInterface {

		@Override public String read() {
			PrintUtil.println("ClassInInterface");
			return null;
		}

		@Override public int value() {
			return 0;
		}

		public static void main(String[] args) {
			new ClassInInterface().read();
		}
	}

}
