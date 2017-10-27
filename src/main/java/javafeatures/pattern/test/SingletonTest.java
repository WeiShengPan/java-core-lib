package javafeatures.pattern.test;

/**
 * 单例模式
 *
 * @author panws
 * @since 2017-10-27
 */
public class SingletonTest {

	public static void main(String[] args) {

		LazySingleton lazySingleton = LazySingleton.getInstance();

		HungrySingleton hungrySingleton = HungrySingleton.getInstance();

		StaticInnerClassSingleton staticInnerClassSingleton = StaticInnerClassSingleton.getInstance();

		EnumSingleton enumSingleton = EnumSingleton.INSTANCE;
	}
}

/**
 * 懒汉式单例
 */
class LazySingleton {

	private static volatile LazySingleton instance;

	private LazySingleton() {

	}

	public static LazySingleton getInstance() {

		if (instance == null) {
			synchronized (LazySingleton.class) {
				if (instance == null) {
					instance = new LazySingleton();
				}
			}
		}

		return instance;
	}
}

/**
 * 饿汉式单例
 */
class HungrySingleton {

	private static final HungrySingleton INSTANCE = new HungrySingleton();

	private HungrySingleton() {

	}

	public static HungrySingleton getInstance() {
		return INSTANCE;
	}

}

/**
 * 静态内部类单例
 */
class StaticInnerClassSingleton {

	private StaticInnerClassSingleton() {

	}

	public static final StaticInnerClassSingleton getInstance() {
		return SingletonHolder.INSTANCE;
	}

	private static class SingletonHolder {
		private static final StaticInnerClassSingleton INSTANCE = new StaticInnerClassSingleton();
	}
}

/**
 * 枚举单例
 */
enum EnumSingleton {

	INSTANCE
}
