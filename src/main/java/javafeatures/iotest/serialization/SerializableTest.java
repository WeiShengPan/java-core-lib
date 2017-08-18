package javafeatures.iotest.serialization;

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

		SerializedObject so = new SerializedObject("hello", "world", 100);

		System.out.println(so);

		try (ObjectOutputStream out = new ObjectOutputStream(
				new FileOutputStream("src/main/resources/io/output9.txt"))) {

			out.writeObject(so);
		}

		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("src/main/resources/io/output9.txt"))) {

			SerializedObject soCopy = (SerializedObject) in.readObject();

			System.out.println(soCopy);
		}

	}

	static class SerializedObject implements Serializable {

		private String name;

		private transient String password;    //声明为transient可关闭该字段的序列化

		private int value;

		private SerializedInnerObject[] slist;

		public SerializedObject(String name, String password, int value) {

			this.name = name;
			this.password = password;
			this.value = value;

			Random rand = new Random(value);

			slist = new SerializedInnerObject[] { new SerializedInnerObject(rand.nextInt(value)),
					new SerializedInnerObject(rand.nextInt(value)), new SerializedInnerObject(rand.nextInt(value)) };
		}

		@Override public String toString() {
			return new ToStringBuilder(this).append("name", name).append("password", password).append("value", value)
					.append("slist", slist).toString();
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

}
