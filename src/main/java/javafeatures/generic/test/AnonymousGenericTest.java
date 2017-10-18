package javafeatures.generic.test;

import java.util.*;

/**
 * @author panws
 * @since 2017-10-12
 */
public class AnonymousGenericTest {

	public static void main(String[] args) {

		Random random = new Random(47);

		Queue<Customer> line = new LinkedList<>();
		Generators.fill(line, Customer.generator(), 15);

		List<Teller> tellers = new ArrayList<>();
		Generators.fill(tellers, Teller.generator(), 4);

		for (Customer c : line) {
			System.out.println(tellers.get(random.nextInt(tellers.size())) + " serves " + c);
		}
	}

}

class Customer {

	private static long counter = 1;

	private final long id = counter++;

	private Customer() {

	}

	@Override public String toString() {
		return "Customer " + id;
	}

	public static Generator<Customer> generator() {
		return new Generator<Customer>() {
			@Override public Customer next() {
				return new Customer();
			}
		};
	}
}

class Teller {

	private static long counter = 1;

	private final long id = counter++;

	private Teller() {

	}

	@Override public String toString() {
		return "Teller " + id;
	}

	public static Generator<Teller> generator() {
		return new Generator<Teller>() {
			@Override public Teller next() {
				return new Teller();
			}
		};
	}
}

