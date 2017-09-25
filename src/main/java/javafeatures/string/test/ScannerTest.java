package javafeatures.string.test;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.Scanner;

/**
 * @author panws
 * @see Scanner
 * @since 2017-09-25
 */
public class ScannerTest {

	public static void main(String[] args) {

		//也可接受String：Scanner scanner = new Scanner("Sir Robin of Camelot\n 24 1.6161279")
		try (Scanner scanner = new Scanner(
				new BufferedReader(new StringReader("Sir Robin of Camelot\n 24 1.6161279")))) {

			System.out.println("What is your name?");
			String name = scanner.nextLine();
			System.out.println(name);

			System.out.println("How old are you? What is your favorite double number?");
			int age = scanner.nextInt();
			double favorite = scanner.nextDouble();
			System.out.println(age);
			System.out.println(favorite);
		}

		//自定义定界符
		try (Scanner scanner = new Scanner("1+2-3-4+5-6")) {
			System.out.println(scanner.delimiter());    //返回当前正在作为定界符使用的pattern对象
			scanner.useDelimiter("[-+]");
			System.out.println(scanner.delimiter());
			while (scanner.hasNext()) {
				System.out.println(scanner.next());
			}
		}

		//hasNext和next方法可以指定pattern，将找到下一个匹配该模式的输入部分
		try (Scanner scanner = new Scanner("hs efs ls lwf oSF 1 3 s")) {
			while (scanner.hasNext("[a-zA-Z]*")) {
				System.out.println(scanner.next("[a-zA-Z]*"));
				System.out.println(scanner.match());
			}
		}
	}
}
