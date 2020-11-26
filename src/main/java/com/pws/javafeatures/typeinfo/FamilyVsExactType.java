package com.pws.javafeatures.typeinfo;

import com.pws.javafeatures.util.PrintUtil;

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

		PrintUtil.println("Testing o of type: " + o.getClass());
		PrintUtil.println("o instanceof Base: " + (o instanceof Base));
		PrintUtil.println("o instanceof Derived: " + (o instanceof Derived));
		PrintUtil.println("Base.isInstance(o): " + (Base.class.isInstance(o)));
		PrintUtil.println("Derived.isInstance(o): " + (Derived.class.isInstance(o)));
		PrintUtil.println("o.getClass() == Base.class: " + (o.getClass() == Base.class));
		PrintUtil.println("o.getClass() == Derived.class: " + (o.getClass() == Derived.class));
		PrintUtil.println("o.getClass().equals(Base.class): " + (o.getClass().equals(Base.class)));
		PrintUtil.println("o.getClass().equals(Derived.class): " + (o.getClass().equals(Derived.class)));

		PrintUtil.println("**********");
	}
}

class Base {

}

class Derived extends Base {

}
