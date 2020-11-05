package guava;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Ordering;
import javafeatures.util.PrintUtil;

/**
 * 在Java中Object类是所有类的父类，其中有几个需要override的方法比如equals,hashCode和toString等方法。
 * Guava提供了覆写这几个方法的工具类。
 *
 * @author panws
 * @since 2017-07-07
 */
public class ObjectsTest {

	public static void main(String[] args) {

		/**
		 * equals方法
		 *
		 * Note for Java 7 and later:</b> This method should be treated as deprecated;
		 * use {@link java.util.Objects#equals} instead.
		 *
		 * 1. 自反性reflexive：任何非空引用x，x.equals(x)返回为true；
		 * 2. 对称性symmetric：任何非空引用x和y，x.equals(y)返回true当且仅当y.equals(x)返回true；
		 * 3. 传递性transitive：任何非空引用x和y，如果x.equals(y)返回true，并且y.equals(z)返回true，那么x.equals(z)返回true；
		 * 4. 一致性consistent：两个非空引用x和y，x.equals(y)的多次调用应该保持一致的结果，（前提条件是在多次比较之间没有修改x和y用于比较的相关信息）；
		 * 5. 对于所有非null的值x， x.equals(null)都要返回false。 (如果你要用null.equals(x)也可以，会报NullPointerException)。
		 */
		PrintUtil.println("***equals***");
		PrintUtil.println(Objects.equal("a", "b"));
		PrintUtil.println(Objects.equal("a", "a"));
		PrintUtil.println(Objects.equal("a", null));
		PrintUtil.println(Objects.equal(null, "b"));
		PrintUtil.println(Objects.equal(new Person("peida", 23), new Person("peida", 23)));
		Person person = new Person("peida", 23);
		PrintUtil.println(Objects.equal(person, person));

		/**
		 * hashCode方法
		 *
		 * Note for Java 7 and later:</b> This method should be treated as deprecated;
		 * use {@link java.util.Objects#hash} instead.
		 *
		 * 1.在Java应用的一次执行过程中，如果对象用于equals比较的信息没有被修改，那么同一个对象多次调用hashCode()方法应该返回同一个整型值。应用的多次执行中，这个值不需要保持一致，即每次执行都是保持着各自不同的值。
		 * 2.如果equals()判断两个对象相等，那么它们的hashCode()方法应该返回同样的值。
		 * 3.并没有强制要求如果equals()判断两个对象不相等，那么它们的hashCode()方法就应该返回不同的值。即，两个对象用equals()方法比较返回false，它们的hashCode可以相同也可以不同。但是，应该意识到，为两个不相等的对象产生两个不同的hashCode可以改善哈希表的性能。
		 */
		PrintUtil.println("***hashCode***");
		PrintUtil.println(Objects.hashCode("a"));
		PrintUtil.println(Objects.hashCode("a"));
		PrintUtil.println(Objects.hashCode("a", "b"));
		PrintUtil.println(Objects.hashCode("b", "a"));
		PrintUtil.println(Objects.hashCode("a", "b", "c"));
		Person person1 = new Person("peida", 23);
		PrintUtil.println(Objects.hashCode(person1));
		PrintUtil.println(Objects.hashCode(person1));

		/**
		 * toString方法
		 */
		PrintUtil.println("***toString***");
		PrintUtil.println(MoreObjects.toStringHelper(Person.class).add("x", 1).toString());
		Person person2 = new Person("peida", 23);
		String result = MoreObjects.toStringHelper(Person.class).add("name", person2.name).add("age", person2.age)
				.toString();
		PrintUtil.println(result);

		/**
		 * compareTo方法
		 */
		PrintUtil.println("***compareTo***");
		Person person3 = new Person("chen", 23);
		Person person4 = new Person("chen", 24);
		Person person5 = new Person("wang", 24);
		PrintUtil.println(person3.compareTo(person4));
		PrintUtil.println(person4.compareTo(person3));
		PrintUtil.println(person3.compareTo(person5));
		PrintUtil.println(person5.compareTo(person3));
		PrintUtil.println(person4.compareTo(person5));
		PrintUtil.println(person5.compareTo(person4));

	}

	private static class Person implements Comparable<Person> {
		String name;
		int age;

		Person(String name, int age) {
			this.name = name;
			this.age = age;
		}

		@Override public int compareTo(Person o) {
			return ComparisonChain.start().compare(name, o.name).compare(age, o.age, Ordering.natural().nullsLast())
					.result();
		}
	}
}
