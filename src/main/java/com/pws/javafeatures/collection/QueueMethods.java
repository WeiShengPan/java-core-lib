package com.pws.javafeatures.collection;

import com.pws.javafeatures.util.PrintUtil;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * @author panws
 * @since 2017-08-31
 */
public class QueueMethods {

	private static int count = 10;

	static void test(Queue<String> queue, Gen gen) {

		for (int i = 0; i < count; i++) {
			queue.offer(gen.next());
		}

		while (queue.peek() != null) {
			PrintUtil.print(queue.remove() + " ");
		}
		PrintUtil.println();
	}

	static class Gen {

		String[] s = "ONE TWO THREE FOUR FIVE SIX SEVEN EIGHT NINE TEN".split(" ");

		int i;

		public String next() {
			return s[i++];
		}
	}

	public static void main(String[] args) {
		//除了优先级队列，Queue将按照元素被置于queue中的顺序产生
		test(new LinkedList<>(), new Gen());
		test(new PriorityQueue<>(), new Gen());
		test(new ArrayBlockingQueue<>(count), new Gen());
		test(new ConcurrentLinkedQueue<>(), new Gen());
		test(new LinkedBlockingQueue<>(), new Gen());
		test(new PriorityBlockingQueue<>(), new Gen());
	}
}
