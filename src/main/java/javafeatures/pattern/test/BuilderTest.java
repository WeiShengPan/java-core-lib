package javafeatures.pattern.test;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import javafeatures.util.PrintUtil;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 建造者模式
 *
 * @author panws
 * @since 2017-11-06
 */
public class BuilderTest {

	public static void main(String[] args) {

		ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("demon-pool-%d").build();
		ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 40, 30, TimeUnit.SECONDS, new SynchronousQueue<>(),
				threadFactory);

		String[][] vals = { { "a", "a", "a" }, { "b", "b", "b" }, { "c", "c", "c" }, { "d", "d", "d" },
				{ "e", "e", "e" } };

		for (int j = 0; j < 8; j++) {
			for (int i = 0; i < 5; i++) {
				executor.execute(new CallPerson(vals[i][0], vals[i][1], vals[i][2], i + 1, i + 1));
			}
		}

		executor.shutdown();

	}
}

class CallPerson implements Runnable {

	private String first;

	private String last;

	private String middle;

	private int no1;

	private int no2;

	CallPerson(String first, String last, String middle, int no1, int no2) {
		this.first = first;
		this.last = last;
		this.middle = middle;
		this.no1 = no1;
		this.no2 = no2;
	}

	@Override
	public void run() {
		Person person = new Person.Builder().first(first).last(last).middle(middle).no1(no1).no2(no2).build();
		PrintUtil.println(person);
	}
}

class Person {

	private final String first;

	private final String last;

	private final String middle;

	private final int no1;

	private final int no2;

	private Person(Builder builder) {
		this.first = builder.first;
		this.last = builder.last;
		this.middle = builder.middle;
		this.no1 = builder.no1;
		this.no2 = builder.no2;
	}

	public String getFirst() {
		return first;
	}

	public String getLast() {
		return last;
	}

	public String getMiddle() {
		return middle;
	}

	public int getNo1() {
		return no1;
	}

	public int getNo2() {
		return no2;
	}

	@Override
	public String toString() {
		return "Person{" + "first='" + first + '\'' + ", last='" + last + '\'' + ", middle='" + middle + '\'' + ", no1="
				+ no1 + ", no2=" + no2 + '}';
	}

	/**
	 * builder，负责构建Person
	 */
	public static class Builder {

		private String first;

		private String last;

		private String middle;

		private int no1;

		private int no2;

		public Builder first(String val) {
			this.first = val;
			return this;
		}

		public Builder last(String val) {
			this.last = val;
			return this;
		}

		public Builder middle(String val) {
			this.middle = val;
			return this;
		}

		public Builder no1(int val) {
			this.no1 = val;
			return this;
		}

		public Builder no2(int val) {
			this.no2 = val;
			return this;
		}

		public Person build() {
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return new Person(this);
		}

	}

}