package com.fsd.yzcx.tools;

import java.lang.reflect.Field;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import android.util.Log;


/**
 * JsonTools
 * 
 * @author Administrator json格式数据的处理
 */
public class JsonTools {
	private final static String flag = "flag";
	private static final String Tag = "NonOwnerAppInputFragment";

	public static boolean isJson(String str) {
		// 判断最后一个是]或是}
		char charAt = str.charAt(str.length() - 1);

		if (charAt == ']' || charAt == '}') {

			return true;
		}
		return false;
	}
	/**
	 * 传入Json字符串和业务Bean的类名，
	 * 可以将Json里的值赋给Bean
	 * @param json
	 * @param classPath Bean的完整路径
	 * @return
	 */
	public static Object jArray2Model(String json, String classPath) {
		try { 
			JSONArray jArray = new JSONArray(json);
			System.out.println(json);
			Class<?> clazz = Class.forName(classPath);
			Object obj = clazz.newInstance();
			Field[] fields = clazz.getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				// 获取成员变量的字段
				String fieldName = fields[i].getName();
				String newWord = new String(fieldName.charAt(0) + "")
				.toUpperCase();
				String setFieldMethodName = "set" + newWord
						+ fieldName.substring(1);
				Log.i(Tag, "fieldName==" + fieldName + "; newWord==" + newWord
						+ "; setFieldMethodName ==" + setFieldMethodName);

				for (int j = 0; j < jArray.length(); j++) {
					JSONObject jObject = (JSONObject) jArray.get(j);
					if (jObject.toString().contains(fieldName)) {
						fields[i].setAccessible(true);
						fields[i].set(obj, jObject.get(fieldName));
					}
				}
			}
			return obj;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public static Object jArray2Model(String json,Class<?> clazz){
		return jArray2Model(json, clazz.getName());
	}

	/**
	 * 对于接收JSON的内容里只有一个参数的name
	 * 和id的情况，请使用这个方法
	 * @param json 
	 * @param nameKey 获取name对应的键名
	 * @param idKey  获取id对应的键名
	 * @return	一个封装了name数组和id值数组的二维数组
	 */
	public static String[][] jArray2StrArrays(String json,String nameKey,String idKey){
		if(json==null)
			return null;
		try {
			JSONArray jArray = new JSONArray(json);
			String[] conTemp = new String[jArray.length() + 1];
			conTemp[0] = "请选择";
			String[] idTemp = new String[jArray.length() + 1];
			idTemp[0] = "";
			for (int i = 0; i < jArray.length(); i++) {
				JSONObject obj = (JSONObject) jArray.get(i);

				conTemp[i + 1] = (String) obj.get(nameKey);
				idTemp[i + 1] = (String) obj.get(idKey);
			}
			return new String[][]{conTemp,idTemp};
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 解析JSON数据，并根据对应的flag返回相应的描述结果
	 * @param json
	 * @return 插入数据返回的结果flag
	 */
	public static int jArray2UpdateFlag(String json){

		try {
			JSONArray jsonArray = new JSONArray(json);
			JSONObject object = (JSONObject) jsonArray.get(0);
			String flag2 = object.getString(flag);
			String info = object.getString("info");
			int i = Integer.parseInt(flag2);
			return i;
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return -1;

	}
	/**
	 * 解析JSON数据，并根据对应的flag返回相应的描述结果
	 * @param json
	 * @return 插入数据返回的结果描述
	 */
	public static String jArray2UpdateResult(String json){

		String[] updateResult = new String[]{"更新失败","更新成功","提交参数未知"};
		int i = jArray2UpdateFlag(json);
		if(i!= -1){
			return updateResult[i];
		}
		return null;

	}
}
