package javafeatures.typeinfo.test;

import java.util.Random;

/**
 * @author panws
 * @since 2017-09-26
 */
public class ClassInitialization {

	public static Random random = new Random(47);

	public static void main(String[] args) throws ClassNotFoundException {

		Class initable = Initable.class;    //仅适用.class来获取对类的引用不会引发初始化
		System.out.println("After creating Initable ref");

		System.out.println(Initable.staticFinal);    //声明为static final的值是编译期常量，不需要对类进行初始化就可以被读取
		System.out.println(Initable.statifFinal2);

		System.out.println(Initable2.staticNonFinal);    //声明为static非final的常量，在被读取前需要进行链接(为这个域分配存储空间)和初始化(初始化存储空间)

		Class initable3 = Class.forName("javafeatures.typeinfo.test.Initable3");    //适用Class.forName()来获取类的引用会立即进行初始化
		System.out.println("After creating Initable3 ref");
		System.out.println(Initable3.staticNonFinal);

	}
}

class Initable {

	static final int staticFinal = 47;
	static final int statifFinal2 = ClassInitialization.random.nextInt(1000);

	static {
		System.out.println("Initializing Initable");
	}
}

class Initable2 {

	static int staticNonFinal = 147;

	static {
		System.out.println("Initializing Initable2");
	}
}

class Initable3 {

	static int staticNonFinal = 74;

	static {
		System.out.println("Initializing Initable3");
	}
}

