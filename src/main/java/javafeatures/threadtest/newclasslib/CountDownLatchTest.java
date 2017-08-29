package javafeatures.threadtest.newclasslib;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * CountDownLatch 用来同步一个或多个任务，强制它们等待有其他任务执行的一组操作完成
 * 典型用法将一个程序分成n个相互独立执行的可解决任务。每个任务完成计数值减一，等待问题被解决的任务阻塞直至锁存器计数结束
 *
 * @author panws
 * @since 2017-08-28
 */
public class CountDownLatchTest {

	private static int SIZE = 50;

	public static void main(String[] args) {

		CountDownLatch countDownLatch = new CountDownLatch(
				SIZE);    //设置一个初始计数器，countDown()会减小这个计数值，对await()的调用会被阻塞，直至计数值为0

		ExecutorService executorService = Executors.newCachedThreadPool();

		for (int i = 0; i < 5; i++) {
			executorService.execute(new WaitingTask(countDownLatch));
		}

		for (int i = 0; i < SIZE; i++) {
			executorService.execute(new TaskPortion(countDownLatch));
		}

		System.out.println("Launched all task");

		executorService.shutdown();

	}
}

class TaskPortion implements Runnable {

	private static int counter = 0;

	private int id = counter++;

	private static Random random = new Random(47);

	private final CountDownLatch countDownLatch;

	public TaskPortion(CountDownLatch countDownLatch) {
		this.countDownLatch = countDownLatch;
	}

	@Override public void run() {

		try {
			TimeUnit.MILLISECONDS.sleep(random.nextInt(1000));
			System.out.println(this + " complete.");
			countDownLatch.countDown();        //计数值减1
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	@Override public String toString() {
		return String.format("%1$-3d ", id);
	}
}

class WaitingTask implements Runnable {

	private static int counter = 0;

	private int id = counter++;

	private final CountDownLatch countDownLatch;

	public WaitingTask(CountDownLatch countDownLatch) {
		this.countDownLatch = countDownLatch;
	}

	@Override public void run() {
		try {
			countDownLatch.await();    //阻塞直至CountDownLatch计数值为0
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Latch barrier passed for " + this);
	}

	@Override public String toString() {
		return String.format("WaitingTask %1$-3d ", id);
	}
}
