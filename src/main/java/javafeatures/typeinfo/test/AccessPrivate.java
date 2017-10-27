package javafeatures.typeinfo.test;

import javafeatures.util.PrintUtil;

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

		PrintUtil.println("Before modified: " + hidden);
		modifyHiddenField(hidden, "i");
		modifyHiddenField(hidden, "s");
		modifyHiddenField(hidden, "s2");
		//结果中i和s2被修改，s1未被修改
		PrintUtil.println("After modified: " + hidden);
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

	/**
	 * modify hidden field
	 *
	 * @param obj
	 * @param fieldName
	 * @throws NoSuchFieldException
	 * @throws IllegalAccessException
	 */
	private static void modifyHiddenField(Object obj, String fieldName)
			throws NoSuchFieldException, IllegalAccessException {

		Field field = obj.getClass().getDeclaredField(fieldName);
		field.setAccessible(true);
		if (field.getType().equals(int.class)) {
			PrintUtil.println(field + " = " + field.getInt(obj));
			field.setInt(obj, 2);
		} else {
			PrintUtil.println(field + " = " + field.get(obj));
			field.set(obj, "modified");
		}
	}
}

class HiddenClass {

	private int i = 1;
	private final String s = "hi";
	private String s2 = "hello";

	public void f() {
		PrintUtil.println("public void f()");
	}

	private void g() {
		PrintUtil.println("private void g()");
	}

	void h() {
		PrintUtil.println("package void h()");
	}

	@Override public String toString() {
		return "i=" + i + ". " + s + ". " + s2;
	}
}
