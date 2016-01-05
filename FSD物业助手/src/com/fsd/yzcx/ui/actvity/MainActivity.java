package com.fsd.yzcx.ui.actvity;

import com.fsd.yzcx.R;
import com.fsd.yzcx.ui.actvity.base.BaseActivity;
import com.fsd.yzcx.ui.fragment.FuWuFragment;
import com.fsd.yzcx.ui.fragment.UserCenterFragment;
import com.fsd.yzcx.ui.view.MyTabItem;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.KeyEvent;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;

public class MainActivity extends BaseActivity {
	public FragmentTabHost tabHost;
	private final String TAG_FUWU="fuwu";
	private final String TAG_USER_CENTER="user_center";
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		this.setContentView(R.layout.activity_main);

		//初始化tabhost
		initTabhost();


	}
	/**
	 * 初始化tabhost
	 */
	private void initTabhost() {

		//1.初始化tabhost
		tabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		tabHost.setup(this, getSupportFragmentManager(),R.id.fl_mian_content);

		//2.初始化tabSpec;
		final MyTabItem fuwuTabItem = new MyTabItem(this);
		fuwuTabItem.setTitle("服务");
		fuwuTabItem.setImage(R.drawable.star_green, R.drawable.star_red);

		tabHost.addTab(tabHost.newTabSpec(TAG_FUWU).setIndicator(fuwuTabItem),
				FuWuFragment.class, null);

		final MyTabItem usercenterTabItem= new MyTabItem(this);
		usercenterTabItem.setTitle("我的");
		usercenterTabItem.setImage(R.drawable.star_green, R.drawable.star_red);

		tabHost.addTab(tabHost.newTabSpec(TAG_USER_CENTER).setIndicator(usercenterTabItem),
				UserCenterFragment.class, null);

		fuwuTabItem.setSelect(true);
		tabHost.setCurrentTabByTag(TAG_FUWU);

		//3.监听tabhost选中事件
		tabHost.setOnTabChangedListener(new OnTabChangeListener() {
			public void onTabChanged(String tabId) {
				fuwuTabItem.setSelect(false);
				usercenterTabItem.setSelect(false);
				if(tabId.equals(TAG_FUWU)){

					fuwuTabItem.setSelect(true);
					tabHost.setCurrentTabByTag(TAG_FUWU);

				}else if(tabId.equals(TAG_USER_CENTER)){
					usercenterTabItem.setSelect(true);
					tabHost.setCurrentTabByTag(TAG_USER_CENTER);
				}
			}
		});

	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		int flag=0;
		if(tabHost.getCurrentTabTag().equals(TAG_FUWU)){
			if(flag==1){
				
				this.finish();
			
			}else{
				return super.onKeyDown(keyCode, event);
			}
		}else if(tabHost.getCurrentTabTag().equals(TAG_USER_CENTER)){
			flag=1;
			tabHost.setCurrentTab(0);
			
		}
		
		return true;
	}
}
