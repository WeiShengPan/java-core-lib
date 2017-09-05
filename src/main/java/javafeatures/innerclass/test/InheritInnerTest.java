package javafeatures.innerclass.test;

import java.util.ArrayList;

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
		System.out.println("InheritInner");
	}

	public static void main(String[] args) {
		WithInner withInner = new WithInner();
		InheritInnerTest ii = new InheritInnerTest(withInner);
	}

}

class WithInner {

	WithInner() {
		System.out.println("WithInner");
	}

	class Inner {
		Inner() {
			System.out.println("Inner");
		}
	}

}
