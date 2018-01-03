package javafeatures.collection.test;

import java.lang.ref.*;
import java.util.LinkedList;

import javafeatures.util.PrintUtil;

/**
 * @author panws
 * @since 2017-09-01
 */
public class ReferenceTest {

	private static ReferenceQueue<VeryBig> rq = new ReferenceQueue<>();

	public static void checkQueue() {

		Reference<? extends VeryBig> inq = rq.poll();

		if (inq != null) {
			PrintUtil.println("In queue: " + inq.get());
		}
	}

	public static void main(String[] args) {
		int size = 10;

		//软引用：在系统发生内存溢出异常之前，才会被回收
		LinkedList<SoftReference<VeryBig>> sa = new LinkedList<>();
		for (int i = 0; i < size; i++) {
			sa.add(new SoftReference<>(new VeryBig("Soft " + i), rq));
			PrintUtil.println("Just created: " + sa.getLast());
			checkQueue();
		}

		//弱引用：当gc工作时，无论内存是否足够，都会被回收
		LinkedList<WeakReference<VeryBig>> wa = new LinkedList<>();
		for (int i = 0; i < size; i++) {
			wa.add(new WeakReference<>(new VeryBig("Weak " + i), rq));
			PrintUtil.println("Just created: " + wa.getLast());
			checkQueue();
		}

		SoftReference<VeryBig> s = new SoftReference<>(new VeryBig("Soft"));
		WeakReference<VeryBig> w = new WeakReference<>(new VeryBig("Weak"));

		//这里只会回收WeakReference，因为内存未满，不会回收SoftReference
		System.gc();

		//虚引用：一个对象对象是否有虚引用的存在，不会对其生存时间构成影响，也无法通过虚引用获取一个对象实例。
		//唯一目的是能够在被回收时收到一个系统通知
		LinkedList<PhantomReference<VeryBig>> pa = new LinkedList<>();
		for (int i = 0; i < size; i++) {
			pa.add(new PhantomReference<>(new VeryBig("Phantom " + i), rq));
			PrintUtil.println("Just created: " + pa.getLast());
			checkQueue();
		}

		//这里会回收PhantomReference
		System.gc();
	}

}

class VeryBig {

	private static final int SIZE = 10000;
	private long[] la = new long[SIZE];
	private String id;

	public VeryBig(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return id;
	}

	@Override
	protected void finalize() throws Throwable {
		PrintUtil.println("Finalizing " + id);
	}
}
