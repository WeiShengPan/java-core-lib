package com.pws.javafeatures.io.nio;

import com.pws.javafeatures.util.PrintUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.StandardCharsets;

/**
 * 使用FileChannel提高读写性能
 *
 * @author panws
 * @since 2017-08-16
 */
@Slf4j
public class FileChannelTest {

	private static final String FILE_PATH = "src/main/resources/io/output5.txt";

	public static void main(String[] args) throws Exception {

		//write from FileOutputStream
		try (FileChannel fc = new FileOutputStream(FILE_PATH).getChannel()) {

			fc.write(ByteBuffer.wrap("你好 \n".getBytes()));
		}

		//write from RandomAccessFile
		try (FileChannel fc = new RandomAccessFile(FILE_PATH, "rw").getChannel()) {

			//move to the end of the file
			fc.position(fc.size());

			fc.write(ByteBuffer.wrap("Some text in the end \n".getBytes()));
		}

		//read from FileInputStream
		String val = FileUtils.read(FILE_PATH);
		log.info("Read From FileInputStream: {}", val);

	}
}
