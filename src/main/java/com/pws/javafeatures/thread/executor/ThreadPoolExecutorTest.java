package com.pws.javafeatures.thread.executor;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.pws.javafeatures.util.PrintUtil;

/**
 * 运行机制上，简单来说：
 * <p>
 * 1. 当一个任务提交，如果线程池中的线程 < corePoolSize，则创建一个新线程；
 * 2. 当一个任务提交，如果线程池中的线程 >= corePoolSize，且workQueue未满，则将任务加入workQueue中排队，不创建新线程；
 * 3. 当一个任务提交，如果线程池中的线程 >= corePoolSize，且workQueue已满，且线程池中的线程< maximumPoolSize，则创建新线程；
 * 4. 当一个任务提交，如果线程池中的线程 = maximumPoolSize，且workQueue已满，则拒绝该任务，将任务交给RejectedExecutionHandler处理。
 *
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

	@Override
	public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
		PrintUtil.println(r + " is rejected !");
	}
}

class Worker implements Runnable {

	int num;

	public Worker(int num) {
		this.num = num;
	}

	@Override
	public void run() {
		PrintUtil.println("Worker :" + num);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		return "Worker{" + "num=" + num + '}';
	}
}
