package javafeatures.exception.test;

/**
 * 类的继承关系中，异常的抛出
 *
 * @author panws
 * @since 2017-09-14
 */
public class ExceptionLimit {

	public static void main(String[] args) throws OneException, TwoException {

		Child child = new Child();
		child.throwOne();
		child.throwTwo();
		child.normalOne();
		child.normalTwo();
	}
}

abstract class Base {

	public void throwOne() throws OneException {
	}

	public abstract void throwTwo() throws TwoException;

	public void normalOne() {
	}

	public abstract void normalTwo();
}

class Child extends Base {

	@Override public void throwOne() throws OneException {
		throw new OneException();
		//不能抛出TwoException，因为父类的方法没有抛出该异常
		//throw new TwoException();
	}

	@Override public void throwTwo() throws TwoException {
		//不能抛出OneException，因为父类的方法没有抛出该异常
		//throw new OneException();
		throw new TwoException();
	}

	@Override public void normalOne() {
		//不能抛出OneException和TwoException，因为父类的方法没有抛出这两个异常
		//throw new OneException();
		//throw new TwoException();
	}

	@Override public void normalTwo() {
		//不能抛出OneException和TwoException，因为父类的方法没有抛出这两个异常
		//throw new OneException();
		//throw new TwoException();
	}
}

class OneException extends Exception {
}

class TwoException extends Exception {
}
