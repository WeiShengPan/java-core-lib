package javafeatures.innerclass.test;

/**
 * 内部类的 .new 和 .this 用法
 *
 * @author panws
 * @since 2017-09-04
 */
public class InnerClassTest {

	public static void main(String[] args) {

		InnerClassTest innerClassTest = new InnerClassTest();

		/*
		 * 外部类获取内部类对象时，使用 .new 来创建
		 */
		Inner inner = innerClassTest.new Inner();

		/*
		 * 静态内部类则不需要外部对象的引用，不需要使用 .new 来创建
		 */
		StaticInner staticInner = new StaticInner();
	}

	private class Inner {

		public InnerClassTest useOuter() {

			/*
			 * 内部类使用外部类时，使用 .this 来操作
			 */
			return InnerClassTest.this;
		}
	}

	private static class StaticInner {

	}

}
