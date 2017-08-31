package javafeatures.io.test.serialization;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.*;
import java.util.Random;

/**
 * 对象序列化
 *
 * @author panws
 * @since 2017-08-17
 */
public class SerializableTest {

	public static void main(String[] args) throws IOException, ClassNotFoundException {

		SerializedObject so = new SerializedObject("hello", "world", 100, new SerializedChildObject());

		System.out.println(so);

		try (ObjectOutputStream out = new ObjectOutputStream(
				new FileOutputStream("src/main/resources/io/output9.txt"))) {

			out.writeObject(so);
		}

		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("src/main/resources/io/output9.txt"))) {

			SerializedObject soCopy = (SerializedObject) in.readObject();    //deserialize to object

			System.out.println(soCopy);
		}

	}

	static class SerializedObject implements Serializable {

		public static int NUM = 3;

		private String name;

		private transient String password;    //声明为transient可关闭该字段的序列化

		private int value;

		private SerializedInnerObject[] slist;

		private SerializedChildObject child;

		public SerializedObject() {

		}

		public SerializedObject(String name, String password, int value, SerializedChildObject child) {

			this.name = name;
			this.password = password;
			this.value = value;
			this.child = child;

			Random rand = new Random(value);

			slist = new SerializedInnerObject[] { new SerializedInnerObject(rand.nextInt(value)),
					new SerializedInnerObject(rand.nextInt(value)), new SerializedInnerObject(rand.nextInt(value)) };
		}

		@Override public String toString() {
			return new ToStringBuilder(this).append("NUM", NUM).append("name", name).append("password", password)
					.append("value", value).append("slist", slist).append("child", child).toString();
		}
	}

	static class SerializedInnerObject implements Serializable {

		private int value;

		public SerializedInnerObject(int value) {
			this.value = value;
		}

		@Override public String toString() {
			return Integer.toString(value);
		}
	}

	static class SerializedChildObject extends SerializedObject {

		private static int NUM_CHILD = NUM;

		public SerializedChildObject() {
			super();
		}

		@Override public String toString() {
			return new ToStringBuilder(this).append("NUM_CHILD", NUM_CHILD).toString();
		}
	}

}
