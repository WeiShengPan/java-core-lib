package javafeatures.io.test.serialization;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.*;

/**
 * @author panws
 * @since 2017-08-18
 */
public class ExternalizableTest {

	public static void main(String[] args) throws IOException, ClassNotFoundException {

		ExternalizedObject eo = new ExternalizedObject(1, "he");

		System.out.println(eo);

		try (ObjectOutputStream out = new ObjectOutputStream(
				new FileOutputStream("src/main/resources/io/output10.txt"))) {

			System.out.println("Saving...");

			out.writeObject(eo);    //序列化时，会调用writeExternal写入数据

			System.out.println("Saving end");
		}

		try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("src/main/resources/io/output10.txt"))) {

			System.out.println("Recovering...");

			ExternalizedObject eoCopy = (ExternalizedObject) in.readObject();    //恢复时，会调用默认的构造器，然后调用readExternal恢复数据

			System.out.println("Recovering end");

			System.out.println(eoCopy);
		}

	}

	static class ExternalizedObject implements Externalizable {

		private int i;

		private String s;

		public ExternalizedObject() {

			System.out.println("default constructor.");
		}

		public ExternalizedObject(int i, String s) {

			System.out.println("parameters constructor.");
			this.i = i;
			this.s = s;
		}

		@Override public String toString() {
			return new ToStringBuilder(this).append("i", i).append("s", s).toString();
		}

		@Override public void writeExternal(ObjectOutput out) throws IOException {

			System.out.println("ExternalizedObject.writeExternal");

			//you must do this below.
			out.writeObject(s);
			out.writeInt(i);
		}

		@Override public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {

			System.out.println("ExternalizedObject.readExternal");

			//you must do this below.
			s = (String) in.readObject();
			i = in.readInt();
		}
	}
}
