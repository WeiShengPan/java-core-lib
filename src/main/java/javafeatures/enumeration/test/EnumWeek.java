package javafeatures.enumeration.test;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Set;
import java.util.TreeSet;

/**
 * 枚举
 *
 * @author panws
 * @since 2017-01-24
 */
public enum EnumWeek {

	//周一
	MON(1) {
		@Override public void abstractJob() {
			System.out.println("Do home work.");
		}

		@Override public void normalJob() {
			System.out.println("Customized job.");
		}
	}, //周二
	TUE(2) {
		@Override public void abstractJob() {
			System.out.println("Do some cleaning.");
		}
	},  //周三
	WED(3) {
		@Override public void abstractJob() {
			System.out.println("Play games.");
		}
	},    //周四
	THU(4) {
		@Override public void abstractJob() {
			System.out.println("Listen music.");
		}
	},     //周五
	FRI(5) {
		@Override public void abstractJob() {
			System.out.println("Do exercises.");
		}
	},     //周六
	SAT(6) {
		@Override public void abstractJob() {
			System.out.println("Go dating.");
		}
	},    //周日
	SUN(7) {
		@Override public void abstractJob() {
			System.out.println("Go sleeping.");
		}
	};

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

	/**
	 * 每个枚举示例必须单独实现该抽象方法，实现不同的业务逻辑
	 */
	public abstract void abstractJob();

	/**
	 * 每个枚举示例可以选择实现该方法，实现不同的业务逻辑，没有实现的示例将调用该默认方法
	 */
	public void normalJob() {
		System.out.println("Normal job.");
	}

	@Override public String toString() {

		return new ToStringBuilder(this).append("ordinary", ordinal()).append("class", getDeclaringClass())
				.append("name", name()).append("order", getOrder()).toString();
	}

	public static void main(String[] args) {
		for (EnumWeek ew : EnumWeek.values()) {
			System.out.println(ew);
			ew.abstractJob();
			ew.normalJob();
		}

		/**
		 * 使用反射查看enum的详细
		 */
		System.out.println("*****Reflection*****");
		Set<String> m1 = analyze(EnumWeek.class);
		Set<String> m2 = analyze(Enum.class);
		m1.removeAll(m2);
		System.out.println(m1);
		System.out.println("*****Finish Reflection*****");

	}

	public static Set<String> analyze(Class<?> enumClass) {

		System.out.println("-----analyze " + enumClass + "-----");

		System.out.println("Interfaces: ");

		for (Type t : enumClass.getGenericInterfaces()) {
			System.out.print("[" + t + "]");
		}

		System.out.println();

		System.out.println("Super class: " + enumClass.getSuperclass());

		Set<String> methods = new TreeSet<>();

		for (Method m : enumClass.getMethods()) {

			methods.add(m.getName());
		}

		System.out.println("Methods: " + methods);

		return methods;
	}

}
