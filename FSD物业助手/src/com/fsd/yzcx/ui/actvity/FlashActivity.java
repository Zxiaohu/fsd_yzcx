package com.fsd.yzcx.ui.actvity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;

import com.fsd.yzcx.R;
import com.fsd.yzcx.ui.actvity.base.BaseActivity;

/**
 * 闪屏页
 * @author zxh
 *
 */
public class FlashActivity extends BaseActivity {
	private final int SPLASH_DISPLAY_LENGHT = 5000; // 延迟5秒
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION| View.SYSTEM_UI_FLAG_FULLSCREEN;  
		
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		View decorView = getWindow().getDecorView();  
		
		// Hide both the navigation bar and the status bar.  
		// SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher, but as  
		// a general rule, you should design your app to hide the status bar whenever you  
		// hide the navigation bar.  
		
		decorView.setSystemUiVisibility(uiOptions);
		setContentView(R.layout.activity_flash);
		
		//判断是否更新
		//延时跳转主界面
		new Handler().postDelayed(new Runnable() {
			public void run() {
				startActivity(new Intent(FlashActivity.this,MainActivity.class ));
				FlashActivity.this.finish();
			}
			
			
		}, SPLASH_DISPLAY_LENGHT);
		
	}
	
	
}
