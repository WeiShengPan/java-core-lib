package com.pws.javafeatures.java8;

import java.lang.annotation.*;

import com.pws.javafeatures.util.PrintUtil;

/**
 * 在Java 8中使用@Repeatable注解定义重复注解，实际上，这并不是语言层面的改进，而是编译器做的一个trick，底层的技术仍然相同。
 * <p>
 * 这里的{@link Filter}类使用@Repeatable(FilterContainer.class)注解修饰，而{@link FilterContainer}是存放{@link Filter}注解的容器，编译器尽量对开发者屏蔽这些细节。
 * 这样，{@link Filterable}接口可以用两个{@link Filter}注解注释（这里并没有提到任何关于{@link FilterContainer}的信息）
 * <p>
 * 同时，反射API提供了一个新的方法：{@link Class}类中的getAnnotationsByType()，可以返回某个类型的重复注解
 *
 * @author panws
 * @since 2018-01-02
 */
public class RepeatableAnnotationTest {

	public static void main(String[] args) {
		//反射API提供了一个新的方法：getAnnotationsByType()，可以返回某个类型的重复注解
		for (Filter filter : Filterable.class.getAnnotationsByType(Filter.class)) {
			PrintUtil.println(filter.value());
		}
	}

	@Target(ElementType.TYPE)
	@Retention(RetentionPolicy.RUNTIME)
	private @interface FilterContainer {
		Filter[] value();
	}

	@Target(ElementType.TYPE)
	@Retention(RetentionPolicy.RUNTIME)
	@Repeatable(FilterContainer.class)
	private @interface Filter {
		String value();
	}

	@Filter("filter1")
	@Filter("filter2")
	private interface Filterable {
	}

}
