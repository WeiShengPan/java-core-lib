/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package javafeatures.collection.test;

import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * @author weisheng.pws
 * @version $Id: PriorityQueueTest.java, v 0.1 2019年06月17日 8:53 PM weisheng.pws Exp $
 */
public class PriorityQueueTest {

    public static void main(String[] args) throws InterruptedException {

        PriorityBlockingQueue<QueueValue> priorityQueue = new PriorityBlockingQueue<>(2, new Comparator<QueueValue>() {
            @Override
            public int compare(QueueValue o1, QueueValue o2) {
                return (int) (o1.timestamp - o2.timestamp);
            }
        });

        QueueValue val1 = new QueueValue(System.currentTimeMillis() / 1000, 10L);
        Thread.sleep(1000);
        QueueValue val2 = new QueueValue(System.currentTimeMillis() / 1000, 5L);
        Thread.sleep(1000);
        QueueValue val3 = new QueueValue(System.currentTimeMillis() / 1000, 20L);
        Thread.sleep(1000);
        QueueValue val4 = new QueueValue(System.currentTimeMillis() / 1000, 8L);



        boolean rst = priorityQueue.add(val3);
        System.out.println(rst);

        rst = priorityQueue.add(val2);
        System.out.println(rst);

        rst = priorityQueue.add(val1);
        System.out.println(rst);

        rst = priorityQueue.add(val4);
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