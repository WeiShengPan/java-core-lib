/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package javafeatures.collection.test;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author weisheng.pws
 * @version $Id: LinkedBlockingQueueTest.java, v 0.1 2019年06月17日 9:12 PM weisheng.pws Exp $
 */
public class LinkedBlockingQueueTest {

    public static void main(String[] args) throws InterruptedException {

        LinkedBlockingQueue<QueueValue> queue = new LinkedBlockingQueue<>(3);

        QueueValue val1 = new QueueValue(System.currentTimeMillis() / 1000, 10L);
        Thread.sleep(1000);
        QueueValue val2 = new QueueValue(System.currentTimeMillis() / 1000, 5L);
        Thread.sleep(1000);
        QueueValue val3 = new QueueValue(System.currentTimeMillis() / 1000, 20L);
        Thread.sleep(1000);
        QueueValue val4 = new QueueValue(System.currentTimeMillis() / 1000, 8L);

        boolean rst = queue.offer(val3);
        System.out.println(queue.size());

        rst = queue.offer(val2);
        System.out.println(queue.size());

        rst = queue.offer(val1);
        System.out.println(queue.size());

        rst = queue.offer(val4);
        System.out.println(rst);
        if (rst == false) {
            queue.take();
        }
        rst = queue.offer(val4);
        System.out.println(rst);

    }

    private static class QueueValue {

        private long timestamp;

        private double val;

        public QueueValue(long timestamp, double val) {
            this.timestamp = timestamp;
            this.val = val;
        }

        public long getTimestamp() {
            return timestamp;
        }

        public double getVal() {
            return val;
        }
    }
}