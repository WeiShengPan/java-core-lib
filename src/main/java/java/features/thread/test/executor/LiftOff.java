package java.features.thread.test.executor;

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
			System.out.println(status());
			Thread.yield();
		}

	}

	public String status() {
		return "#" + id + "(" + (countDown > 0 ? countDown : "LiftOff!") + ")";
	}
}
