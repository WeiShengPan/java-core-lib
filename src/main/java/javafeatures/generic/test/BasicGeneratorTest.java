package javafeatures.generic.test;

/**
 * @author panws
 * @since 2017-10-12
 */
public class BasicGeneratorTest {

	public static void main(String[] args) {

		BasicGenerator<CountedObj> generator = BasicGenerator.create(CountedObj.class);

		for (int i = 0; i < 5; i++) {
			System.out.println(generator.next());
		}
	}

}

class BasicGenerator<T> implements Generator {

	private Class<T> type;

	private BasicGenerator(Class<T> type) {
		this.type = type;
	}

	public static <T> BasicGenerator<T> create(Class<T> type) {
		return new BasicGenerator<>(type);
	}

	public T next() {

		try {
			return type.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}

class CountedObj {

	private static long counter = 0;

	private final long id = counter++;

	public long id() {
		return id;
	}

	@Override public String toString() {
		return "CountedObj " + id();
	}
}
