package javafeatures.iotest;

import java.io.*;

/**
 * @author panws
 * @since 2017-08-16
 */
public class StoringAndRecoveringTest {

	public static void main(String[] args) throws IOException {

		try (DataOutputStream out = new DataOutputStream(
				new BufferedOutputStream(new FileOutputStream("src/main/resources/io/output2.txt")))) {

			out.writeDouble(3.1415926);    //存储到流中，相应的read方法可以恢复它
			out.writeUTF("That is pi");
			out.writeBoolean(true);
			out.write(1);

		}

		try (DataInputStream in = new DataInputStream(
				new BufferedInputStream(new FileInputStream("src/main/resources/io/output2.txt")))) {

			System.out.println(in.readDouble());
			System.out.println(in.readUTF());
			System.out.println(in.readBoolean());
			System.out.println(in.read());
		}

	}
}
