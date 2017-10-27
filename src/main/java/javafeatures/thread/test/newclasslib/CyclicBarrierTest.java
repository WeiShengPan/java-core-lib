package javafeatures.thread.test.newclasslib;

import javafeatures.util.PrintUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * @author panws
 * @since 2017-08-28
 */
public class CyclicBarrierTest {

	public static void main(String[] args) {

		int nHourses = 7;

		int pause = 800;

		new HorseRace(nHourses, pause);
	}
}

class Horse implements Runnable {

	private static int counter = 0;

	private int id = counter++;

	private int strides = 0;

	private Random random = new Random(47);

	private static CyclicBarrier cyclicBarrier;

	public Horse(CyclicBarrier cb) {
		cyclicBarrier = cb;
	}

	public synchronized int getStrides() {
		return strides;
	}

	@Override public void run() {
		try {
			while (!Thread.interrupted()) {
				synchronized (this) {
					strides += random.nextInt(3);
				}
				cyclicBarrier.await();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (BrokenBarrierException e) {
			throw new RuntimeException(e);
		}
	}

	@Override public String toString() {
		return "Horse " + id + " ";
	}

	public String tracks() {
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < getStrides(); i++) {
			s.append("*");
		}
		s.append(id);
		return s.toString();
	}
}

class HorseRace {

	private static int FINISH_LINE = 30;

	private List<Horse> horseList = new ArrayList<>();

	private ExecutorService executorService = Executors.newCachedThreadPool();

	private CyclicBarrier cyclicBarrier;

	public HorseRace(int nHorses, final int pause) {

		cyclicBarrier = new CyclicBarrier(nHorses, new Runnable() {
			@Override public void run() {
				StringBuilder s = new StringBuilder();

				for (int i = 0; i < FINISH_LINE; i++) {
					s.append("=");
				}
				PrintUtil.println(s);

				for (Horse horse : horseList) {
					PrintUtil.println(horse.tracks());
				}

				for (Horse horse : horseList) {
					if (horse.getStrides() >= FINISH_LINE) {
						PrintUtil.println(horse + " won!");
						executorService.shutdownNow();
						return;
					}
				}

				try {
					TimeUnit.MILLISECONDS.sleep(pause);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});

		for (int i = 0; i < nHorses; i++) {
			Horse horse = new Horse(cyclicBarrier);
			horseList.add(horse);
			executorService.execute(horse);
		}
	}
}