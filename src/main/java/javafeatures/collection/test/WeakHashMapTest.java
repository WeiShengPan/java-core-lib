package javafeatures.collection.test;

import java.util.WeakHashMap;

/**
 * @author panws
 * @since 2017-09-01
 */
public class WeakHashMapTest {

	public static void main(String[] args) {

		int size = 30;

		Key[] keys = new Key[size];

		WeakHashMap<Key, Value> map = new WeakHashMap<>();

		for (int i = 0; i < size; i++) {

			Key k = new Key(Integer.toString(i));
			Value v = new Value(Integer.toString(i));

			//每隔3个元素将键的普通引用放入到数组中，保证其不会被回收
			if (i % 3 == 0) {
				keys[i] = k;    //save as real references
			}

			map.put(k, v);
		}

		System.gc();
		System.out.println(map);
	}

}

class Element {

	private String id;

	public Element(String id) {
		this.id = id;
	}

	@Override public int hashCode() {
		return id.hashCode();
	}

	@Override public boolean equals(Object obj) {
		return obj instanceof Element && id.equals(((Element) obj).id);
	}

	@Override public String toString() {
		return id;
	}

	@Override protected void finalize() throws Throwable {
		System.out.println("Finalizing " + getClass().getSimpleName() + " " + id);
	}
}

class Key extends Element {

	public Key(String id) {
		super(id);
	}
}

class Value extends Element {

	public Value(String id) {
		super(id);
	}
}
