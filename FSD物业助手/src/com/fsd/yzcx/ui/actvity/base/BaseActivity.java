package com.fsd.yzcx.ui.actvity.base;

import com.fsd.yzcx.R;
import com.fsd.yzcx.tools.ActivityCollector;
import com.fsd.yzcx.tools.LogUtil;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;
import android.view.WindowManager;

public class BaseActivity extends FragmentActivity {

	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		LogUtil.d("BaseActivity", this.getClass().getSimpleName()+"被加载");
		ActivityCollector.addActivity(this);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			setTranslucentStatus(true);
		}

		SystemBarTintManager tintManager = new SystemBarTintManager(this);
		tintManager.setStatusBarTintEnabled(true);
		tintManager.setStatusBarTintResource(com.fsd.yzcx.R.color.blue2);
		tintManager.setNavigationBarTintEnabled(true);
		tintManager.setNavigationBarTintResource(R.color.blue2);

	}
	private void setTranslucentStatus(boolean on) {
		Window win = getWindow();
		WindowManager.LayoutParams winParams = win.getAttributes();
		final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
		if (on) {
			winParams.flags |= bits;
		} else {
			winParams.flags &= ~bits;
		}
		win.setAttributes(winParams);
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		LogUtil.e("BaseActivity", this.getClass().getSimpleName()+"被销毁");
		ActivityCollector.removeActivity(this);
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
	}
}
