package javafeatures.io.test.serialization;

import java.io.*;
import java.util.Random;

import javafeatures.util.PrintUtil;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 对象序列化
 *
 * @author panws
 * @since 2017-08-17
 */
public class SerializableTest {

	public static void main(String[] args) throws IOException, ClassNotFoundException {

		SerializedObject so = new SerializedObject("hello", "world", 100, new SerializedChildObject());

		PrintUtil.println(so);

		try (ObjectOutputStream out = new ObjectOutputStream(
				new FileOutputStream("src/main/resources/io/output9.txt"))) {

			out.writeObject(so);
		}

		//这里在序列化之后、反序列化之前改变NUM的值，目的是验证static变量也是不可被序列化的；
		//之所以反序列化后是有值的，是读取jvm中已存储的static变量值，而不是反序列化的值
		SerializedObject.NUM = 0;

		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("src/main/resources/io/output9.txt"))) {

			//deserialize to object
			SerializedObject soCopy = (SerializedObject) in.readObject();

			PrintUtil.println(soCopy);
		}

	}

	static class SerializedObject implements Serializable {

		/**
		 * 声明为static的变量不可被序列化和反序列化
		 */
		public static int NUM = 3;

		private String name;

		/**
		 * 声明为transient可关闭该字段的序列化
		 */
		private transient String password;

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

		private static final int NUM_CHILD = NUM;

		public SerializedChildObject() {
			super();
		}

		@Override public String toString() {
			return new ToStringBuilder(this).append("NUM_CHILD", NUM_CHILD).toString();
		}
	}

}
