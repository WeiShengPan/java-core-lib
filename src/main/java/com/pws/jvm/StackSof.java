package com.pws.jvm;

import com.pws.javafeatures.util.PrintUtil;

/**
 * 虚拟机栈 StackOverFlowError
 * <p>
 * vm args: -Xss128k
 *
 * @author panws
 * @since 2017-12-12
 */
public class StackSof {

	private int stackLength = 1;

	public void stackLeak() {
		stackLength++;
		stackLeak();
	}

	public static void main(String[] args) {
		StackSof sof = new StackSof();
		try {
			sof.stackLeak();
		} catch (Throwable t) {
			PrintUtil.println("stack length: ", sof.stackLength);
			throw t;
		}
	}
}
