package javafeatures.io.test.baseio;

import javafeatures.util.PrintUtil;

import java.io.*;

/**
 * @author panws
 * @since 2017-08-16
 */
public class StoringAndRecoveringTest {

	public static void main(String[] args) throws IOException {

		try (DataOutputStream out = new DataOutputStream(
				new BufferedOutputStream(new FileOutputStream("src/main/resources/io/output2.txt")))) {

			//存储到流中，相应的read方法可以恢复它
			out.writeDouble(3.1415926);
			out.writeUTF("That is pi");
			out.writeBoolean(true);
			out.write(1);

		}

		try (DataInputStream in = new DataInputStream(
				new BufferedInputStream(new FileInputStream("src/main/resources/io/output2.txt")))) {

			PrintUtil.println(in.readDouble());
			PrintUtil.println(in.readUTF());
			PrintUtil.println(in.readBoolean());
			PrintUtil.println(in.read());
		}

	}
}
