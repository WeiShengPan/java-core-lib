package javafeatures.enumeration.test;

import javafeatures.util.PrintUtil;

import java.util.EnumSet;

/**
 * @author panws
 * @since 2017-08-18
 */
public class EnumSetTest {

	public static void main(String[] args) {

		EnumSet<EnumWeek> weekEnumSet = EnumSet.noneOf(EnumWeek.class);
		PrintUtil.println(weekEnumSet);

		weekEnumSet.add(EnumWeek.MON);
		PrintUtil.println(weekEnumSet);

		weekEnumSet.addAll(EnumSet.allOf(EnumWeek.class));
		PrintUtil.println(weekEnumSet);

		weekEnumSet.removeAll(EnumSet.of(EnumWeek.TUE, EnumWeek.WED));
		PrintUtil.println(weekEnumSet);

		weekEnumSet.removeAll(EnumSet.range(EnumWeek.FRI, EnumWeek.SUN));
		PrintUtil.println(weekEnumSet);

		weekEnumSet = EnumSet.allOf(EnumWeek.class);
		PrintUtil.println(weekEnumSet);

		weekEnumSet.remove(EnumWeek.MON);
		PrintUtil.println(weekEnumSet);

		PrintUtil.println(weekEnumSet.contains(EnumWeek.MON));
		PrintUtil.println(weekEnumSet.contains(EnumWeek.TUE));

	}
}
