package javafeatures.typeinfo.test;

import javafeatures.util.PrintUtil;

/**
 * @author panws
 * @since 2017-09-26
 */
public class ToyTest {

	public static void main(String[] args) throws IllegalAccessException, InstantiationException {

		Class clazz = null;

		try {
			clazz = Class.forName("javafeatures.typeinfo.test.FancyToy");
		} catch (ClassNotFoundException e) {
			PrintUtil.println("Can't find FancyToy");
			System.exit(1);
		}

		printInfo(clazz);

		for (Class face : clazz.getInterfaces()) {
			printInfo(face);
		}

		Class up = clazz.getSuperclass();
		Object obj = null;
		try {
			obj = up.newInstance();
		} catch (IllegalAccessException e) {
			PrintUtil.println("Can't access");
			System.exit(1);
		} catch (InstantiationException e) {
			PrintUtil.println("Can't instantiate");
			System.exit(1);
		}
		printInfo(obj.getClass());

	}

	static void printInfo(Class clazz) {
		PrintUtil.println("Class name: " + clazz.getName() + " is interface? [" + clazz.isInterface() + "]");
		PrintUtil.println("Simple name: " + clazz.getSimpleName());
		PrintUtil.println("Canonical name: " + clazz.getCanonicalName());
	}
}

interface HasBatteries {

}

interface WaterProof {

}

interface Shoots {

}

class Toy {
	Toy() {

	}

	Toy(int i) {

	}
}

class FancyToy extends Toy implements HasBatteries, WaterProof, Shoots {

	FancyToy() {
		super(1);
	}
}
