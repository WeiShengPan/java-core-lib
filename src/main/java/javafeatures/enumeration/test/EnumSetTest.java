package javafeatures.enumeration.test;

import java.util.EnumSet;

/**
 * @author panws
 * @since 2017-08-18
 */
public class EnumSetTest {

	public static void main(String[] args) {

		EnumSet<EnumWeek> weekEnumSet = EnumSet.noneOf(EnumWeek.class);
		System.out.println(weekEnumSet);

		weekEnumSet.add(EnumWeek.MON);
		System.out.println(weekEnumSet);

		weekEnumSet.addAll(EnumSet.allOf(EnumWeek.class));
		System.out.println(weekEnumSet);

		weekEnumSet.removeAll(EnumSet.of(EnumWeek.TUE, EnumWeek.WED));
		System.out.println(weekEnumSet);

		weekEnumSet.removeAll(EnumSet.range(EnumWeek.FRI, EnumWeek.SUN));
		System.out.println(weekEnumSet);

		weekEnumSet = EnumSet.allOf(EnumWeek.class);
		System.out.println(weekEnumSet);

		weekEnumSet.remove(EnumWeek.MON);
		System.out.println(weekEnumSet);

		System.out.println(weekEnumSet.contains(EnumWeek.MON));
		System.out.println(weekEnumSet.contains(EnumWeek.TUE));

	}
}
