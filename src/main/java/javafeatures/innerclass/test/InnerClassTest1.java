package javafeatures.innerclass.test;

import javafeatures.util.PrintUtil;

/**
 * 定义在方法中的内部类
 *
 * @author panws
 * @since 2017-09-04
 */
public class InnerClassTest1 {

	public InnerInterface getInner(String s) {

		class Inner implements InnerInterface {

			private String label;

			private Inner(String s) {
				label = s;
			}

			@Override public String read() {
				return label;
			}

			@Override public int value() {
				return 0;
			}
		}

		return new Inner(s);

	}

	public void getInner(boolean b) {

		if (b) {
			class Inner implements InnerInterface {

				private String label;

				private Inner(String s) {
					label = s;
				}

				@Override public String read() {
					return label;
				}

				@Override public int value() {
					return 0;
				}
			}

			//作用域中可以正常使用内部类
			Inner inner = new Inner("hello");
		}

		/*
		 * 定义在作用域中的内部类只在作用域中有效，作用域外无法使用
		 */
		//Inner inner = new Inner("hello");
	}

	public static void main(String[] args) {

		InnerClassTest1 instance = new InnerClassTest1();

		InnerInterface inner = instance.getInner("hello world");

		PrintUtil.println(inner.read());

	}

}
