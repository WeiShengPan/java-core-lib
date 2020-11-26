package com.pws.fastjson;

import com.alibaba.fastjson.JSONObject;
import com.pws.javafeatures.util.PrintUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 使用com.alibaba.fastjson解析json字符串时的特性
 *
 * @author panws
 * @since 2017-10-18
 */
@Slf4j
public class FieldAnalyze {

	private static final String TO_ANALYZE = "{\"strExist\":\"exist\",\"intExist\":1,\"boolExist\":true,\"strNumber\":\"999\",\"strBool\":\"false\"}";

	private static final String STR_EXIST = "strExist";
	private static final String STR_NOT_EXIST = "strNotExist";

	private static final String INT_EXIST = "intExist";
	private static final String INT_NOT_EXIST = "intNotExist";

	private static final String BOOL_EXIST = "boolExist";
	private static final String BOOL_NOT_EXIST = "boolNotExist";

	private static final String STR_NUMBER = "strNumber";
	private static final String STR_BOOL = "strBool";

	public static void main(String[] args) {

		JSONObject jsonObj = JSONObject.parseObject(TO_ANALYZE);
		log.info(jsonObj.toJSONString());

		PrintUtil.printSep();

		/*
		  按类型解析
		 */
		String strExist = jsonObj.getString(STR_EXIST);
		String strNotExist = jsonObj.getString(STR_NOT_EXIST);
		log.info("String exist: ", strExist);
		log.info("String not exist: ", strNotExist);

		int intValueExist = jsonObj.getIntValue(INT_EXIST);
		int intValueNotExist = jsonObj.getIntValue(INT_NOT_EXIST);
		Integer integerExist = jsonObj.getInteger(INT_EXIST);
		Integer integerNotExist = jsonObj.getInteger(INT_NOT_EXIST);
		log.info("int value exist: ", intValueExist);
		log.info("int value not exist: ", intValueNotExist);
		log.info("Integer exist: ", integerExist);
		log.info("Integer not exist: ", integerNotExist);

		boolean booleanValueExist = jsonObj.getBooleanValue(BOOL_EXIST);
		boolean booleanValueNotExist = jsonObj.getBooleanValue(BOOL_NOT_EXIST);
		Boolean booleanExist = jsonObj.getBoolean(BOOL_EXIST);
		Boolean booleanNotExist = jsonObj.getBoolean(BOOL_NOT_EXIST);
		log.info("boolean value exist: ", booleanValueExist);
		log.info("boolean value not exist: ", booleanValueNotExist);
		log.info("Boolean exist: ", booleanExist);
		log.info("Boolean not exist: ", booleanNotExist);

		PrintUtil.printSep();

		/*
		  跨类型解析
		 */
		//正常解析
		String strFromInt = jsonObj.getString(INT_EXIST);
		//正常解析
		String strFromBool = jsonObj.getString(BOOL_EXIST);
		log.info("string from int: ", strFromInt);
		log.info("string from bool: ", strFromBool);

		//String为数字时可以正常解析，其他值会抛出异常java.lang.NumberFormatException
		int intValueFromStr = jsonObj.getIntValue(STR_NUMBER);
		//Boolean为true时为1，false为0
		int intValueFromBool = jsonObj.getIntValue(BOOL_EXIST);
		//String为数字时可以正常解析，其他值会抛出异常java.lang.NumberFormatException
		Integer integerFromStr = jsonObj.getInteger(STR_NUMBER);
		//Boolean为true时为1，false为0
		Integer integerFromBool = jsonObj.getInteger(BOOL_EXIST);
		log.info("int value from String number: ", intValueFromStr);
		log.info("int value from bool: ", intValueFromBool);
		log.info("Integer from String number: ", integerFromStr);
		log.info("Integer from bool: ", integerFromBool);

		//String为true或false时可以正常解析，其他值会抛出异常com.alibaba.fastjson.JSONException: can not cast to boolean
		boolean booleanValueFromStr = jsonObj.getBooleanValue(STR_BOOL);
		//当int值为1时为true，其他值都为false
		boolean booleanValueFromInt = jsonObj.getBooleanValue(INT_EXIST);
		//String为true或false时可以正常解析，其他值会抛出异常com.alibaba.fastjson.JSONException: can not cast to boolean
		Boolean booleanFromStr = jsonObj.getBoolean(STR_BOOL);
		//当int值为1时为true，其他值都为false
		Boolean booleanFromInt = jsonObj.getBoolean(INT_EXIST);
		log.info("boolean value from String boolean: ", booleanValueFromStr);
		log.info("boolean value from int: ", booleanValueFromInt);
		log.info("Boolean from String boolean: ", booleanFromStr);
		log.info("Boolean from int: ", booleanFromInt);
	}

}
