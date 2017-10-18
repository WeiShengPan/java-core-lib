package fastjson.test;

import com.alibaba.fastjson.JSONObject;
import javafeatures.util.PrintUtil;

/**
 * 使用com.alibaba.fastjson解析json字符串时的特性
 *
 * @author panws
 * @since 2017-10-18
 */
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
		PrintUtil.println(jsonObj);

		PrintUtil.printSep();

		/*
		  按类型解析
		 */
		String strExist = jsonObj.getString(STR_EXIST);
		String strNotExist = jsonObj.getString(STR_NOT_EXIST);
		PrintUtil.println("String exist: ", strExist);
		PrintUtil.println("String not exist: ", strNotExist);

		int intValueExist = jsonObj.getIntValue(INT_EXIST);
		int intValueNotExist = jsonObj.getIntValue(INT_NOT_EXIST);
		Integer integerExist = jsonObj.getInteger(INT_EXIST);
		Integer integerNotExist = jsonObj.getInteger(INT_NOT_EXIST);
		PrintUtil.println("int value exist: ", intValueExist);
		PrintUtil.println("int value not exist: ", intValueNotExist);
		PrintUtil.println("Integer exist: ", integerExist);
		PrintUtil.println("Integer not exist: ", integerNotExist);

		boolean booleanValueExist = jsonObj.getBooleanValue(BOOL_EXIST);
		boolean booleanValueNotExist = jsonObj.getBooleanValue(BOOL_NOT_EXIST);
		Boolean booleanExist = jsonObj.getBoolean(BOOL_EXIST);
		Boolean booleanNotExist = jsonObj.getBoolean(BOOL_NOT_EXIST);
		PrintUtil.println("boolean value exist: ", booleanValueExist);
		PrintUtil.println("boolean value not exist: ", booleanValueNotExist);
		PrintUtil.println("Boolean exist: ", booleanExist);
		PrintUtil.println("Boolean not exist: ", booleanNotExist);

		PrintUtil.printSep();

		/*
		  跨类型解析
		 */
		//正常解析
		String strFromInt = jsonObj.getString(INT_EXIST);
		//正常解析
		String strFromBool = jsonObj.getString(BOOL_EXIST);
		PrintUtil.println("string from int: ", strFromInt);
		PrintUtil.println("string from bool: ", strFromBool);

		//String为数字时可以正常解析，其他值会抛出异常java.lang.NumberFormatException
		int intValueFromStr = jsonObj.getIntValue(STR_NUMBER);
		//Boolean为true时为1，false为0
		int intValueFromBool = jsonObj.getIntValue(BOOL_EXIST);
		//String为数字时可以正常解析，其他值会抛出异常java.lang.NumberFormatException
		Integer integerFromStr = jsonObj.getInteger(STR_NUMBER);
		//Boolean为true时为1，false为0
		Integer integerFromBool = jsonObj.getInteger(BOOL_EXIST);
		PrintUtil.println("int value from String number: ", intValueFromStr);
		PrintUtil.println("int value from bool: ", intValueFromBool);
		PrintUtil.println("Integer from String number: ", integerFromStr);
		PrintUtil.println("Integer from bool: ", integerFromBool);

		//String为true或false时可以正常解析，其他值会抛出异常com.alibaba.fastjson.JSONException: can not cast to boolean
		boolean booleanValueFromStr = jsonObj.getBooleanValue(STR_BOOL);
		//当int值为1时为true，其他值都为false
		boolean booleanValueFromInt = jsonObj.getBooleanValue(INT_EXIST);
		//String为true或false时可以正常解析，其他值会抛出异常com.alibaba.fastjson.JSONException: can not cast to boolean
		Boolean booleanFromStr = jsonObj.getBoolean(STR_BOOL);
		//当int值为1时为true，其他值都为false
		Boolean booleanFromInt = jsonObj.getBoolean(INT_EXIST);
		PrintUtil.println("boolean value from String boolean: ", booleanValueFromStr);
		PrintUtil.println("boolean value from int: ", booleanValueFromInt);
		PrintUtil.println("Boolean from String boolean: ", booleanFromStr);
		PrintUtil.println("Boolean from int: ", booleanFromInt);
	}

}
