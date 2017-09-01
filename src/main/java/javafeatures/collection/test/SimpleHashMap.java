package javafeatures.collection.test;

import javafeatures.util.PrintUtil;

import java.util.*;

/**
 * @author panws
 * @since 2017-09-01
 */
public class SimpleHashMap<K, V> extends AbstractMap<K, V> {

	static final int SIZE = 997;

	LinkedList<MapEntry<K, V>>[] buckets = new LinkedList[SIZE];

	@Override public Set<Entry<K, V>> entrySet() {

		Set<Map.Entry<K, V>> set = new HashSet<>();

		for (LinkedList<MapEntry<K, V>> bucket : buckets) {
			if (bucket == null) {
				continue;
			}
			for (MapEntry<K, V> pair : bucket) {
				set.add(pair);
			}
		}

		return set;
	}

	@Override public V get(Object key) {

		int index = Math.abs(key.hashCode()) % SIZE;

		if (buckets[index] == null) {
			return null;
		}

		for (MapEntry<K, V> pair : buckets[index]) {
			if (pair.getKey().equals(key)) {
				return pair.getValue();
			}
		}

		return null;
	}

	@Override public V put(K key, V value) {

		V oldValue = null;

		int index = Math.abs(key.hashCode()) % SIZE;

		if (buckets[index] == null) {
			buckets[index] = new LinkedList<>();
		}

		LinkedList<MapEntry<K, V>> bucket = buckets[index];

		MapEntry<K, V> pair = new MapEntry<>(key, value);	//新的键值对

		boolean found = false;

		ListIterator<MapEntry<K, V>> listIterator = bucket.listIterator();

		while (listIterator.hasNext()) {
			MapEntry<K, V> iPair = listIterator.next();
			if (iPair.getKey().equals(key)) {
				oldValue = iPair.getValue();
				listIterator.set(pair);
				found = true;
				break;
			}
		}

		if (!found) {
			buckets[index].add(pair);
		}

		return oldValue;
	}

	public static void main(String[] args) {

		SimpleHashMap<String, String> map = new SimpleHashMap<>();

		map.putAll(Countries.capitals());

		PrintUtil.println(map);

		PrintUtil.println(map.get("CHINA"));

		PrintUtil.println(map.entrySet());
	}
}

class MapEntry<K, V> implements Map.Entry<K, V> {

	private K key;
	private V value;

	public MapEntry(K key, V value) {
		this.key = key;
		this.value = value;
	}

	@Override public K getKey() {
		return key;
	}

	@Override public V getValue() {
		return value;
	}

	@Override public V setValue(V value) {
		V result = this.value;
		this.value = value;
		return result;
	}

	@Override public int hashCode() {
		return (key == null ? 0 : key.hashCode()) ^ (value == null ? 0 : value.hashCode());
	}

	@Override public boolean equals(Object obj) {

		if (!(obj instanceof MapEntry)) {
			return false;
		}

		MapEntry other = (MapEntry) obj;

		return (key == null ? other.key == null : key.equals(other.key)) && (value == null ?
				other.value == null :
				value.equals(other.value));
	}

	@Override public String toString() {
		return key + "=" + value;
	}
}
