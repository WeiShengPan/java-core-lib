package javafeatures.collection.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Override hashCode()
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

	@Override public int hashCode() {

		//给result赋予某个非零值常量
		int result = 17;

		//为对象内每个有意义的域f计算出一个int散列码，并合并计算到result中
		result = 37 * result + (aBoolean ? 0 : 1);    // boolean = f?0:1
		result = 37 * result + aString.hashCode();    //String = hashCode() .String对象如果包含相同字符会映射到同一块内存区域
		result = 37 * result + anInt;                //byte char short int = (int)f
		result = 37 * result + (int) aByte;
		result = 37 * result + (int) aChar;
		result = 37 * result + (int) aShort;
		result = 37 * result + (int) (aLong ^ (aLong >>> 32));    //long = (int)(f^(f>>>32))
		result = 37 * result + Float.floatToIntBits(aFloat);    //float = Float.floatToIntBits(f)
		result = 37 * result + (int) (Double.doubleToLongBits(aDouble) ^ (Double.doubleToLongBits(aDouble)
				>>> 32));    //double = (int)(Double.doubleToLongBits(f)^(Double.doubleToLongBits(f)>>>32))
		result = 37 * result + aObject.hashCode();

		return result;
	}

	@Override public boolean equals(Object obj) {
		//未具体实现
		return super.equals(obj);
	}
}
