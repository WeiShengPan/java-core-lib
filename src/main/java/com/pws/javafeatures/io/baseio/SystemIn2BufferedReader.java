package com.pws.javafeatures.io.baseio;

import com.pws.javafeatures.util.PrintUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 从标准输入中读取
 *
 * @author panws
 * @since 2017-08-16
 */
public class SystemIn2BufferedReader {

	public static void main(String[] args) throws IOException {

		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		String s;

		while ((s = in.readLine()) != null && s.length() != 0) {
			PrintUtil.println("echo : " + s);
		}

	}
}
