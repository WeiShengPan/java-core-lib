package com.pws.javafeatures.thread.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 这里只是演示Executors.newCachedThreadPool()方法，实际生产使用尽量不适用，因为workQueue使用了无界队列，新任务会无限制的加入到队列中，
 * 堆积的请求会耗费非常大的内存，应手动使用ThreadPoolExecutor创建线程{@link ThreadPoolExecutorTest}
 *
 * @author panws
 * @since 2017-08-21
 */
public class SingleThreadExecutorTest {

	public static void main(String[] args) {

		ExecutorService executorService = Executors.newSingleThreadExecutor();

		for (int i = 0; i < 5; i++) {
			executorService.execute(new LiftOff());
		}

		executorService.shutdown();

	}
}
