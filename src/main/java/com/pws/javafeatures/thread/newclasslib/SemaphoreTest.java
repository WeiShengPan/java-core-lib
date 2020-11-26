package com.pws.javafeatures.thread.newclasslib;

import com.pws.javafeatures.util.PrintUtil;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Semaphore 类似于操作系统中的信号量，可以控制对互斥资源的访问线程数。
 * 以下代码模拟了对某个服务的并发请求，每次只能有 3 个客户端同时访问，请求总数为 10。
 *
 * @author panws
 * @since 2018-09-13
 */
public class SemaphoreTest {

	public static void main(String[] args) {

		final int clientCount = 3;
		final int totalRequestCount = 10;

		Semaphore semaphore = new Semaphore(clientCount);
		ExecutorService executorService = Executors.newCachedThreadPool();

		for (int i = 0; i < totalRequestCount; i++) {
			executorService.execute(() -> {
				try {
					semaphore.acquire();
					PrintUtil.println(semaphore.availablePermits());
				} catch (InterruptedException e) {
					PrintUtil.err(e.getMessage(), e);
				} finally {
					semaphore.release();
				}
			});
		}

		executorService.shutdown();
	}
}
