package javafeatures.innerclass.test;

import javafeatures.util.PrintUtil;

/**
 * 内部类的继承
 * <p>
 * 因为内部类的构造器必须连接到指向其外围类对象的引用，所以指向其外围类对象的引用必须被初始化
 *
 * @author panws
 * @since 2017-09-05
 */
public class InheritInnerTest extends WithInner.Inner {

	InheritInnerTest(WithInner withInner) {
		withInner.super();
		PrintUtil.println("InheritInner");
	}

	public static void main(String[] args) {
		WithInner withInner = new WithInner();
		InheritInnerTest ii = new InheritInnerTest(withInner);
	}

}

class WithInner {

	WithInner() {
		PrintUtil.println("WithInner");
	}

	class Inner {
		Inner() {
			PrintUtil.println("Inner");
		}
	}

}
