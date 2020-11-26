package com.pws.javafeatures.java8;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import com.pws.javafeatures.util.PrintUtil;

/**
 * 方法引用使得开发者可以直接引用现存的方法、Java类的构造方法或者实例对象。
 *
 * @author panws
 * @since 2018-01-02
 */
public class ReferenceTest {

	public static void main(String[] args) {

		//第一种方法引用的类型是构造器引用，语法是Class::new，或者更一般的形式：Class<T>::new。注意：这个构造器没有参数。
		Car car = Car.create(Car::new);
		List<Car> cars = Arrays.asList(car);

		//第二种方法引用的类型是静态方法引用，语法是Class::static_method。注意：这个方法接受一个Car类型的参数。
		cars.forEach(Car::collide);

		//第三种方法引用的类型是某个类的成员方法的引用，语法是Class::method，注意，这个方法没有定义入参
		cars.forEach(Car::repair);

		//第四种方法引用的类型是某个实例对象的成员方法的引用，语法是instance::method。注意：这个方法接受一个Car类型的参数：
		cars.forEach(car::follow);
	}

	private static class Car {
		private static Car create(Supplier<Car> supplier) {
			return supplier.get();
		}

		private static void collide(Car car) {
			PrintUtil.println("Collided " + car.toString());
		}

		private void follow(Car another) {
			PrintUtil.println("Following the " + another.toString());
		}

		private void repair() {
			PrintUtil.println("Repaired " + this.toString());
		}
	}
}
