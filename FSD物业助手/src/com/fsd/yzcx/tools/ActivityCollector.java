package com.fsd.yzcx.tools;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.FragmentActivity;

/**
 * activity管理者
 * @author zxh
 *
 */
public class ActivityCollector {

	public static List<FragmentActivity> activities = new ArrayList<FragmentActivity>();

	public static void  addActivity(FragmentActivity activity){
		activities.add(activity);
	}
	public static void removeActivity(FragmentActivity activity){
		activities.remove(activity);
	}
	public static void finshAll(){
		for (FragmentActivity activity : activities) {
			if(!activity.isFinishing()){
				activity.finish();}
			
		}
	}
}
