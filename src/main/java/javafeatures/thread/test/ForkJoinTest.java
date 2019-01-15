package javafeatures.thread.test;

import javafeatures.util.PrintUtil;
import org.apache.commons.lang3.time.StopWatch;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

/**
 * Fork/Join框架用于并行计算中，和MapReduce原理类似，把大的计算任务拆分成多个小任务进行计算；Fork就是把一个大任务切分成若干个子
 * 任务并行执行，Join就是合并这些子任务的执行结果
 * <p>
 * <p>
 * ForkJoinPool ：实现了工作窃取算法来提高 CPU 的利用率。ForkJoinTask需要通过ForkJoinPool来执行，任务分割出的子任务会添加到当
 * 前工作线程所维护的双端队列中，每个线程都维护了一个双端队列，用来存储需要执行的任务。工作窃取算法允许空闲的线程从其它线程的双
 * 端队列中窃取一个任务来执行。窃取的任务必须是最晚的任务，避免和队列所属线程发生竞争。Thread2 从 Thread1 的队列中拿出最晚的
 * Task1，Thread1 会拿出 Task2 来执行，这样就避免发生竞争。但是如果队列中只有一个任务时还是会发生竞争。
 * <p>
 * <p>
 * Fork/Join框架提供了两个子类，RecursiveAction(用于没有返回结果的任务) RecursiveTask(用于有返回结果的任务)
 *
 * @author panws
 * @since 2018-09-20
 */
public class ForkJoinTest {

	// 任务量小时，for loop耗时较短；但当任务量非常大时，ForkJoinPool性能高，且差距非常明显，差距可达数个数量级
	private static final Long MAX = 100000000L;

	public static void main(String[] args) {

		StopWatch stopWatch = StopWatch.createStarted();
		Long rst = 0L;
		for (int i = 1; i <= MAX; i++) {
			rst += i;
		}
		PrintUtil.println("1. time cost: %dms", stopWatch.getNanoTime() / 1000000);

		stopWatch.reset();
		stopWatch.start();
		ForkJoinPool forkJoinPool = new ForkJoinPool();
		Future<Long> result = forkJoinPool.submit(new ForkJoinTask(1L, MAX));
		PrintUtil.println("2. time cost: %dms", stopWatch.getNanoTime() / 1000000);
	}

	private static class ForkJoinTask extends RecursiveTask<Long> {

		//小于该阈值的任务才会直接进行计算，否则递归拆分成更小的任务
		private final int threshold = 5;
		private Long first;
		private Long last;

		ForkJoinTask(Long first, Long last) {
			this.first = first;
			this.last = last;
		}

		@Override
		protected Long compute() {
			Long result = 0L;
			if (last - first <= threshold) {
				//任务足够小，满足计算的阈值，则直接计算
				for (Long i = first; i <= last; i++) {
					result += i;
				}
			} else {
				//大于阈值，则递归拆分成更小的任务
				Long middle = first + (last - first) / 2;

				ForkJoinTask leftTask = new ForkJoinTask(first, middle);
				ForkJoinTask rightTask = new ForkJoinTask(middle + 1, last);

				leftTask.fork();
				rightTask.fork();
				result = leftTask.join() + rightTask.join();
			}

			return result;
		}
	}

}
