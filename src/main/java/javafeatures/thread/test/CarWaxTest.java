package javafeatures.thread.test;

import javafeatures.util.PrintUtil;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author panws
 * @since 2017-08-25
 */
public class CarWaxTest {

	public static void main(String[] args) throws InterruptedException {

		Car car = new Car();

		ExecutorService executorService = Executors.newCachedThreadPool();

		executorService.execute(new WaxOn(car));
		executorService.execute(new BuffOn(car));

		TimeUnit.SECONDS.sleep(5);

		executorService.shutdownNow();
	}
}

/**
 * 抛光、打蜡必须交替进行
 */
class Car {

	private boolean waxOn = false;

	/**
	 * 抛光
	 */
	public void wax() {
		synchronized (this) {
			waxOn = true;
			notifyAll();
		}
	}

	/**
	 * 打蜡
	 */
	public void buff() {
		synchronized (this) {
			waxOn = false;
			notifyAll();
		}
	}

	/**
	 * 等待抛光
	 *
	 * @throws InterruptedException
	 */
	public void waitForWax() throws InterruptedException {
		synchronized (this) {
			while (waxOn == false) {
				wait();
			}
		}
	}

	/**
	 * 等待打蜡
	 *
	 * @throws InterruptedException
	 */
	public void waitForBuff() throws InterruptedException {
		synchronized (this) {
			while (waxOn == true) {
				wait();
			}
		}
	}
}

class WaxOn implements Runnable {

	private Car car;

	public WaxOn(Car car) {
		this.car = car;
	}

	@Override public void run() {
		try {
			while (!Thread.interrupted()) {
				PrintUtil.println("Wax");
				TimeUnit.MILLISECONDS.sleep(200);
				car.wax();
				car.waitForBuff();
			}
		} catch (InterruptedException e) {
			PrintUtil.println(e);
		}
	}
}

class BuffOn implements Runnable {

	private Car car;

	public BuffOn(Car car) {
		this.car = car;
	}

	@Override public void run() {

		try {
			while (!Thread.interrupted()) {
				car.waitForWax();
				PrintUtil.println("Buff");
				TimeUnit.MILLISECONDS.sleep(200);
				car.buff();
			}
		} catch (InterruptedException e) {
			PrintUtil.println(e);
		}

	}
}


