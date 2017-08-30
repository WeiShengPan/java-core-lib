package java.features.annotation.test;

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

				System.out.println(method + ": " + testCase.id() + ": " + testCase.desc());
			}
		}
	}

	@TestCase(id = 1, desc = "m1") public void m1() {

		System.out.println("m1");
	}

	public void m2() {
		System.out.println("m2");
	}

	@TestCase(desc = "m3") public void m3() {
		System.out.println("m3");
	}

	@TestCase(id = 4, desc = "m4") public void m4() {
		System.out.println("m4");
	}
}
