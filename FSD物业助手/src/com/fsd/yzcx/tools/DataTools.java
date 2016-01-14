package com.fsd.yzcx.tools;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DataTools {


	/**
	 * 根据正则表达式去
	 * @param Reg 正则表达式
	 * @param content 匹配的内容
	 * @return
	 */
	public static boolean isHaveIn(String Reg,String content){
		Pattern pattern=Pattern.compile(Reg);		
		if(pattern.matcher(content).find()){
			return true;
		};
		return false;
	}
/**
 * 将数组转化为json格式的数据
 * 
 * @param strArr 数组
 * @param arrkey 数组的key
 * @param objectkey 具体值的key
 * @return
 */
	public static String mStrArr2Json(String strArr[],String arrkey,String objectkey){
		/**
		 * eg:{
    "arrkey": [
        {
            "objectkey": "strArr[0]"
        },
        {
            "objectkey": "strArr[1]"
        },
        {
            "objectkey": "strArr[length-1]"
        }
    ]
}
		 */
		StringBuffer json=new StringBuffer();
		json.append("{"+"\""+arrkey+"\":"+"[");
		for (int i = 0; i < strArr.length; i++) {
			if(i==strArr.length-1){//最后一个只用}
				json.append("{\""+objectkey+"\":"+"\""+strArr[i]+"\""+"}");
			}else{
				json.append("{\""+objectkey+"\":"+"\""+strArr[i]+"\""+"},");
			}
		}
		json.append("]}");
		return json.toString();
	}

	/**
	 * 将json字符串解析成数组
	 */
	public static String[] mJson2StrArr(String jsondata,String strArrkey,String objectkey){
		
		try {
			
			JSONObject object = new JSONObject(jsondata);
			JSONArray jsonArray = object.getJSONArray(strArrkey);
			String strarr [] = new String[jsonArray.length()];
			
			for (int i = 0; i < jsonArray.length(); i++) {
				strarr[i]=jsonArray.getJSONObject(i).getString(objectkey);
			}
			
			return strarr;
		} catch (JSONException e) {
			e.printStackTrace();
			LogUtil.e("test", "解析json数据异常");
		}
		return null;
	}
	
	
	/**
	 * 
	 * @param strArr 字符串数组
	 * @return set
	 *
	 */
	public static Set<String> makeStrArr2Set(String [] strArr){

		Set<String> set = new TreeSet<String>();

		for (int i = 0; i < strArr.length; i++) {
			set.add(strArr[i]);
		}

		return set;
	}

}
