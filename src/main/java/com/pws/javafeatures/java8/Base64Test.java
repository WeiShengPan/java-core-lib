package com.pws.javafeatures.java8;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import com.pws.javafeatures.util.PrintUtil;

/**
 * Java8 引入了Base64，无需在依赖第三方工具类
 * 
 * @author panws
 * @since 2018-01-03
 */
public class Base64Test {
	
	private static final String TEXT = "This is a text.";
	
	private static final String URL = "www.test-why.com/index?ip=1&name=名字";
	
	public static void main(String[] args) {
		
		String encodedText = Base64.getEncoder().encodeToString(TEXT.getBytes(StandardCharsets.UTF_8));
		PrintUtil.println(encodedText);
		
		String decodedText = new String(Base64.getDecoder().decode(encodedText), StandardCharsets.UTF_8);
		PrintUtil.println(decodedText);
		
		String encodedUrl = Base64.getUrlEncoder().encodeToString(URL.getBytes(StandardCharsets.UTF_8));
		PrintUtil.println(encodedUrl);
		
		String decodedUrl = new String(Base64.getUrlDecoder().decode(encodedUrl), StandardCharsets.UTF_8);
		PrintUtil.println(decodedUrl);
		
	}
}
