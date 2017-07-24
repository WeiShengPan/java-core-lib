package javafeatures.threadtest;

/**
 * join是Thread类的一个方法，启动线程后直接调用，即join()的作用是：“等待该线程终止”，
 * 这里需要理解的就是该线程是指的主线程等待子线程的终止。也就是在子线程调用了join()方
 * 法后面的代码，只有等到子线程结束了才能执行。
 *
 * @author panws
 * @since 2017-07-24
 */
public class JoinTest {

	public static void main(String[] args) throws InterruptedException {

		System.out.println(Thread.currentThread().getName() + "主线程开始");

		JoinThread joinThreadA = new JoinThread("A");
		JoinThread joinThreadB = new JoinThread("B");

		joinThreadA.start();
		joinThreadB.start();

		joinThreadA.join();
		joinThreadB.join();

		System.out.println(Thread.currentThread().getName() + "主线程结束");
	}
}

class JoinThread extends Thread {
	private String name;

	public JoinThread(String name) {
		super(name);
		this.name = name;
	}

	public void run() {
		System.out.println(Thread.currentThread().getName() + " 线程运行开始!");
		for (int i = 0; i < 5; i++) {
			System.out.println("子线程" + name + "运行 : " + i);
			try {
				sleep((int) Math.random() * 10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println(Thread.currentThread().getName() + " 线程运行结束!");
	}
}
