package jvm.test;

/**
 * 虚拟机栈 OutOfMemoryError
 *
 * windows平台的虚拟机中，java线程是映射到操作系统的内核线程上的，以下代码执行时有较大风险，可能会导致操作系统假死
 *
 * vm args: -Xss2M
 * @author panws
 * @since 2017-12-12
 */
public class StackOom {

	private void neverStop() {
		while (true) {

		}
	}

	public void stackLeakByThread() {
		while (true) {
			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					neverStop();
				}
			});
			thread.start();
		}
	}

	public static void main(String[] args) {
		StackOom oom = new StackOom();
		oom.stackLeakByThread();
	}
}
