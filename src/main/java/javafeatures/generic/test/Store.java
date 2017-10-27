package javafeatures.generic.test;

import javafeatures.util.PrintUtil;

import java.util.ArrayList;
import java.util.Random;

/**
 * 本例用于演示利用泛型可以简单的构建复杂模型
 *
 * @author panws
 * @since 2017-10-12
 */
public class Store extends ArrayList<Aisle> {

	private ArrayList<CheckoutStand> checkouts = new ArrayList<>();

	private Office office = new Office();

	public Store(int nAisles, int nShelves, int nProducts) {

		for (int i = 0; i < nAisles; i++) {
			add(new Aisle(nShelves, nProducts));
		}
	}

	@Override public String toString() {

		StringBuilder stringBuilder = new StringBuilder();

		for (Aisle aisle : this) {
			for (Shelf shelf : aisle) {
				for (Product product : shelf) {
					stringBuilder.append(product);
					stringBuilder.append("\n");
				}
			}
		}

		return stringBuilder.toString();
	}

	public static void main(String[] args) {
		PrintUtil.println(new Store(2, 2, 5));
	}
}

/**
 * 商品
 */
class Product {

	private final int id;

	private String desc;

	private double price;

	public Product(int id, String desc, double price) {
		this.id = id;
		this.desc = desc;
		this.price = price;
		PrintUtil.println(toString());
	}

	@Override public String toString() {
		return id + ": " + desc + ". price: $" + price;
	}

	public void priceChange(double change) {
		price += change;
	}

	public static Generator<Product> generator = new Generator<Product>() {

		private Random random = new Random(47);

		@Override public Product next() {
			return new Product(random.nextInt(1000), "Test", Math.round(random.nextDouble() * 1000.0) + 0.99);
		}
	};
}

/**
 * 货架
 */
class Shelf extends ArrayList<Product> {

	public Shelf(int nProducts) {
		Generators.fill(this, Product.generator, nProducts);
	}
}

/**
 * 走道
 */
class Aisle extends ArrayList<Shelf> {

	public Aisle(int nShelves, int nProducts) {
		for (int i = 0; i < nShelves; i++) {
			add(new Shelf(nProducts));
		}
	}
}

/**
 * 结账处
 */
class CheckoutStand {

}

/**
 * 办公室
 */
class Office {

}
