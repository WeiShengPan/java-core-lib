package javafeatures.collection.test;

import javafeatures.util.PrintUtil;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author panws
 * @since 2017-08-31
 */
public class HashTest {

	public static void main(String[] args) throws Exception {

		//GroundHog没有重写hashCode()和equals()方法，默认的方法会比较地址
		detect(GroundHog.class);

		//GroundHog2重写了hashCode()和equals()方法
		detect(GroundHog2.class);

	}

	public static <T extends GroundHog> void detect(Class<T> type) throws Exception {

		Constructor<T> ghog = type.getConstructor(int.class);

		Map<GroundHog, Prediction> map = new HashMap<>(10);

		for (int i = 0; i < 10; i++) {

			map.put(ghog.newInstance(i), new Prediction());
		}

		PrintUtil.println("Map = " + map);

		GroundHog gh = ghog.newInstance(3);

		PrintUtil.println("Looking up prediction for " + gh);

		if (map.containsKey(gh)) {

			PrintUtil.println(map.get(gh));
		} else {

			PrintUtil.println("Key not found: " + gh);
		}
	}
}

class GroundHog {

	protected int number;

	public GroundHog(int number) {
		this.number = number;
	}

	@Override public String toString() {
		return "ghog#" + number;
	}
}

class GroundHog2 extends GroundHog {

	public GroundHog2(int number) {
		super(number);
	}

	@Override public int hashCode() {
		return number;
	}

	@Override public boolean equals(Object obj) {

		return obj instanceof GroundHog2 && ((GroundHog2) obj).number == number;
	}
}

class Prediction {

	private static Random random = new Random(47);

	private boolean shadow = random.nextDouble() > 0.5;

	@Override public String toString() {
		return shadow ? "Winter" : "Spring";
	}
}