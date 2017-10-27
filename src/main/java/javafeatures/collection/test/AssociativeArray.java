package javafeatures.collection.test;

import javafeatures.util.PrintUtil;

/**
 * 非常简单的map的关联数据的实现，有助于理解map
 *
 * @author panws
 * @since 2017-08-31
 */
public class AssociativeArray<K, V> {

	private Object[][] pairs;

	private int index;

	public AssociativeArray(int length) {
		pairs = new Object[length][2];
	}

	public void put(K key, V value) {

		if (index >= pairs.length) {
			throw new ArrayIndexOutOfBoundsException();
		}

		pairs[index++] = new Object[] { key, value };
	}

	public V get(K key) {

		for (int i = 0; i < index; i++) {
			if (key.equals(pairs[i][0])) {
				return (V) pairs[i][1];
			}
		}

		return null;
	}

	@Override public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < index; i++) {
			sb.append(pairs[i][0].toString());
			sb.append(" : ");
			sb.append(pairs[i][1].toString());
			if (i < index - 1) {
				sb.append("\n");
			}
		}
		return sb.toString();
	}

	public static void main(String[] args) {

		AssociativeArray<String, String> map = new AssociativeArray<>(6);

		map.put("sky", "blue");
		map.put("grass", "green");
		map.put("ocean", "dancing");
		map.put("tree", "tall");
		map.put("earth", "brown");
		map.put("sun", "warm");

		try {
			map.put("extra", "object");
		} catch (ArrayIndexOutOfBoundsException e) {
			PrintUtil.println("Too much objects");
		}

		PrintUtil.println(map);
		PrintUtil.println(map.get("ocean"));
	}
}
