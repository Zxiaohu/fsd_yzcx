package com.fsd.yzcx.tools;

import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Spinner;

public class ForEmptyTool {
	
	/**
	 * 判断传入的组件引用内容是否有为空的，
	 * 
	 * 
	 * @param objs
	 * @return 只要有一个为空则返回true,都不为空则返回false
	 */
	public static boolean isAnyEmpty(Object[] objs){
		for (Object object : objs) {
			if(object instanceof Spinner){
				Spinner sp = (Spinner) object;
				if(0 == sp.getSelectedItemPosition())
					return true;
			}else if(object instanceof EditText){
				EditText et = (EditText) object;
				if(TextUtils.isEmpty(et.getText().toString())){
					return true;
				}
			}
		}
		return false;
		
	}
	
	/**
	 * 清空所有EditText和Spinner的内容
	 * @param objs
	 */
	public static void resetAll(Object[] objs){
		for (Object object : objs) {
			if(object instanceof Spinner){
				Spinner sp = (Spinner) object;
				sp.setSelection(0);
			}else if(object instanceof EditText){
				EditText et = (EditText) object;
				et.setText("");
			}
		}
	}
}
