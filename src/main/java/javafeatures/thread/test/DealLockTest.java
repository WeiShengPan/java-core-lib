package javafeatures.thread.test;

import javafeatures.util.PrintUtil;

/**
 * 死锁
 *
 * @author panws
 * @since 2017-07-26
 */
public class DealLockTest {

	public static void main(String[] args) {

		Object lockA = new Object();
		Object lockB = new Object();

		ThreadA threadA = new ThreadA(lockA, lockB);
		ThreadB threadB = new ThreadB(lockA, lockB);

		threadA.start();
		threadB.start();

	}

}

class ThreadA extends Thread {

	private Object lockA;

	private Object lockB;

	ThreadA(Object lockA, Object lockB) {
		this.lockA = lockA;
		this.lockB = lockB;
	}

	@Override public void run() {
		synchronized (lockA) {
			PrintUtil.println(this.getClass().getName() + " holds lockA.");
			try {
				sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			synchronized (lockB) {
				PrintUtil.println(this.getClass().getName() + " holds lockB.");
			}
			PrintUtil.println(this.getClass().getName() + " releases lockB.");
		}
		PrintUtil.println(this.getClass().getName() + " releases lockA.");
	}
}

class ThreadB extends Thread {

	private Object lockA;

	private Object lockB;

	ThreadB(Object lockA, Object lockB) {
		this.lockA = lockA;
		this.lockB = lockB;
	}

	@Override public void run() {
		synchronized (lockB) {
			PrintUtil.println(this.getClass().getName() + " holds lockB.");
			try {
				sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			synchronized (lockA) {
				PrintUtil.println(this.getClass().getName() + " holds lockA.");
			}
			PrintUtil.println(this.getClass().getName() + " releases lockA.");
		}
		PrintUtil.println(this.getClass().getName() + " releases lockB.");
	}
}
