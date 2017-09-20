package javafeatures.thread.test;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @author panws
 * @since 2017-09-12
 */
public class BlockingQueueTest {

	public static void main(String[] args) throws InterruptedException {

		DelayQueue<DelayedEle> delayQueue = new DelayQueue();

		DelayedEle ele1 = new DelayedEle(1);

		delayQueue.add(ele1);

		System.out.println(delayQueue.take().getValue());

	}

	static class DelayedEle implements Delayed {

		private int value;

		public DelayedEle(int value) {
			this.value = value;
		}

		@Override public long getDelay(TimeUnit unit) {
			return 0;
		}

		@Override public int compareTo(Delayed o) {
			return this.compareTo(o);
		}

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}
	}

}
