package javafeatures.threadtest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * TODO
 * @author panws
 * @since 2017-07-24
 */
public class CallableTest {

	public static void main(String[] args) throws ExecutionException, InterruptedException {

		ExecutorService service = Executors.newFixedThreadPool(3);

		List<Future<Integer>> result = new ArrayList<>();

		for (int i = 0; i < 3; i++) {
			result.add(service.submit(new Task(i)));
		}

		for (Future<Integer> r : result) {
//			while (!r.isDone()) {
				System.out.println(r.get());
//			}
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


