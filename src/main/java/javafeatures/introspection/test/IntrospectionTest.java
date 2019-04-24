package javafeatures.introspection.test;

import javafeatures.util.PrintUtil;

import java.beans.*;

/**
 * 自省
 *
 * @author panws
 * @since 2019-04-24
 */
public class IntrospectionTest {

	public static void main(String[] args) throws Exception {

		Test4Introspection<String, Integer> instance = new Test4Introspection<>();

		BeanInfo beanInfo = Introspector.getBeanInfo(Test4Introspection.class);

		BeanDescriptor beanDescriptor = beanInfo.getBeanDescriptor();
		PrintUtil.println("Bean Class: %s", beanDescriptor.getBeanClass());
		PrintUtil.println("Customizer Class: %s", beanDescriptor.getCustomizerClass());

		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (PropertyDescriptor pd : propertyDescriptors) {
			switch (pd.getName()) {
				case "f1":
					pd.getWriteMethod().invoke(instance, "field1");
					PrintUtil.println("Field 'f1' value: %s", pd.getReadMethod().invoke(instance));
					break;
				case "f2":
					pd.getWriteMethod().invoke(instance, 2);
					PrintUtil.println("Field 'f2' value: %d", pd.getReadMethod().invoke(instance));
					break;
				default:
					PrintUtil.println("Unrecognized field: %s", pd.getName());
			}
		}

		MethodDescriptor[] methodDescriptors = beanInfo.getMethodDescriptors();
		for (MethodDescriptor md : methodDescriptors) {
			PrintUtil.println("Method: %s", md.getMethod().getName());
		}

	}

	private static class Test4Introspection<T, F> {

		public T f1;

		private F f2;

		public T getF1() {
			return f1;
		}

		public void setF1(T f1) {
			this.f1 = f1;
		}

		public F getF2() {
			return f2;
		}

		public void setF2(F f2) {
			this.f2 = f2;
		}

		public String test4It() {
			return "yes";
		}
	}

}
