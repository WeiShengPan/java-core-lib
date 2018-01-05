package javafeatures.io.test.serialization;

import java.io.*;

import javafeatures.util.PrintUtil;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author panws
 * @since 2017-08-18
 */
public class ExternalizableTest {

	public static void main(String[] args) throws IOException, ClassNotFoundException {

		ExternalizedObject eo = new ExternalizedObject(1, "he");

		PrintUtil.println(eo);

		try (ObjectOutputStream out = new ObjectOutputStream(
				new FileOutputStream("src/main/resources/io/output10.txt"))) {

			PrintUtil.println("Saving...");

			//序列化时，会调用writeExternal写入数据
			out.writeObject(eo);

			PrintUtil.println("Saving end");
		}

		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("src/main/resources/io/output10.txt"))) {

			PrintUtil.println("Recovering...");

			//恢复时，会调用默认的构造器，然后调用readExternal恢复数据
			ExternalizedObject eoCopy = (ExternalizedObject) in.readObject();

			PrintUtil.println("Recovering end");

			PrintUtil.println(eoCopy);
		}

	}

	static class ExternalizedObject implements Externalizable {

		/**
		 * 当序列化实现的是Externalizable，序列化和反序列化都是手动通过writeExternal和
		 * readExternal指定的，不判断变量是否有transient；这里虽然声明i为transient，但
		 * 序列化和反序列化时都仍有这个域变量
		 */
		private transient int i;

		private String s;

		public ExternalizedObject() {

			PrintUtil.println("default constructor.");
		}

		public ExternalizedObject(int i, String s) {

			PrintUtil.println("parameters constructor.");
			this.i = i;
			this.s = s;
		}

		@Override public String toString() {
			return new ToStringBuilder(this).append("i", i).append("s", s).toString();
		}

		@Override public void writeExternal(ObjectOutput out) throws IOException {

			PrintUtil.println("ExternalizedObject.writeExternal");

			//you must do this below.
			out.writeObject(s);
			out.writeInt(i);
		}

		@Override public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {

			PrintUtil.println("ExternalizedObject.readExternal");

			//you must do this below.
			s = (String) in.readObject();
			i = in.readInt();
		}
	}
}
