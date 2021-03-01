package com.pws.javafeatures.thread;

import com.oracle.tools.packager.Log;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 线程本地变量
 *
 * @author panws
 * @since 2017-08-23
 */
@Slf4j
public class ThreadLocalTest {

	public static void main(String[] args) throws InterruptedException {

		ExecutorService executorService = Executors.newCachedThreadPool();

		for (int i = 0; i < 5; i++) {
			executorService.execute(new Accessor(i));
		}

		//运行观察一段时间
		TimeUnit.MICROSECONDS.sleep(5000);

		executorService.shutdownNow();    //停止
	}
}

class Accessor implements Runnable {

	private int id;

	public Accessor(int id) {
		this.id = id;
	}

	@Override public void run() {
		while (!Thread.currentThread().isInterrupted()) {
			ThreadLocalHolder.incr();
			Log.info(this.toString());
			Thread.yield();
		}
	}

	@Override public String toString() {
		return "#" + id + ":" + ThreadLocalHolder.get();
	}
}

class ThreadLocalHolder {

	private ThreadLocalHolder() {

	}

	private static ThreadLocal<Integer> value = new ThreadLocal<Integer>() {

		private Random random = new Random(47);

		@Override protected Integer initialValue() {
			return random.nextInt(1000);
		}
	};

	/**
	 * 不需要声明为synchronized,ThreadLocal保证不会出现竞争
	 */
	public static void incr() {
		value.set(value.get() + 1);
	}

	/**
	 * 不需要声明为synchronized,ThreadLocal保证不会出现竞争
	 *
	 * @return
	 */
	public static Integer get() {
		return value.get();
	}
}
