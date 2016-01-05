package com.fsd.yzcx.ui.actvity.base;

import com.fsd.yzcx.tools.ActivityCollector;
import com.fsd.yzcx.tools.LogUtil;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class BaseActivity extends FragmentActivity {

	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		LogUtil.d("BaseActivity", this.getClass().getSimpleName()+"被加载");
		ActivityCollector.addActivity(this);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		LogUtil.e("BaseActivity", this.getClass().getSimpleName()+"被销毁");
		ActivityCollector.removeActivity(this);
	}
}
