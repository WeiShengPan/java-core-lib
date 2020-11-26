package com.pws.javafeatures.thread.executor;

import com.pws.javafeatures.util.PrintUtil;

/**
 * @author panws
 * @since 2017-08-21
 */
public class LiftOff implements Runnable {

	protected int countDown = 10;

	private static int taskCount = 0;

	private final int id = taskCount++;

	@Override public void run() {

		while (countDown-- > 0) {
			PrintUtil.println(status());
			Thread.yield();
		}

	}

	public String status() {
		return "#" + id + "(" + (countDown > 0 ? countDown : "LiftOff!") + ")";
	}
}
