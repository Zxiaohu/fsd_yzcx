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

	/**
	 * 
	 * @param uname 检测该值是否是否在缓存文件中
	 * @return
	 */
	public static boolean isHave(String key){
		
		if(null!=M_PREFERENCES.getString(key, null)){
			return true;
		}
		return false;
	}
	
	/**
	 * 查看所有数据
	 * @return
	 */
	public static String getAll(){

		Map<String, ?> all = M_PREFERENCES.getAll();
		StringBuffer sb = new StringBuffer();
		
		for (String key : all.keySet()) {
			sb.append("key= "+ key + " value= " + all.get(key));
			  }
		
		return sb.toString();
	}

	/**
	 * 清除所有的文件信息
	 */
	public static void delAll(){
		M_EDITOR.clear().commit();
	}
	/**
	 * 插入一条string类型的数据
	 * @param uname
	 * @param strContent
	 */
	public static void insertData(String uname,String strContent){
		M_EDITOR.putString(uname,strContent).commit();
	}
	
	/**
	 * 插入多条string类型的数据
	 * @param key 
	 * @param strContent
	 */
	public static void insertData(String []key,String []strContent){
		for (int i = 0; i < strContent.length; i++) {
			M_EDITOR.putString(key[i], strContent[i]);
		}
		M_EDITOR.commit();
		
	}
	/**
	 * 插入set数据
	 * @param key
	 * @param setContent
	 */
	public static void insertData(String key,Set<String> setContent){
		M_EDITOR.putStringSet(key,setContent).commit();
	}
	/**
	 * 根据key查询String的数据
	 * @param key
	 * @return
	 */
	public static String queryStr(String key){	
		return M_PREFERENCES.getString(key, null);
	}
	
	/**
	 * 根据key查找set集合数据
	 * @param key
	 * @return
	 */
	public static Set<String> querySet(String key){
		
		return M_PREFERENCES.getStringSet(key,null);
	}
	
	
	 
}
