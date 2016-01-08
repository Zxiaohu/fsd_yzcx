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
