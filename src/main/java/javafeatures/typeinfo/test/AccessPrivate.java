package javafeatures.typeinfo.test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 没有任何方式可以阻止反射到达并调用非公共访问权限的方法。
 * <p>
 * 域也如此，但final域在遭遇修改时是安全的，运行时系统会在不抛异常的情况下接受任何修改尝试，
 * 但实际上不会发生任何修改
 *
 * @author panws
 * @since 2017-10-09
 */
public class AccessPrivate {

	public static void main(String[] args)
			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, NoSuchFieldException {

		HiddenClass hidden = new HiddenClass();

		callHiddenMethod(hidden, "f");
		callHiddenMethod(hidden, "g");
		callHiddenMethod(hidden, "h");

		System.out.println("Before modified: " + hidden);
		modifyHiddenField(hidden, "i");
		modifyHiddenField(hidden, "s");
		modifyHiddenField(hidden, "s2");
		System.out.println("After modified: " + hidden);    //结果中i和s2被修改，s1未被修改
	}

	/**
	 * call hidden method
	 *
	 * @param obj
	 * @param methodName
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	private static void callHiddenMethod(Object obj, String methodName)
			throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

		Method method = obj.getClass().getDeclaredMethod(methodName);
		method.setAccessible(true);
		method.invoke(obj);
	}

	private static void modifyHiddenField(Object obj, String fieldName)
			throws NoSuchFieldException, IllegalAccessException {

		Field field = obj.getClass().getDeclaredField(fieldName);
		field.setAccessible(true);
		if (field.getType().equals(int.class)) {
			System.out.println(field + " = " + field.getInt(obj));
			field.setInt(obj, 2);
		} else {
			System.out.println(field + " = " + field.get(obj));
			field.set(obj, "modified");
		}
	}
}

class HiddenClass {

	private int i = 1;
	private final String s = "hi";
	private String s2 = "hello";

	public void f() {
		System.out.println("public void f()");
	}

	private void g() {
		System.out.println("private void g()");
	}

	void h() {
		System.out.println("package void h()");
	}

	@Override public String toString() {
		return "i=" + i + ". " + s + ". " + s2;
	}
}
