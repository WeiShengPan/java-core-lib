package javafeatures.thread.test;

import javafeatures.util.PrintUtil;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * java.util.concurrent.BlockingQueue 接口有以下阻塞队列的实现：
 * FIFO 队列 ：LinkedBlockingQueue、ArrayBlockingQueue（固定长度）
 * 优先级队列 ：PriorityBlockingQueue
 * <p>
 * 提供了阻塞的 take() 和 put() 方法：如果队列为空 take() 将阻塞，直到队列中有内容；如果队列为满 put() 将阻塞，直到队列有空闲位置。
 * <p>
 * 使用 BlockingQueue 实现生产者消费者问题
 *
 * @author panws
 * @since 2017-09-12
 */
public class BlockingQueueTest {

	private static BlockingQueue<String> queue = new ArrayBlockingQueue<>(5);

	public static void main(String[] args) {

		ExecutorService executorService = Executors.newCachedThreadPool();

		for (int i = 0; i < 2; i++) {
			Producer producer = new Producer();
			executorService.execute(producer);
		}

		for (int i = 0; i < 5; i++) {
			Consumer consumer = new Consumer();
			executorService.execute(consumer);
		}

		for (int i = 0; i < 3; i++) {
			Producer producer = new Producer();
			executorService.execute(producer);
		}

	}

	private static class Producer implements Runnable {
		@Override
		public void run() {
			try {
				queue.put("product");
			} catch (InterruptedException e) {
				PrintUtil.err(e.getMessage(), e);
			}
			PrintUtil.print("produce......");
		}
	}

	private static class Consumer implements Runnable {
		@Override
		public void run() {
			try {
				String product = queue.take();
			} catch (InterruptedException e) {
				PrintUtil.err(e.getMessage(), e);
			}
			PrintUtil.print("consume......");
		}
	}

}
