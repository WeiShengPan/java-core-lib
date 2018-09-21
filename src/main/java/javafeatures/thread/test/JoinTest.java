package javafeatures.thread.test;

import javafeatures.util.PrintUtil;

/**
 * join是Thread类的一个方法，启动线程后直接调用，即join()的作用是：“等待该线程终止”，
 * 这里需要理解的就是该线程是指的主线程等待子线程的终止。也就是在子线程调用了join()方
 * 法后面的代码，只有等到子线程结束了才能执行。
 * <p>
 * <p>
 * 线程加入规则：
 * Thread 对象的结束先行发生于join()方法返回
 *
 * @author panws
 * @since 2017-07-24
 */
public class JoinTest {

	public static void main(String[] args) throws InterruptedException {

		PrintUtil.println(Thread.currentThread().getName() + "主线程开始");

		JoinThread joinThreadA = new JoinThread("A");
		JoinThread joinThreadB = new JoinThread("B");

		joinThreadA.start();
		joinThreadB.start();

		//等待Thread A执行结束
		joinThreadA.join();
		//等待Thread B执行结束
		joinThreadB.join();

		//这里只有等到Thread A和Thread B结束后才会执行
		PrintUtil.println(Thread.currentThread().getName() + "主线程结束");
	}
}

class JoinThread extends Thread {
	private String name;

	public JoinThread(String name) {
		super(name);
		this.name = name;
	}

	@Override
	public void run() {
		PrintUtil.println(Thread.currentThread().getName() + " 线程运行开始!");
		for (int i = 0; i < 5; i++) {
			PrintUtil.println("子线程" + name + "运行 : " + i);
			try {
				sleep((int) Math.random() * 10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		PrintUtil.println(Thread.currentThread().getName() + " 线程运行结束!");
	}
}
