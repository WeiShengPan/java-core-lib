package javafeatures.enumtest;

/**
 * 枚举
 * @author panws
 * @since 2017-01-24
 */
public enum EnumWeek {

	MON(1), TUE(2), WED(3), THU(4), FRI(5), SAT(6), SUN(7);

	private final int order;

	EnumWeek(int order) {
		this.order = order;
	}

	public int getOrder() {
		return order;
	}

	public static EnumWeek valueOf(int order) {
		switch (order) {
		case 1:
			return MON;
		case 2:
			return TUE;
		case 3:
			return WED;
		case 4:
			return THU;
		case 5:
			return FRI;
		case 6:
			return SAT;
		case 7:
			return SUN;
		default:
			throw new IllegalArgumentException("argument out of range");
		}
	}

}
