package javafeatures.collection.test;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 * @author panws
 * @since 2017-08-31
 */
public class ListMethods {

	private static boolean b;

	private static String s;

	private static int i;

	private static Iterator<String> it;

	private static ListIterator<String> lit;

	/**
	 * list 基本操作
	 *
	 * @param a
	 */
	public static void basicTest(List<String> a) {

		a.add(1, "x");    //指定index添加元素
		a.add("x");    //在末尾添加元素
		a.addAll(3, Countries.names(5));    //指定index添加一个集合中的所有元素
		a.addAll(Countries.names(5));    //在末尾添加一个集合中的所有元素

		b = a.contains("1");    //是否包含指定元素
		b = a.containsAll(Countries.names(5));    //是否包含集合中的所有元素

		s = a.get(1);    //指定index获取元素
		i = a.indexOf("1");    //获取元素的index
		i = a.lastIndexOf("1");    //最后一个匹配上的index

		b = a.isEmpty();    //列表是否为空

		it = a.iterator();
		lit = a.listIterator();
		lit = a.listIterator(3);    //从指定index开始的ListIterator

		a.remove(1);    //移除指定index的元素
		a.remove("1");    //移除指定元素

		a.set(1, "1");    //设置index的值

		a.retainAll(Countries.names(5));    //取交集

		a.removeAll(Countries.names(5));    //移除指定集合中的所有元素

		a.clear();    //清除所有元素

		i = a.size();
	}

	/**
	 * list 使用iterator遍历元素
	 *
	 * @param a
	 */
	public static void iterMotion(List<String> a) {

		ListIterator<String> listIterator = a.listIterator();

		b = listIterator.hasNext();
		b = listIterator.hasPrevious();

		s = listIterator.next();
		i = listIterator.nextIndex();

		s = listIterator.previous();
		i = listIterator.previousIndex();
	}

	/**
	 * list 使用iterator修改元素
	 *
	 * @param a
	 */
	public static void iterManipulation(List<String> a) {

		ListIterator<String> listIterator = a.listIterator();

		listIterator.add("1");
		listIterator.next();    //需要运动到下一个元素

		listIterator.remove();
		listIterator.next();    //需要移动到下一个元素

		listIterator.set("2");
	}

	/**
	 * list 查看操作结果
	 *
	 * @param a
	 */
	public static void testVisual(List<String> a) {

		System.out.println(a);
		List<String> b = Countries.names(5);
		System.out.println(b);
		a.addAll(b);
		a.addAll(b);
		System.out.println(a);

		ListIterator<String> listIterator = a.listIterator(a.size() / 2);
		listIterator.add("ONE");
		System.out.println(a);
		System.out.println(listIterator.next());
		listIterator.remove();
		System.out.println(listIterator.next());
		listIterator.set("TWO");
		System.out.println(a);

		while (listIterator.hasPrevious()) {
			System.out.print(listIterator.previous() + " ");
		}
		System.out.println();
	}

	/**
	 * linkedList 专用操作
	 */
	public static void testLinkedList() {

		LinkedList<String> linkedList = new LinkedList<>();

		linkedList.addAll(Countries.names(5));
		System.out.println(linkedList);

		linkedList.add("ONE");
		linkedList.add("TWO");
		System.out.println(linkedList);

		System.out.println(linkedList.getFirst());
		System.out.println(linkedList.getLast());

		System.out.println(linkedList.removeFirst());
		System.out.println(linkedList.removeLast());
		System.out.println(linkedList);
	}

	public static void main(String[] args) {

		testVisual(Countries.names(5));

		testLinkedList();

	}
}
