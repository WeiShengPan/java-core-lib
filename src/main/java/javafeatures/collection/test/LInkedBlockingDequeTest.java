/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package javafeatures.collection.test;

import java.util.concurrent.LinkedBlockingDeque;

/**
 * @author weisheng.pws
 * @version $Id: LInkedBlockingDequeTest.java, v 0.1 2019年06月20日 11:24 AM weisheng.pws Exp $
 */
public class LInkedBlockingDequeTest {

    public static void main(String[] args) throws InterruptedException {

        LinkedBlockingDeque<dequeValue> deque = new LinkedBlockingDeque<>(3);

        dequeValue val1 = new dequeValue(System.currentTimeMillis() / 1000, 3L);
        Thread.sleep(1000);
        dequeValue val2 = new dequeValue(System.currentTimeMillis() / 1000, 2L);
        Thread.sleep(1000);
        dequeValue val3 = new dequeValue(System.currentTimeMillis() / 1000, 1L);
        Thread.sleep(1000);
        dequeValue val4 = new dequeValue(System.currentTimeMillis() / 1000, 4L);

        boolean rst = deque.offerLast(val3);
        System.out.println(deque.size());

        rst = deque.offerLast(val2);
        System.out.println(deque.size());

        rst = deque.offerLast(val1);
        System.out.println(deque.size());

        rst = deque.offerLast(val4);
        System.out.println(rst);
        if (rst == false) {
            deque.takeFirst();
        }
        rst = deque.offerLast(val4);
        System.out.println(rst);

        dequeValue first = deque.getFirst();
        dequeValue last = deque.getLast();

        System.out.println(first);
        System.out.println(last);

        LinkedBlockingDeque<dequeValue> newDeque = new LinkedBlockingDeque<>(4);
        for (dequeValue val : deque) {
            newDeque.offerLast(val);
        }
        System.out.println(newDeque.size());

        deque = newDeque;
        deque.offerLast(new dequeValue(System.currentTimeMillis() / 1000, 5L));
        System.out.println(deque);

    }

    private static class dequeValue {

        private long timestamp;

        private double val;

        public dequeValue(long timestamp, double val) {
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