package javafeatures.collection.test;

/**
 * Override hashCode()
 * <p>
 * 为什么要重写hashCode？
 * 1、在java应用程序执行期间，如果在equals方法比较中所用的信息没有被修改，那么在同一个对象上多次调用hashCode方法时必须一致地返回相同的整数。如果多次执行同一个应用时，不要求该整数必须相同。
 * 2、如果两个对象通过调用equals方法是相等的，那么这两个对象调用hashCode方法必须返回相同的整数。
 * 3、如果两个对象通过调用equals方法是不相等的，不要求这两个对象调用hashCode方法必须返回不同的整数。但是程序员应该意识到对不同的对象产生不同的hash值可以提供哈希表的性能。
 * <p>
 * 重写equals方法必须同时重写hashCode方法，相等的对象必须有相同的hashCode值，否则会违反第二条，会导致HashSet、HashMap无法正常工作
 *
 * @author panws
 * @since 2017-09-01
 */
public class HashCodeOverride {

	private boolean aBoolean;
	private String aString;
	private int anInt;
	private byte aByte;
	private char aChar;
	private short aShort;
	private long aLong;
	private float aFloat;
	private double aDouble;
	private Object aObject;

	@Override
	public int hashCode() {

		//给result赋予某个非零值常量
		int result = 17;

		//为对象内每个有意义的域f计算出一个int散列码，并合并计算到result中
		// boolean = f?0:1
		result = 37 * result + (aBoolean ? 0 : 1);
		//String = hashCode() .String对象如果包含相同字符会映射到同一块内存区域
		result = 37 * result + aString.hashCode();
		//byte char short int = (int)f
		result = 37 * result + anInt;
		result = 37 * result + (int) aByte;
		result = 37 * result + (int) aChar;
		result = 37 * result + (int) aShort;
		//long = (int)(f^(f>>>32))
		result = 37 * result + (int) (aLong ^ (aLong >>> 32));
		//float = Float.floatToIntBits(f)
		result = 37 * result + Float.floatToIntBits(aFloat);
		//double = (int)(Double.doubleToLongBits(f)^(Double.doubleToLongBits(f)>>>32))
		result = 37 * result + (int) (Double.doubleToLongBits(aDouble) ^ (Double.doubleToLongBits(aDouble) >>> 32));
		result = 37 * result + aObject.hashCode();

		return result;
	}

	@Override
	public boolean equals(Object obj) {
		//未具体实现
		return super.equals(obj);
	}
}
