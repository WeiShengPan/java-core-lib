package java.features.thread.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 仿真程序，计算每天通过各个大门进去公园的总人数。每个大门都有一个十字转门Entrance；
 * 任何一个十字转门的计数值递增时，表示公园中的总人数的共享计数器Counter计数值也会递增
 *
 * @author panws
 * @since 2017-08-24
 */
public class CancelTest {

	public static void main(String[] args) throws InterruptedException {

		ExecutorService executorService = Executors.newCachedThreadPool();

		for (int i = 1; i < 6; i++) {
			executorService.execute(new Entrance(i));
		}

		TimeUnit.SECONDS.sleep(3);

		Entrance.cancel();

		executorService.shutdown();

		//等待每个任务结束，如果所有任务在超时时间达到之前全部结束则返回true，否则返回false，表示不是所有任务都已经结束
		if (executorService.awaitTermination(250, TimeUnit.MILLISECONDS)) {
			System.out.println("Some task were not terminated!");
		}

		System.out.println("Sum of Counter: " + Entrance.getTotalCount());
		System.out.println("Sum of All Entrance: " + Entrance.sumEntrances());
	}
}

/**
 * 总参观人数的计数器
 */
class Counter {

	private int count = 0;

	private Random random = new Random(47);

	/**
	 * 总计数器自增1，并返回自增后的当前计数值
	 * synchronized 保证多个任务访问并修改count时的同步问题
	 *
	 * @return
	 */
	public synchronized int incr() {
		int temp = count;
		if (random.nextBoolean()) {
			Thread.yield();
		}
		return count = ++temp;
	}

	/**
	 * 返回当前计数值
	 *
	 * @return
	 */
	public synchronized int value() {
		return count;
	}
}

class Entrance implements Runnable {

	private final int id;

	private static Counter counter = new Counter();

	private static List<Entrance> entrances = new ArrayList<>();

	private static volatile boolean canceled = false;

	private int number = 0;    //单个入口的人数

	public Entrance(int id) {
		this.id = id;
		entrances.add(this);
	}

	@Override public void run() {

		while (!canceled) {
			synchronized (this) {
				++number;
			}
			System.out.println(this + " Total: " + counter.incr());

			try {
				TimeUnit.MILLISECONDS.sleep(100);
			} catch (InterruptedException e) {
				System.out.println("sleep interrupted");
			}
		}

		System.out.println("Stopping " + this);
	}

	public static void cancel() {
		canceled = true;
	}

	/**
	 * 获取当前入口的人数
	 *
	 * @return
	 */
	public synchronized int getValue() {
		return number;
	}

	/**
	 * 获取全局计数器的计数值
	 * 应该等于每个入口的人数相加。如果去除Counter中的synchronized，将会导致不一致
	 *
	 * @return
	 */
	public static int getTotalCount() {
		return counter.value();
	}

	/**
	 * 将每个入口的人数相加
	 *
	 * @return
	 */
	public static int sumEntrances() {
		int sum = 0;
		for (Entrance entrance : entrances) {
			sum += entrance.getValue();
		}
		return sum;
	}

	@Override public String toString() {
		return "Entrance " + id + ": " + getValue();
	}
}
