package com.pws.javafeatures.annotation;

import com.pws.javafeatures.util.PrintUtil;

import java.lang.reflect.Method;

/**
 * @author panws
 * @since 2017-08-18
 */
public class TestCaseService {

	public static void main(String[] args) {

		for (Method method : TestCaseService.class.getDeclaredMethods()) {

			TestCase testCase = method.getAnnotation(TestCase.class);

			if (testCase != null) {

				PrintUtil.println(method + ": " + testCase.id() + ": " + testCase.desc());
			}

			if (method.isAnnotationPresent(TestCase.class)) {

				PrintUtil.println("#" + method + ": " + testCase.id() + ": " + testCase.desc());
			}
		}
	}

	@TestCase(id = 1, desc = "m1") public void m1() {

		PrintUtil.println("m1");
	}

	public void m2() {
		PrintUtil.println("m2");
	}

	@TestCase(desc = "m3") public void m3() {
		PrintUtil.println("m3");
	}

	@TestCase(id = 4, desc = "m4") public void m4() {
		PrintUtil.println("m4");
	}
}
