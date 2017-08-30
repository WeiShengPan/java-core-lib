package guava.test;

import com.google.common.collect.BiMap;
import com.google.common.collect.EnumHashBiMap;
import com.google.common.collect.HashBiMap;
import java.features.enumeration.test.EnumWeek;

/**
 * BiMap:双向Map，提供了key和value的双向关联
 *
 * @author panws
 * @since 2017-06-23
 */
public class BiMapTest {

	public static void main(String[] args) {

		/**
		 * BiMap:双向Map
		 */
		BiMap<String, Integer> biMap = HashBiMap.create();
		biMap.put("RED", 1);
		biMap.put("GREEN", 2);
		biMap.put("YELLOW", 3);

		System.out.println(biMap);
		System.out.println(biMap.inverse());

		System.out.println(biMap.get("RED"));
		System.out.println(biMap.inverse().get(1));

		/**
		 * BiMap要求key和value都有唯一性
		 * biMap.put("BLACK", 3);	会抛出java.lang.IllegalArgumentException: value already present: 3
		 * 可以使用forcePut(key,value)方法进行强制覆盖
		 */
		System.out.println("BiMap要求key和value都有唯一性");
		biMap.forcePut("BLACK", 1);
		System.out.println(biMap.get("RED"));    //RED is no longer exist
		System.out.println(biMap.get("BLACK"));
		System.out.println(biMap.inverse().get(1));

		/**
		 * inverse()方法会返回一个反转的BiMap，但是注意这个反转的map不是新的map对象，它实现了一种视图关联。
		 * 对于反转后的map的所有操作都会影响原先的map对象。
		 */
		System.out.println("理解inverse()方法");
		biMap.remove("GREEN");
		System.out.println(biMap);
		System.out.println(biMap.inverse());
		biMap.inverse().remove(3);
		System.out.println(biMap);
		System.out.println(biMap.inverse());

		/**
		 * BiMap的实现类
		 *
		 * Key-Value Map Impl     Value-Key Map Impl     Corresponding BiMap
		 *　　HashMap                HashMap                HashBiMap
		 *　　ImmutableMap           ImmutableMap           ImmutableBiMap
		 *　　EnumMap                EnumMap                EnumBiMap
		 *　　EnumMap                HashMap                EnumHashBiMap
		 */

		EnumHashBiMap<EnumWeek, Integer> weekMap = EnumHashBiMap.create(EnumWeek.class);
	}
}
