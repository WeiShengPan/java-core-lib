package java.features.enumeration.test;

import java.util.EnumMap;
import java.util.Map;

/**
 * @author panws
 * @since 2017-08-18
 */
public class EnumMapTest {

	public static void main(String[] args) {

		EnumMap<EnumWeek, Integer> weekEnumMap = new EnumMap<>(EnumWeek.class);

		weekEnumMap.put(EnumWeek.MON, EnumWeek.MON.getOrder());
		weekEnumMap.put(EnumWeek.SUN, EnumWeek.SUN.getOrder());

		for (Map.Entry entry : weekEnumMap.entrySet()) {
			System.out.println(entry.getKey() + ": " + entry.getValue());
		}

	}
}
