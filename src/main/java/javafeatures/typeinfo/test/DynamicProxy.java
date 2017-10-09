package javafeatures.typeinfo.test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 动态代理
 * <p>
 * 通过调用静态方法Proxy.newProxyInstance()创建动态代理，这个方法需要一个类加载器，
 * 一个希望该代理实现的接口列表，以及一个InvocationHandler接口的实现
 * <p>
 * 动态代理可以将所有调用重定向到调用处理器，因此通常会向调用处理器的构造器传递一个
 * 对象的引用，从而使得调用处理器在执行其中介任务时，可以将请求转发。
 *
 * @author panws
 * @since 2017-09-30
 */
public class DynamicProxy {

	public static void main(String[] args) {

		RealObject real = new RealObject();

		consume(real);

		Interface proxy = (Interface) Proxy
				.newProxyInstance(Interface.class.getClassLoader(), new Class[] { Interface.class },
						new DynamicProxyHandler(real));

		consume(proxy);

	}

	public static void consume(Interface iface) {
		iface.doSomething();
		iface.doSomethingElse("hi");
	}
}

/**
 * 调用处理器
 */
class DynamicProxyHandler implements InvocationHandler {

	private Object proxied;

	public DynamicProxyHandler(Object proxied) {
		this.proxied = proxied;
	}

	@Override public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

		System.out.println("**** proxy: " + proxy.getClass() + ". method:  " + method + ". args: " + args);

		if (args != null) {
			for (Object arg : args) {
				System.out.println(" " + arg);
			}
		}

		return method.invoke(proxied, args);    //先执行代理的操作，最后使用method.invoke将请求转发给被代理的对象
	}
}

interface Interface {

	void doSomething();

	void doSomethingElse(String arg);
}

class RealObject implements Interface {

	@Override public void doSomething() {
		System.out.println("doSomething");
	}

	@Override public void doSomethingElse(String arg) {
		System.out.println("doSomethingElse " + arg);
	}
}
