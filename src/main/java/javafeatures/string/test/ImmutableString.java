package javafeatures.string.test;

import java.util.ArrayList;
import java.util.List;

/**
 * String 不可变性
 * 相同的String指向同一块物理内存区域
 *
 * @author panws
 * @since 2017-09-20
 */
public class ImmutableString {

	public static void main(String[] args) {

		/*
		 * String 传递的是引用的拷贝
		 */
		String str1 = "hello";
		System.out.println(str1);

		String str2 = upCase(str1);    //实际传递的是引用的一个拷贝
		System.out.println(str2);
		System.out.println(str1);    //str1没有被改变

		String str3 = str1;
		System.out.println(str1.equals(str2));
		System.out.println(str1.equals(str3));    //所指向的是同一块物理区域

		String str4 = "hello";
		System.out.println(str1.equals(str4));    //指向的是同一块物理区域，所以相等

		/*
		 * 对象传递的是引用
		 */
		StrObj obj1 = new StrObj();
		obj1.add("hello");
		System.out.println(obj1);
		StrObj obj2 = add(obj1);    //实际传递的是对象的引用
		System.out.println(obj2);
		System.out.println(obj1);    //obj1被改变了
	}

	/**
	 * 对于一个方法而言，参数是为该方法提供信息的，而不是想让方法改变自己，这一点很重要；
	 * 正是有了这种保障，才使得代码易于编写和阅读
	 *
	 * @param str
	 * @return
	 */
	private static String upCase(String str) {
		return str.toUpperCase();
	}

	/**
	 * 此方法会改变参数
	 *
	 * @param obj
	 * @return
	 */
	private static StrObj add(StrObj obj) {
		obj.add("world");
		return obj;
	}
}

class StrObj {

	private List<String> strList = new ArrayList<>();

	public void add(String str) {
		this.strList.add(str);
	}

	@Override public String toString() {
		return strList.toString();
	}
}

