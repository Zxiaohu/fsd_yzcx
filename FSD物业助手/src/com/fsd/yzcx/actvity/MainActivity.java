package com.fsd.yzcx.actvity;

import com.fsd.yzcx.R;
import com.fsd.yzcx.actvity.base.BaseActivity;
import com.fsd.yzcx.fragment.FuWuFragment;
import com.fsd.yzcx.view.MyTabItem;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.widget.TabHost.TabSpec;

public class MainActivity extends BaseActivity {
	private FragmentTabHost tabHost;
	private final String TAG_FUWU="fuwu";
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		this.setContentView(R.layout.activity_main);
	
		//1.初始化tabhost
		tabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		tabHost.setup(this, getSupportFragmentManager());
		
		
		//2.初始化tabSpec
		TabSpec tabSpec = tabHost.newTabSpec(TAG_FUWU);
		//tabSpec.setIndicator("测试");
		
		tabSpec.setIndicator(new MyTabItem(this));
		
		
		//3.添加tabspec
		tabHost.addTab(tabSpec,FuWuFragment.class, null);
		
		
	}
}
