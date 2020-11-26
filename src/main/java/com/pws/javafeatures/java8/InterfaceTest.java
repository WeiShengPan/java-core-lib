package com.pws.javafeatures.java8;

import java.util.function.Supplier;

import com.pws.javafeatures.util.PrintUtil;

/**
 * Java 8使用两个新概念扩展了接口的含义：默认方法和静态方法。
 * <p>
 *
 * @author panws
 * @since 2018-01-02
 */
public class InterfaceTest {

	public static void main(String[] args) {

		new DefaultImpl().defaultable();
		new DefaultOverriddenImpl().defaultable();

		Statical.statical();
		Defaultable defaultImpl = Statical.create(DefaultImpl::new);
		Defaultable defaultOverriddenImpl = Statical.create(DefaultOverriddenImpl::new);
		defaultImpl.defaultable();
		defaultOverriddenImpl.defaultable();

	}

	/**
	 * 默认方法使得开发者可以在不破坏二进制兼容性的前提下，往现存接口中添加新的方法，
	 * 即不强制那些实现了该接口的类也同时实现这个新加的方法。默认方法必须在接口中有实现
	 */
	private interface Defaultable {

		/**
		 * default method in interface
		 */
		default void defaultable() {
			PrintUtil.println("Default method");
		}

	}

	private static class DefaultImpl implements Defaultable {
	}

	private static class DefaultOverriddenImpl implements Defaultable {
		@Override
		public void defaultable() {
			PrintUtil.println("Default method implementation");
		}
	}

	/**
	 * 接口中可以有静态方法，必须在接口中有实现
	 */
	private interface Statical {

		/**
		 * static method in interface
		 */
		static void statical() {
			PrintUtil.println("Static method");
		}

		/**
		 * static method : create a instance which implement {@link Defaultable}
		 *
		 * @param supplier
		 * @return
		 */
		static Defaultable create(Supplier<Defaultable> supplier) {
			return supplier.get();
		}
	}

}

