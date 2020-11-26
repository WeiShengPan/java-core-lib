package com.pws.javafeatures.thread;

import com.pws.javafeatures.util.PrintUtil;

/**
 * @author panws
 * @since 2017-07-24
 */
public class WaitNotifyTest {

	public static void main(String[] args) throws InterruptedException {
		Object a = new Object();
		Object b = new Object();
		Object c = new Object();
		WaitNotifyTread pa = new WaitNotifyTread("A", c, a);
		WaitNotifyTread pb = new WaitNotifyTread("B", a, b);
		WaitNotifyTread pc = new WaitNotifyTread("C", b, c);

		//每个线程开始后，休眠一小段时间，确保按顺序A、B、C执行
		new Thread(pa).start();
		Thread.sleep(100);
		new Thread(pb).start();
		Thread.sleep(100);
		new Thread(pc).start();
		Thread.sleep(100);
	}
}


class WaitNotifyTread implements Runnable {

	private final String name;
	private final Object prev;
	private final Object self;

	public WaitNotifyTread(String name, Object prev, Object self) {
		this.name = name;
		this.prev = prev;
		this.self = self;
	}

	@Override public void run() {
		int count = 10;
		while (count > 0) {
			synchronized (prev) {
				synchronized (self) {
					PrintUtil.print(name);
					count--;
					self.notifyAll();
				}
				try {
					prev.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
