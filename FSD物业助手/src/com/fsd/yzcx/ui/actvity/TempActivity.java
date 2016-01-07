package com.fsd.yzcx.ui.actvity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;

import com.fsd.yzcx.R;
import com.fsd.yzcx.tools.LogUtil;
import com.fsd.yzcx.ui.actvity.base.BaseActivity;
import com.fsd.yzcx.ui.fragment.base.BaseFragment;
import com.fsd.yzcx.ui.fragment.fuwu.PaidFragment;
import com.fsd.yzcx.ui.fragment.fuwu.PropertyFragment;
import com.fsd.yzcx.ui.fragment.fuwu.SuggestionsFragment;
import com.fsd.yzcx.ui.fragment.user.UserInfoFragment;

public class TempActivity extends BaseActivity {
	private FragmentManager fManager;	//fragment管理器
	private BaseFragment baseFragment;//
	private FragmentTransaction transaction;//fragment事务
	private Intent intent;
	
	
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		
		
		this.setContentView(R.layout.activity_temp);
		//根据传过来的标记动态的去加载布局
		intent = this.getIntent();		
		int flag=intent.getIntExtra("flag", 0);		
		createFragment(flag);
		
	}


	/**
	 * 动态加载器
	 * @param flag
	 */
	private void createFragment(int flag) {
	
		fManager = getSupportFragmentManager();
		transaction =fManager.beginTransaction();
		Bundle bundle= new Bundle();
		switch (flag) {
		case 0:
			//结束当前的activity什么都不做
			this.finish();
			break;
		case 1:
			//加载个人信息的fragment
			String userifno=intent.getStringExtra("userinfo");
			
			LogUtil.i(this.getClass().getSimpleName(), userifno);
			bundle.putString("userinfo", userifno);
			baseFragment = new UserInfoFragment();
			
			if(userifno!=null){
				baseFragment.setArguments(bundle);
			}
			break;
		case 2:
			baseFragment = new SuggestionsFragment();//投诉建议
			
			break;
			
		case 3:
			baseFragment = new PropertyFragment();//物业报修
			
			break;
		case 4:
			
			baseFragment = new PaidFragment();//有偿服务
			
			break;
		default:
			break;
		}
		transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

		transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
		
		transaction.add(R.id.fl_tempAty_content, baseFragment);
		transaction.commit();
				
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		this.finish();
		return super.onKeyDown(keyCode, event);
	}
}
