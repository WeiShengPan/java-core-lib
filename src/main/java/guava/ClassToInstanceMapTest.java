package guava;

import com.google.common.collect.ClassToInstanceMap;
import com.google.common.collect.MutableClassToInstanceMap;
import javafeatures.util.PrintUtil;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * map的key是多种类型，想通过映射他们得到这种类型
 * ClassToInstaceMap提供了方法 T getInstance(Class<T>) 和 T putInstance(Class<T>, T),消除了强制类型转换
 * ClassToInstanceMap<B> 实现了Map<Class<? extends B>, B>，或者说，这是一个从B的子类到B对象的映射，这可能使得ClassToInstanceMap的泛型轻度混乱，但是只要记住B总是Map的上层绑定类型，通常来说B只是一个对象。
 *
 * @author panws
 * @since 2017-07-04
 */
public class ClassToInstanceMapTest {

	public static void main(String[] args) {

		ClassToInstanceMap<Number> numberMap = MutableClassToInstanceMap.create();

		numberMap.put(Integer.class, 0);
		numberMap.put(BigDecimal.class, BigDecimal.ZERO);
		numberMap.put(BigInteger.class, BigInteger.ZERO);
		numberMap.put(BigInteger.class, BigInteger.ONE);

		PrintUtil.println(numberMap);
		PrintUtil.println(numberMap.getInstance(Integer.class));
	}
}
