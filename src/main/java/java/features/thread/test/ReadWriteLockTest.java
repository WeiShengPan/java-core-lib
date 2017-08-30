package java.features.thread.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author panws
 * @since 2017-08-28
 */
public class ReadWriteLockTest {

	private static int readers = 30;

	private static int writers = 1;

	private int size = 100;

	private static ExecutorService executorService = Executors.newCachedThreadPool();

	private ReaderWriterList<Integer> list = new ReaderWriterList<>(size, 0);

	private Random random = new Random(47);

	public void execute() {

		for (int i = 0; i < readers; i++) {
			executorService.execute(new Reader());
		}

		for (int i = 0; i < writers; i++) {
			executorService.execute(new Writer());
		}

	}

	private class Writer implements Runnable {
		@Override public void run() {
			try {
				for (int i = 0; i < 20; i++) {
					list.set(i, random.nextInt());
					TimeUnit.MILLISECONDS.sleep(100);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Writer finished, shutting down.");
			executorService.shutdownNow();
		}
	}

	private class Reader implements Runnable {
		@Override public void run() {
			try {
				while (!Thread.interrupted()) {
					for (int i = 0; i < size; i++) {
						list.get(i);
						TimeUnit.MILLISECONDS.sleep(1);
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

class ReaderWriterList<T> {

	private ArrayList<T> lockedList;

	private ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);

	public ReaderWriterList(int size, T initialValue) {

		lockedList = new ArrayList<T>(Collections.nCopies(size, initialValue));

	}

	public T set(int index, T element) {

		Lock writeLock = lock.writeLock();

		writeLock.lock();

		try {
			return lockedList.set(index, element);
		} finally {
			writeLock.unlock();
		}
	}

	public T get(int index) {

		Lock readLock = lock.readLock();

		readLock.lock();

		try {
			if (lock.getReadLockCount() > 1) {
				System.out.println(lock.getReadLockCount());
			}
			return lockedList.get(index);
		} finally {
			readLock.unlock();
		}
	}
}

class ExecuteMain {

	public static void main(String[] args) {
		ReadWriteLockTest readWriteLockTest = new ReadWriteLockTest();
		readWriteLockTest.execute();
	}

}
