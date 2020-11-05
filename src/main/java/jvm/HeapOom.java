package jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * Java 堆溢出 OutOfMemoryError
 * <p>
 * vm args: -Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError
 *
 * @author panws
 * @since 2017-12-12
 */

public class HeapOom {

	static class OOMObject {
	}

	public static void main(String[] args) {

		List<OOMObject> list = new ArrayList<>();

		while (true) {
			list.add(new OOMObject());
		}
	}
}
