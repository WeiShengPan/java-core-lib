/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2020 All Rights Reserved.
 */
package javafeatures.thread.test.executor;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import javafeatures.util.PrintUtil;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author weisheng.pws
 * @version $Id: DynamicModifyExecutor.java, v 0.1 2020年06月01日 4:11 PM weisheng.pws Exp $
 */
public class DynamicModifyExecutor {

    public static void main(String[] args) throws InterruptedException {
        dynamicModifyExecutor();
    }

    private static void dynamicModifyExecutor() throws InterruptedException {

        ThreadPoolExecutor executor = new ThreadPoolExecutor(2,
                5,
                60,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(10),
                new ThreadFactoryBuilder().setNameFormat("dynamic-%d").build());

        for (int i = 0; i < 15; i++) {
            executor.submit(() -> {
                threadPoolStatus(executor);
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        // 动态设置core和max
        // （queue size无法动态设置，原因是LinkedBlockingQueue的capacity是final，动态设置queue size需要做改造实现自定义queue）
        threadPoolStatus(executor);
        executor.setCorePoolSize(10);
        executor.setMaximumPoolSize(15);
        threadPoolStatus(executor);

        // 只有设置了这个，且设置了keepAliveTime大于0，core线程才会被回收
        executor.allowCoreThreadTimeOut(true);

        Thread.currentThread().join();
    }

    private static void threadPoolStatus(ThreadPoolExecutor executor) {
        BlockingQueue<Runnable> queue = executor.getQueue();

        String strBuilder = Thread.currentThread().getName() + " ["
                + " coreSize:" + executor.getCorePoolSize()
                + " maxSize:" + executor.getMaximumPoolSize()
                + " activeCount:" + executor.getActiveCount()
                + " completeCount:" + executor.getCompletedTaskCount()
                + " queueSize:" + queue.size()
                + " queueRemainCapacity:" + queue.remainingCapacity()
                + "]";
        PrintUtil.println(strBuilder);

    }

}