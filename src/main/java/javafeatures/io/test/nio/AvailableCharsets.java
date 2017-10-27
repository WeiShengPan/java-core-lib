package javafeatures.io.test.nio;

import javafeatures.util.PrintUtil;

import java.nio.charset.Charset;
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

			PrintUtil.print(charsetsEntry.getKey() + ": ");

			for (String alias : ((Charset) charsetsEntry.getValue()).aliases()) {

				PrintUtil.print("[" + alias + "]");
			}

			PrintUtil.print("\n");
		}

		//		Iterator<String> keyIt = charsets.keySet().iterator();
		//
		//		while (keyIt.hasNext()) {
		//
		//			String csName = keyIt.next();
		//
		//			PrintUtil.print(csName);
		//
		//			Iterator aliasesIt = charsets.get(csName).aliases().iterator();
		//
		//			if (aliasesIt.hasNext()) {
		//				PrintUtil.print(": ");
		//			}
		//			while (aliasesIt.hasNext()) {
		//				PrintUtil.print(aliasesIt.next());
		//				if (aliasesIt.hasNext()) {
		//					PrintUtil.print(", ");
		//				}
		//			}
		//
		//			PrintUtil.print("\n");
		//
		//		}

	}
}
