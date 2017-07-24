package javafeatures.threadtest;

/**
 * TODO
 *
 * @author panws
 * @since 2017-07-24
 */
public class ProductConsumeTest {

	public static void main(String[] args) {

		final Repository repository = new Repository(0);

		Object locker = new Object();

		Consumer consumer1 = new Consumer(repository, locker);
		Consumer consumer2 = new Consumer(repository, locker);
		Consumer consumer3 = new Consumer(repository, locker);

		Producer producer1 = new Producer(repository, locker);

		new Thread(consumer2).start();

		new Thread(producer1).start();

		new Thread(consumer1).start();



	}

}

class Producer implements Runnable {

	private Repository repository;

	private final Object locker;

	Producer(Repository repository, Object locker) {
		this.repository = repository;
		this.locker = locker;
	}

	@Override

	public void run() {
		synchronized (locker) {
			while (true) {
				if (repository.getProductAmount() >= 5) {
					System.out.println("仓库已满，停止生产");
					try {
						locker.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					return;
				}
				repository.setMax(repository.getMax() + 1);
				repository.setProductAmount(repository.getProductAmount() + 1);
				System.out.println("生产者生产第" + repository.getProductAmount() + "个产品");
				locker.notifyAll();
			}
		}

	}
}

class Consumer implements Runnable {

	private Repository repository;

	private final Object locker;

	Consumer(Repository repository, Object locker) {
		this.repository = repository;
		this.locker = locker;
	}

	@Override public void run() {
		synchronized (locker) {
			while (true) {
				if (repository.getProductAmount() <= 0) {
					System.out.println("产品缺货，无法消费");
					try {
						locker.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					return;
				}
				repository.setProductAmount(repository.getProductAmount() - 1);
				System.out.println("消费者取走了第" + repository.getProductAmount() + "个产品");
				locker.notifyAll();
			}
		}
	}
}

class Repository {

	private int max = 0;

	private int productAmount;

	public Repository(int productAmount) {
		this.productAmount = productAmount;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public int getProductAmount() {
		return productAmount;
	}

	public void setProductAmount(int productAmount) {
		this.productAmount = productAmount;
	}
}
