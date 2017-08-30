package commonslang3.test.stopwatch.test;

import org.apache.commons.lang3.time.StopWatch;

/**
 * @author panws
 * @since 2017-06-30
 */
public class StopWatchTest {

	public static void main(String[] args) throws InterruptedException {

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		Thread.sleep(1000);

		System.out.println("Start time: " + stopWatch.getStartTime() + " ms");
		System.out.println("Time: " + stopWatch.getTime() + " ms");
		System.out.println("Nano time: " + stopWatch.getNanoTime() + " ms");

		stopWatch.split();
		System.out.println("Split nano time: " + stopWatch.getSplitNanoTime() + " ms");
		System.out.println("Split time: " + stopWatch.getSplitTime() + " ms");

		stopWatch.stop();
	}
}
