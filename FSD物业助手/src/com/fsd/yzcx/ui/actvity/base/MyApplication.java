package com.fsd.yzcx.ui.actvity.base;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class MyApplication extends Application {
	
	
	//public static RequestQueue requestQueue;//全局请求队列
	private static Context context;//全局context对象

	public void onCreate() {
		super.onCreate();
		// RequestManager.getInstance().init(this);//初始化工具
		//requestQueue = Volley.newRequestQueue(getApplicationContext());//初始化全局请求队列
		this.context= getBaseContext();
	}
	
	
	/**
	 * 获取全局SharedPreferences
	 * @return
	 */
	public static SharedPreferences getSharedPreferences(){
		return PreferenceManager.getDefaultSharedPreferences(context);
	}
	
	
	public static Context getContext(){
		
		return context;
	}
}
