package javafeatures.java8;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import javafeatures.util.PrintUtil;

/**
 * Java 8在语言层面（使用反射API和Parameter.getName()方法）和字节码层面（使用新的javac编译器以及-parameters参数）
 * 提供支持运行时获取方法的参数名称。
 *
 * @author panws
 * @since 2018-01-02
 */
public class ParameterNameTest {

	public static void main(String[] args) throws NoSuchMethodException {

		Method method = ParameterNameTest.class.getDeclaredMethod("testMethod", String.class, int.class, boolean.class);

		for (final Parameter parameter : method.getParameters()) {
			PrintUtil.println("Parameter: ", parameter.getName());
		}

	}

	private void testMethod(String str, int i, boolean bool) {

	}
}
