package javafeatures.typeinfo.test;

/**
 * instanceof 和 Class.equal() 的区别
 * instanceof保持了类型的概念，考虑继承；equal只比较实际的Class对象，不考虑继承
 *
 * @author panws
 * @since 2017-09-29
 */
public class FamilyVsExactType {

	public static void main(String[] args) {
		test(new Base());
		test(new Derived());
	}

	static void test(Object o) {

		System.out.println("Testing o of type: " + o.getClass());
		System.out.println("o instanceof Base: " + (o instanceof Base));
		System.out.println("o instanceof Derived: " + (o instanceof Derived));
		System.out.println("Base.isInstance(o): " + (Base.class.isInstance(o)));
		System.out.println("Derived.isInstance(o): " + (Derived.class.isInstance(o)));
		System.out.println("o.getClass() == Base.class: " + (o.getClass() == Base.class));
		System.out.println("o.getClass() == Derived.class: " + (o.getClass() == Derived.class));
		System.out.println("o.getClass().equals(Base.class): " + (o.getClass().equals(Base.class)));
		System.out.println("o.getClass().equals(Derived.class): " + (o.getClass().equals(Derived.class)));

		System.out.println("**********");
	}
}

class Base {

}

class Derived extends Base {

}
