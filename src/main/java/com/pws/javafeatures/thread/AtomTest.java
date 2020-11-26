package com.pws.javafeatures.thread;

import com.pws.javafeatures.util.PrintUtil;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 原子性测试 要强调的是Atomic类被设计用来构建java.util.concurrent中的类，因此只有特殊情况下才在自己的代码中使用。通常依赖于锁更安全一点
 *
 * @author panws
 * @since 2017-08-23
 */
public class AtomTest {

	public static void main(String[] args) {

		new Timer().schedule(new TimerTask() {
			@Override public void run() {
				System.err.println("Abort.");
				System.exit(0);

			}
		}, 10000);

		ExecutorService executorService = Executors.newCachedThreadPool();

		AtomInt atomInt = new AtomInt();

		executorService.execute(atomInt);

		while (true) {
			int val = atomInt.getValue();
			if (val % 2 != 0) {
				PrintUtil.println(val);
				System.exit(0);
			}
		}
	}
}

class AtomInt implements Runnable {

	private AtomicInteger i = new AtomicInteger(0);

	public int getValue() {
		return i.get();
	}

	private void incr() {
		i.addAndGet(2);
	}

	@Override public void run() {
		while (true) {
			incr();
		}
	}
}
