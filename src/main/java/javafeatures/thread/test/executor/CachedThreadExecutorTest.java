package javafeatures.thread.test.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 这里只是演示Executors.newCachedThreadPool()方法，实际生产使用尽量不适用，因为设置maxPoolSize为Integer.MAX_VALUE，
 * 会造成创建过多的线程，应手动使用ThreadPoolExecutor创建线程{@link ThreadPoolExecutorTest}
 *
 * @author panws
 * @since 2017-08-21
 */
public class CachedThreadExecutorTest {

	public static void main(String[] args) {

		ExecutorService executorService = Executors.newCachedThreadPool();

		for (int i = 0; i < 5; i++) {

			executorService.execute(new LiftOff());
		}

		executorService.shutdown();
	}
}
