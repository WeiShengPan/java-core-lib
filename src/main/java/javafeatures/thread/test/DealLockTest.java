package javafeatures.thread.test;

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

	public void run() {
		synchronized (lockA) {
			System.out.println(this.getClass().getName() + " holds lockA.");
			try {
				sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			synchronized (lockB) {
				System.out.println(this.getClass().getName() + " holds lockB.");
			}
			System.out.println(this.getClass().getName() + " releases lockB.");
		}
		System.out.println(this.getClass().getName() + " releases lockA.");
	}
}

class ThreadB extends Thread {

	private Object lockA;

	private Object lockB;

	ThreadB(Object lockA, Object lockB) {
		this.lockA = lockA;
		this.lockB = lockB;
	}

	public void run() {
		synchronized (lockB) {
			System.out.println(this.getClass().getName() + " holds lockB.");
			try {
				sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			synchronized (lockA) {
				System.out.println(this.getClass().getName() + " holds lockA.");
			}
			System.out.println(this.getClass().getName() + " releases lockA.");
		}
		System.out.println(this.getClass().getName() + " releases lockB.");
	}
}
