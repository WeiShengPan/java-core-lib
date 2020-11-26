package com.pws.javafeatures.thread;

import com.pws.javafeatures.util.PrintUtil;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 多线程实现生产者消费者模式，使用queue实现
 * {@link java.util.concurrent.LinkedBlockingQueue}
 * {@link java.util.concurrent.ArrayBlockingQueue}
 *
 * @author panws
 * @since 2019-02-12
 */
public class ProducerConsumerQueueTest {

	private static final String EXIT_MSG = "Good bye!";

	public static void main(String[] args) {

		BlockingQueue<String> queue = new LinkedBlockingQueue<>(5);
		//		BlockingQueue<String> queue = new ArrayBlockingQueue<>(5);

		Producer producer = new Producer(queue);
		Consumer consume = new Consumer(queue);

		new Thread(producer).start();
		new Thread(consume).start();
	}

	private static class Producer implements Runnable {

		private BlockingQueue<String> queue;

		public Producer(BlockingQueue<String> queue) {
			this.queue = queue;
		}

		@Override
		public void run() {

			for (int i = 0; i < 20; i++) {
				try {
					Thread.sleep(5L);
					String msg = "Message" + i;
					PrintUtil.println("Produced new item: " + msg);
					queue.put(msg);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			try {
				PrintUtil.println("Time to say good bye!");
				queue.put(EXIT_MSG);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private static class Consumer implements Runnable {

		private BlockingQueue<String> queue;

		public Consumer(BlockingQueue<String> queue) {
			this.queue = queue;
		}

		@Override
		public void run() {

			try {
				String msg;
				while (!EXIT_MSG.equalsIgnoreCase(msg = queue.take())) {
					PrintUtil.println("Consumed item: " + msg);
					Thread.sleep(10L);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}

}
