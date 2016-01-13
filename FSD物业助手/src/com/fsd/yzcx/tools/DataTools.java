package com.fsd.yzcx.tools;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;

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
 * 将数组转化为json格式的数组
 * @param strArr 数组
 * @param arrkey 数组的key
 * @param objectkey 具体值的key
 * @return
 */
	public static String mStrArr2Json(String strArr[],String arrkey,String objectkey){

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
