package javafeatures.thread.test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Callable接口，该接口中的call方法可以在线程执行结束时产生一个返回值，并且必须使用
 * ExecutorService.submit()方法调用，submit()方法会返回产生的Future对象，它用Callable
 * 返回结果的特定类型进行了参数化。可以使用isDone()检测Future是否已完成，也可以不使用
 * isDone()直接使用get()，get()将阻塞直至结果准备就绪。
 *
 * @author panws
 * @since 2017-07-24
 */
public class CallableTest {

	public static void main(String[] args) throws ExecutionException, InterruptedException {

		ExecutorService service = Executors.newFixedThreadPool(3);

		List<Future<Integer>> result = new ArrayList<>();

		for (int i = 3; i < 6; i++) {
			result.add(service.submit(new Task(i)));
		}

		for (Future<Integer> r : result) {
			System.out.println(r.get());
		}

	}

	static class Task implements Callable<Integer> {

		private int upperBounds;

		public Task(int upperBounds) {
			this.upperBounds = upperBounds;
		}

		@Override public Integer call() throws Exception {
			int sum = 0;
			for (int i = 1; i < upperBounds; i++) {
				sum += i;
			}
			return sum;
		}
	}

}


