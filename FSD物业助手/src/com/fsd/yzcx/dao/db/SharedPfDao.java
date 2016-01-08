package com.fsd.yzcx.dao.db;

import java.util.Map;
import java.util.Set;

import org.json.JSONObject;

import com.fsd.yzcx.ui.actvity.base.MyApplication;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * SharedPreferences的数据处理类
 * @author zxh
 *
 */
public  class SharedPfDao {
	protected static final SharedPreferences M_PREFERENCES;
	protected static final Editor M_EDITOR;
	
	static{
		M_PREFERENCES=MyApplication.getSharedPreferences();
		M_EDITOR=M_PREFERENCES.edit();
	}

	public static String getAll(){

		Map<String, ?> all = M_PREFERENCES.getAll();
		StringBuffer sb = new StringBuffer();
		
		for (String key : all.keySet()) {
			sb.append("key= "+ key + " value= " + all.get(key));
			  }
		
		return sb.toString();
	}

	public static void delAll(){
		M_EDITOR.clear().commit();
	}
	
	public static void insertData(String uname,String strContent){
		M_EDITOR.putString(uname,strContent).commit();
	}
	
	public static void insertData(String []key,String []strContent){
		for (int i = 0; i < strContent.length; i++) {
			M_EDITOR.putString(key[i], strContent[i]);
		}
		M_EDITOR.commit();
		
	}
	public static void insertData(String key,Set<String> setContent){
		M_EDITOR.putStringSet(key,setContent).commit();
	}
	
	public static String queryStr(String key){	
		return M_PREFERENCES.getString(key, null);
	}
	
	public static Set<String> querySet(String key){
		
		return M_PREFERENCES.getStringSet(key,null);
	}
	
	
	 
}
