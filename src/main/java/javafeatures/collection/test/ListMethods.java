package javafeatures.collection.test;

import javafeatures.util.PrintUtil;

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

		//指定index添加元素
		a.add(1, "x");
		//在末尾添加元素
		a.add("x");
		//指定index添加一个集合中的所有元素
		a.addAll(3, Countries.names(5));
		//在末尾添加一个集合中的所有元素
		a.addAll(Countries.names(5));

		//是否包含指定元素
		b = a.contains("1");
		//是否包含集合中的所有元素
		b = a.containsAll(Countries.names(5));

		//指定index获取元素
		s = a.get(1);
		//获取元素的index
		i = a.indexOf("1");
		//最后一个匹配上的index
		i = a.lastIndexOf("1");

		//列表是否为空
		b = a.isEmpty();

		it = a.iterator();
		lit = a.listIterator();
		//从指定index开始的ListIterator
		lit = a.listIterator(3);

		//移除指定index的元素
		a.remove(1);
		//移除指定元素
		a.remove("1");

		//设置index的值
		a.set(1, "1");

		//取交集
		a.retainAll(Countries.names(5));

		//移除指定集合中的所有元素
		a.removeAll(Countries.names(5));

		//清除所有元素
		a.clear();

		i = a.size();
	}

	/**
	 * list 使用ListIterator遍历元素(双向移动)
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
		//需要运动到下一个元素
		listIterator.next();

		listIterator.remove();
		//需要移动到下一个元素
		listIterator.next();

		listIterator.set("2");
	}

	/**
	 * list 查看操作结果
	 *
	 * @param a
	 */
	public static void testVisual(List<String> a) {

		PrintUtil.println(a);
		List<String> b = Countries.names(5);
		PrintUtil.println(b);
		a.addAll(b);
		a.addAll(b);
		PrintUtil.println(a);

		ListIterator<String> listIterator = a.listIterator(a.size() / 2);
		listIterator.add("ONE");
		PrintUtil.println(a);
		PrintUtil.println(listIterator.next());
		listIterator.remove();
		PrintUtil.println(listIterator.next());
		listIterator.set("TWO");
		PrintUtil.println(a);

		while (listIterator.hasPrevious()) {
			PrintUtil.print(listIterator.previous() + " ");
		}
		PrintUtil.println();
	}

	/**
	 * linkedList 专用操作
	 */
	public static void testLinkedList() {

		LinkedList<String> linkedList = new LinkedList<>();

		linkedList.addAll(Countries.names(5));
		PrintUtil.println(linkedList);

		linkedList.add("ONE");
		linkedList.add("TWO");
		PrintUtil.println(linkedList);

		PrintUtil.println(linkedList.getFirst());
		PrintUtil.println(linkedList.getLast());

		PrintUtil.println(linkedList.removeFirst());
		PrintUtil.println(linkedList.removeLast());
		PrintUtil.println(linkedList);

		//LinkedList 实现Deque接口，具有Last In First Out的栈行为
		linkedList.push("Last");
		PrintUtil.println(linkedList);
		PrintUtil.println(linkedList.pop());
	}

	public static void main(String[] args) {

		testVisual(Countries.names(5));

		testLinkedList();

	}
}
