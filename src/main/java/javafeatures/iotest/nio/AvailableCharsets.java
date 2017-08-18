package javafeatures.iotest.nio;

import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;

/**
 * @author panws
 * @since 2017-08-17
 */
public class AvailableCharsets {

	public static void main(String[] args) {

		SortedMap<String, Charset> charsets = Charset.availableCharsets();

		for (Map.Entry charsetsEntry : charsets.entrySet()) {

			System.out.print(charsetsEntry.getKey() + ": ");

			for (String alias : ((Charset) charsetsEntry.getValue()).aliases()) {

				System.out.print("[" + alias + "]");
			}

			System.out.print("\n");
		}

		//		Iterator<String> keyIt = charsets.keySet().iterator();
		//
		//		while (keyIt.hasNext()) {
		//
		//			String csName = keyIt.next();
		//
		//			System.out.print(csName);
		//
		//			Iterator aliasesIt = charsets.get(csName).aliases().iterator();
		//
		//			if (aliasesIt.hasNext()) {
		//				System.out.print(": ");
		//			}
		//			while (aliasesIt.hasNext()) {
		//				System.out.print(aliasesIt.next());
		//				if (aliasesIt.hasNext()) {
		//					System.out.print(", ");
		//				}
		//			}
		//
		//			System.out.print("\n");
		//
		//		}

	}
}
