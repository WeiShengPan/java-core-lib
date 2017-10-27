package javafeatures.thread.test;

import javafeatures.util.PrintUtil;

/**
 * Thread.holdsLock(Object obj)
 * Returns true if and only if the current thread holds the
 * monitor lock on the specified object.
 *
 * @author panws
 * @since 2017-07-25
 */
public class HoldsLockTest {

	public static void main(String[] args) throws InterruptedException {

		Object locker = new Object();

		MyThread myThread = new MyThread(locker);

		PrintUtil.println(Thread.currentThread().getName() + " holds lock:" + Thread.holdsLock(locker));

		myThread.start();

		myThread.join();

		PrintUtil.println(Thread.currentThread().getName() + " holds lock:" + Thread.holdsLock(locker));

	}

}

class MyThread extends Thread {

	Object locker;

	MyThread(Object locker) {
		this.locker = locker;
	}

	@Override public void run() {
		PrintUtil.println(Thread.currentThread().getName() + " holds lock:" + Thread.holdsLock(locker));
		synchronized (locker) {
			try {
				PrintUtil.println(Thread.currentThread().getName() + " holds lock:" + Thread.holdsLock(locker));
				sleep(5000);
				PrintUtil.println(Thread.currentThread().getName() + " holds lock:" + Thread.holdsLock(locker));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		PrintUtil.println(Thread.currentThread().getName() + " holds lock:" + Thread.holdsLock(locker));
	}
}
