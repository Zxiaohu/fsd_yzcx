package com.fsd.yzcx.tools.http;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;

import com.fsd.yzcx.tools.LogUtil;
import com.fsd.yzcx.tools.http.impl.MyHttpListener;
import com.lidroid.xutils.http.RequestParams;

public class DialogHttp extends BaseHttpTools{

	//private Activity context;//这个context必须是activity 用来创建加载进度条的
	private ProgressDialog dialog;
	public DialogHttp(Context context,String url, RequestParams params, String tag,
			MyHttpListener httpListener) {
		super(url, params, tag, httpListener);
		//this.context=context;
		this.dialog=new ProgressDialog(context);
	}

	protected void onHttpRun() {
		LogUtil.d("test", "我开启了进度条");
		dialog.show();
		
	}
	protected void onHttpStop(){

		LogUtil.d("test", "我停止了进度条");
		
		dialog.dismiss();
		
	}
	
}
