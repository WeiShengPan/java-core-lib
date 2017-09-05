package javafeatures.innerclass.test;

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
		serviceConsumer(ServiceImpl1.factory);
		serviceConsumer(ServiceImpl2.factory);
	}
}

interface Service {

	void m1();

	void m2();
}

interface ServiceFactory {

	Service getService();
}

class ServiceImpl1 implements Service {

	private ServiceImpl1() {
	}

	@Override public void m1() {
		System.out.println("ServiceImpl1.m1");
	}

	@Override public void m2() {
		System.out.println("ServiceImpl1.m2");
	}

	//使用匿名内部类作为工厂
	public static ServiceFactory factory = () -> new ServiceImpl1();

}

class ServiceImpl2 implements Service {

	private ServiceImpl2() {
	}

	@Override public void m1() {
		System.out.println("ServiceImpl2.m1");
	}

	@Override public void m2() {
		System.out.println("ServiceImpl2.m2");
	}

	//使用匿名内部类作为工厂
	public static ServiceFactory factory = () -> new ServiceImpl2();

}
