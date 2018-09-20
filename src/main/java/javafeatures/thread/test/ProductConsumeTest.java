package javafeatures.thread.test;

import javafeatures.util.PrintUtil;

/**
 * 多线程实现生产者消费者模式
 *
 * @author panws
 * @since 2017-07-24
 */
public class ProductConsumeTest {

	public static void main(String[] args) {

		Repository repository = new Repository(0);

		Consumer consumer1 = new Consumer(repository, "A");
		Consumer consumer2 = new Consumer(repository, "B");
		Consumer consumer3 = new Consumer(repository, "C");

		Producer producer1 = new Producer(repository, "a");
		Producer producer2 = new Producer(repository, "b");

		new Thread(consumer1).start();
		new Thread(producer1).start();
		new Thread(consumer2).start();
		new Thread(producer2).start();
		new Thread(consumer3).start();

	}

}

/**
 * 生产者
 */
class Producer implements Runnable {

	private final Repository repository;

	private String name;

	Producer(Repository repository, String name) {
		this.repository = repository;
		this.name = name;
	}

	@Override public void run() {
		while (true) {
			synchronized (repository) {
				if (repository.getAccumulativeValue() >= 30) {
					PrintUtil.println("生产数量已达上限，生产者" + name + "停止生产");
					repository.notifyAll();
					return;
				}
				if (repository.getProductAmount() >= 5) {
					PrintUtil.println("仓库已满，生产者" + name + "暂停生产");
					try {
						repository.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					continue;
				}
				repository.setAccumulativeValue(repository.getAccumulativeValue() + 1);
				repository.setProductAmount(repository.getProductAmount() + 1);
				PrintUtil.println("生产者" + name + "生产第" + repository.getProductAmount() + "个产品");
				repository.notifyAll();
			}
		}

	}
}

/**
 * 消费者
 */
class Consumer implements Runnable {

	private final Repository repository;

	private String name;

	Consumer(Repository repository, String name) {
		this.repository = repository;
		this.name = name;
	}

	@Override public void run() {
		while (true) {
			synchronized (repository) {
				if (repository.getProductAmount() <= 0) {
					PrintUtil.println("产品缺货，消费者" + name + "无法消费");
					try {
						repository.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					continue;
				}
				repository.setProductAmount(repository.getProductAmount() - 1);
				PrintUtil.println("消费者 " + name + " 取走了第" + (repository.getProductAmount() + 1) + "个产品");
				repository.notifyAll();
			}
		}
	}
}

/**
 * 产品仓库
 */
class Repository {

	/**
	 * 累计值
	 */
	private int accumulativeValue = 0;

	/**
	 * 库存
	 */
	private int productAmount;

	public Repository(int productAmount) {
		this.productAmount = productAmount;
	}

	public int getAccumulativeValue() {
		return accumulativeValue;
	}

	public void setAccumulativeValue(int accumulativeValue) {
		this.accumulativeValue = accumulativeValue;
	}

	public int getProductAmount() {
		return productAmount;
	}

	public void setProductAmount(int productAmount) {
		this.productAmount = productAmount;
	}
}
