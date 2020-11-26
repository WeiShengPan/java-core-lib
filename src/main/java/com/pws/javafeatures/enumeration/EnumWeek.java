package com.pws.javafeatures.enumeration;

import com.pws.javafeatures.util.PrintUtil;
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
			PrintUtil.println("Do home work.");
		}

		@Override public void normalJob() {
			PrintUtil.println("Customized job.");
		}
	}, //周二
	TUE(2) {
		@Override public void abstractJob() {
			PrintUtil.println("Do some cleaning.");
		}
	},  //周三
	WED(3) {
		@Override public void abstractJob() {
			PrintUtil.println("Play games.");
		}
	},    //周四
	THU(4) {
		@Override public void abstractJob() {
			PrintUtil.println("Listen music.");
		}
	},     //周五
	FRI(5) {
		@Override public void abstractJob() {
			PrintUtil.println("Do exercises.");
		}
	},     //周六
	SAT(6) {
		@Override public void abstractJob() {
			PrintUtil.println("Go dating.");
		}
	},    //周日
	SUN(7) {
		@Override public void abstractJob() {
			PrintUtil.println("Go sleeping.");
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
		PrintUtil.println("Normal job.");
	}

	@Override public String toString() {

		return new ToStringBuilder(this).append("ordinary", ordinal()).append("class", getDeclaringClass())
				.append("name", name()).append("order", getOrder()).toString();
	}

	public static void main(String[] args) {
		for (EnumWeek ew : EnumWeek.values()) {
			PrintUtil.println(ew);
			ew.abstractJob();
			ew.normalJob();
		}

		/**
		 * 使用反射查看enum的详细
		 */
		PrintUtil.println("*****Reflection*****");
		Set<String> m1 = analyze(EnumWeek.class);
		Set<String> m2 = analyze(Enum.class);
		m1.removeAll(m2);
		PrintUtil.println(m1);
		PrintUtil.println("*****Finish Reflection*****");

	}

	public static Set<String> analyze(Class<?> enumClass) {

		PrintUtil.println("-----analyze " + enumClass + "-----");

		PrintUtil.println("Interfaces: ");

		for (Type t : enumClass.getGenericInterfaces()) {
			PrintUtil.print("[" + t + "]");
		}

		PrintUtil.println();

		PrintUtil.println("Super class: " + enumClass.getSuperclass());

		Set<String> methods = new TreeSet<>();

		for (Method m : enumClass.getMethods()) {

			methods.add(m.getName());
		}

		PrintUtil.println("Methods: " + methods);

		return methods;
	}

}
