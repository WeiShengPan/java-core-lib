package com.pws.javafeatures.io.baseio;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author panws
 * @since 2017-08-16
 */
public class BinaryFileTest {

	public static byte[] read(File bFile) throws IOException {

		try (BufferedInputStream bf = new BufferedInputStream(new FileInputStream(bFile))) {

			byte[] data = new byte[bf.available()];

			bf.read(data);

			return data;

		}
	}

	public static byte[] read(String bFilename) throws IOException {

		return read(new File(bFilename).getAbsoluteFile());

	}
}
