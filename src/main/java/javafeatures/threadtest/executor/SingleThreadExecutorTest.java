package javafeatures.threadtest.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author panws
 * @since 2017-08-21
 */
public class SingleThreadExecutorTest {

	public static void main(String[] args) {

		ExecutorService executorService = Executors.newSingleThreadExecutor();

		for (int i=0;i<5;i++) {
			executorService.execute(new LiftOff());
		}

		executorService.shutdown();

	}
}
