package javafeatures.thread.test;

import javafeatures.util.PrintUtil;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * 后台线程，不属于程序中不可或缺的部分。所以当所有非后台线程结束时，程序就终止了，同时杀死进程中的所有后台线程。
 *
 * @author panws
 * @since 2017-08-21
 */
public class DaemonTest {

	public static void main(String[] args) throws InterruptedException {

		ExecutorService executorService = Executors.newCachedThreadPool(new DaemonThreadFactory());

		for (int i = 0; i < 10; i++) {
			executorService.execute(new DaemonThread());
		}

		PrintUtil.println("All daemon started.");

		//将主线程休眠一小段时间的意义在于可观察后台线程的执行情况。
		//当主线程结束时，所有非后台线程就都结束了，同时会杀死所有的后台线程。
		TimeUnit.MILLISECONDS.sleep(100);

	}
}

class DaemonThreadFactory implements ThreadFactory {

	@Override public Thread newThread(Runnable r) {
		Thread thread = new Thread(r);

		//将所有线程设置为后台线程
		thread.setDaemon(true);

		return thread;
	}
}

class DaemonThread implements Runnable {

	@Override public void run() {
		try {
			while (true) {
				TimeUnit.MILLISECONDS.sleep(100);
				PrintUtil.println(Thread.currentThread() + " " + this);
			}
		} catch (InterruptedException e) {
			System.err.println("Interrupted.");
		}
	}
}
