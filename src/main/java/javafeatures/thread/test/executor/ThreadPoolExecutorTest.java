package javafeatures.thread.test.executor;

import javafeatures.util.PrintUtil;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author panws
 * @since 2017-11-03
 */
public class ThreadPoolExecutorTest {

	public static void main(String[] args) throws InterruptedException {

		CustomRejectExecutionHandler rejectExecutionHandler = new CustomRejectExecutionHandler();

		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(3, 6, 10, TimeUnit.SECONDS,
				new ArrayBlockingQueue<>(10), rejectExecutionHandler);

		for (int i = 1; i <= 25; i++) {
			threadPoolExecutor.execute(new Worker(i));
			Thread.sleep(20);
			if (i != 0 && i % 5 == 0) {
				Thread.sleep(1000);
				PrintUtil.printSep();
			}
		}

		Thread.sleep(50000);
		threadPoolExecutor.shutdown();
	}

}

class CustomRejectExecutionHandler implements RejectedExecutionHandler {

	@Override public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
		PrintUtil.println(r + " is rejected !");
	}
}

class Worker implements Runnable {

	int num;

	public Worker(int num) {
		this.num = num;
	}

	@Override public void run() {
		PrintUtil.println("Worker :" + num);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override public String toString() {
		return "Worker{" + "num=" + num + '}';
	}
}
