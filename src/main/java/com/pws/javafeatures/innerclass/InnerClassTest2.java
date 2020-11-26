package com.pws.javafeatures.innerclass;

import com.pws.javafeatures.util.PrintUtil;

/**
 * 匿名内部类的使用
 *
 * @author panws
 * @since 2017-09-05
 */
public class InnerClassTest2 {

	public InnerInterface getInner(String s) {

		return new InnerInterface() {
			@Override public String read() {
				PrintUtil.println(s);
				return s;
			}

			@Override public int value() {
				return Integer.parseInt(s);
			}
		};
	}

	public InnerInterface getInnerWithParam(int n) {

		return new InnerObj(n) {
			@Override public int value() {
				//尽管InnerObj是一个具有具体实现的普通类，但它还是被导出类当做公共接口来使用，最终结果value为 n * 3
				int value = super.value() * 3;
				PrintUtil.println(value);
				return value;
			}
		};
	}

	public InnerInterface getInnerWithConstructor(int n) {

		return new Base(n) {

			//通过实例初始化，能够达到为匿名内部类创建一个构造器的效果
			{
				PrintUtil.println("Inside instance initializer");
			}

			@Override public String read() {
				PrintUtil.println("In anonymous read()");
				return null;
			}

			@Override public int value() {
				return 0;
			}
		};
	}

	public static void main(String[] args) {

		InnerClassTest2 innerClassTest2 = new InnerClassTest2();

		innerClassTest2.getInner("hello world").read();

		innerClassTest2.getInnerWithParam(5).value();

		innerClassTest2.getInnerWithConstructor(5).read();
	}
}

abstract class Base implements InnerInterface {
	public Base(int n) {
		PrintUtil.println("Base constructor. n = " + n);
	}
}
