package commonslang3.test.stopwatch.test;

import javafeatures.util.PrintUtil;
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

		PrintUtil.println("Start time: " + stopWatch.getStartTime() + " ms");
		PrintUtil.println("Time: " + stopWatch.getTime() + " ms");
		PrintUtil.println("Nano time: " + stopWatch.getNanoTime() + " ms");

		stopWatch.split();
		PrintUtil.println("Split nano time: " + stopWatch.getSplitNanoTime() + " ms");
		PrintUtil.println("Split time: " + stopWatch.getSplitTime() + " ms");

		stopWatch.stop();
	}
}
