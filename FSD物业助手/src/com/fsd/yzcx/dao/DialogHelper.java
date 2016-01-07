package com.fsd.yzcx.dao;

import com.fsd.yzcx.R;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.View;

public class DialogHelper {

	//显示进度条的方法
	public static void showDialog(Context context,boolean isShow){
		ProgressDialog dialog  = new ProgressDialog(context);
		dialog.setView(View.inflate(context, R.layout.http_progress, null));
		if(isShow){
			dialog.show();
		}else{
			dialog.dismiss();
		}
	}
	
}
