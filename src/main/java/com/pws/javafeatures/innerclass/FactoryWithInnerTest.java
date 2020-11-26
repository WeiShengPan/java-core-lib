package com.pws.javafeatures.innerclass;

import com.pws.javafeatures.util.PrintUtil;

/**
 * 工厂方法与匿名内部类的结合
 *
 * @author panws
 * @since 2017-09-05
 */
public class FactoryWithInnerTest {

	private static void serviceConsumer(ServiceFactory sf) {
		Service service = sf.getService();
		service.m1();
		service.m2();
	}

	public static void main(String[] args) {
		serviceConsumer(Service1Impl.FACTORY);
		serviceConsumer(Service2Impl.FACTORY);
	}
}

interface Service {

	/**
	 * m1
	 */
	void m1();

	/**
	 * m2
	 */
	void m2();
}

interface ServiceFactory {

	/**
	 * get service
	 * @return
	 */
	Service getService();
}

class Service1Impl implements Service {

	private Service1Impl() {
	}

	@Override public void m1() {
		PrintUtil.println("Service1Impl.m1");
	}

	@Override public void m2() {
		PrintUtil.println("Service1Impl.m2");
	}

	/**
	 * 使用匿名内部类作为工厂
	 */
	static final ServiceFactory FACTORY = Service1Impl::new;

}

class Service2Impl implements Service {

	private Service2Impl() {
	}

	@Override public void m1() {
		PrintUtil.println("Service2Impl.m1");
	}

	@Override public void m2() {
		PrintUtil.println("Service2Impl.m2");
	}

	/**
	 * 使用匿名内部类作为工厂
	 */
	static final ServiceFactory FACTORY = Service2Impl::new;

}
