package javafeatures.collection.test;

import java.util.*;

/**
 * @author panws
 * @since 2017-08-30
 */
class Countries {

	private Countries() {

	}

	static final String[][] DATA = { { "CHINA", "Beijing" }, { "JAPAN", "Tokyo" }, { "KOREA", "Seoul" },
			{ "AUSTRALIA", "Canberra" }, { "AUSTRIA", "Vienna" }, { "FRANCE", "Paris" }, { "GREECE", "Athens" },
			{ "ITALY", "Rome" }, { "CANADA", "Ottawa" }, { "CUBA", "Havana" } };

	/**
	 * 二维数组DATA的Map视图
	 * FlyweightMap必须实现entrySet()方法，它需要定制的Map.Entry和定制的Set实现。
	 */
	private static class FlyweightMap extends AbstractMap<String, String> {

		/**
		 * 每个Map.Entry对象都只存储了它的索引，而不是实际的键和值。当调用getKey()和getValue()时，它会使用
		 * 该索引来返回恰当的DATA元素
		 */
		private static class Entry implements Map.Entry<String, String> {

			private int index;

			Entry(int index) {
				this.index = index;
			}

			@Override public String getKey() {
				return DATA[index][0];
			}

			@Override public String getValue() {
				return DATA[index][1];
			}

			/**
			 * 不支持的操作
			 */
			@Override public String setValue(String value) {
				throw new UnsupportedOperationException();
			}

			@Override public boolean equals(Object obj) {
				return DATA[index][0].equals(obj);
			}

			@Override public int hashCode() {
				return DATA[index][0].hashCode();
			}

			@Override public String toString() {
				return "[" + getKey() + " " + getValue() + "]";
			}
		}

		/**
		 * EntrySet确保size不会大于DATA的大小
		 */
		static class EntrySet extends AbstractSet<Map.Entry<String, String>> {

			private int size;

			EntrySet(int size) {
				if (size < 0) {
					this.size = 0;
				} else if (size > DATA.length) {
					this.size = DATA.length;
				} else {
					this.size = size;
				}
			}

			@Override public int size() {
				return size;
			}

			/**
			 * 每个迭代器只有一个Map.Entry。Entry对象被用作数据的视窗，它只包含DATA中的索引。每次调用next()方法
			 * 时，Entry的index都会递增，指向下一元素，然后从next()返回该Iterator所持有的单一Entry对象
			 */
			private class Iter implements Iterator<Map.Entry<String, String>> {

				private Entry entry = new Entry(-1);

				@Override public boolean hasNext() {
					return entry.index < size - 1;
				}

				@Override public Map.Entry<String, String> next() {
					entry.index++;
					return entry;
				}

				@Override public void remove() {
					throw new UnsupportedOperationException();
				}
			}

			@Override public Iterator<Map.Entry<String, String>> iterator() {
				return new Iter();
			}
		}

		/**
		 * 定制的EntrySet
		 */
		private static Set<Map.Entry<String, String>> entries = new EntrySet(DATA.length);

		/**
		 * 返回定制的EntrySet
		 */
		@Override public Set<Map.Entry<String, String>> entrySet() {
			return entries;
		}
	}

	/**
	 * 产生一个包含指定尺寸的EntrySet的FlyweightMap，用于重载过的capitals()和names()方法
	 */
	private static Map<String, String> select(final int size) {
		return new FlyweightMap() {
			@Override public Set<Map.Entry<String, String>> entrySet() {
				return new EntrySet(size);
			}
		};
	}

	private static Map<String, String> map = new FlyweightMap();

	private static List<String> names = new ArrayList<>(map.keySet());

	public static Map<String, String> capitals() {
		return map;
	}

	public static Map<String, String> capitals(int size) {
		return select(size);
	}

	public static List<String> names() {
		return names;
	}

	public static List<String> names(int size) {
		return new ArrayList<>(select(size).keySet());
	}

}
