package com.pws.commonslang3;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;

/**
 * @author panws
 * @since 2017-06-30
 */
@Slf4j
public class StopWatchTest {

	public static void main(String[] args) throws InterruptedException {

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		Thread.sleep(1000);

		log.info("Start time: " + stopWatch.getStartTime() + " ms");
		log.info("Time: " + stopWatch.getTime() + " ms");
		log.info("Nano time: " + stopWatch.getNanoTime() + " ms");

		stopWatch.split();
		log.info("Split nano time: " + stopWatch.getSplitNanoTime() + " ms");
		log.info("Split time: " + stopWatch.getSplitTime() + " ms");

		stopWatch.stop();
	}
}
