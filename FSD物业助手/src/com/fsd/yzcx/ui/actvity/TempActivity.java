package com.fsd.yzcx.ui.actvity;

import android.app.Activity;
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
import com.fsd.yzcx.ui.fragment.order.DetailsListsFragment;
import com.fsd.yzcx.ui.fragment.user.AlertPwdFragment;
import com.fsd.yzcx.ui.fragment.user.UserInfoFragment;

public class TempActivity extends BaseActivity {
	private FragmentManager fManager;	//fragment管理器
	private static BaseFragment baseFragment;//
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
			String userifno=intent.getStringExtra("data");//数据
			bundle.putString("userinfo", userifno);
			baseFragment = new UserInfoFragment();//用户个人信息的fragment
			if(userifno!=null){
				baseFragment.setArguments(bundle);//传送数据
			}
			break;
		case 2:
			setFragmentAndData(bundle,new SuggestionsFragment());//投诉建议的fragment
			break;
		case 3:
			setFragmentAndData(bundle,new PropertyFragment());//物业报修的fragment
			break;
		case 4:
			setFragmentAndData(bundle,new PaidFragment());//有偿服务的Fragment
			break;
		case 5:
			setFragmentAndData(bundle,new AlertPwdFragment());//修改密码的fragment
			break;
		case 6:
			setFragmentAndData(bundle,new DetailsListsFragment());//修改密码的fragment
			break;
		default:
			break;
		}
		
		//设置动画效果
		transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
		
		//填充fragment并提交
		transaction.add(R.id.fl_tempAty_content, baseFragment);
		transaction.commit();
		
		//return baseFragment;
	}
	
	/**
	 * 需要填充数据的话走这个方法去填充
	 * @param bundle
	 * @param Fragment
	 */
	private void setFragmentAndData(Bundle bundle,BaseFragment Fragment) {
		String data=intent.getStringExtra("data");
		bundle.putString("data", data);
		baseFragment = Fragment;	
		if(data!=null){
			baseFragment.setArguments(bundle);
		}
	}
	
	/**
	 * 
	 * @param activity 需要跳转到本activity加载的方法
	 * @param flag 标记对应加载的fragment
	 * @param data 需要携带的数据
	 */
	public static void openFragment(Activity activity,int flag,String data){
		Intent intent = new Intent(activity,TempActivity.class);
		intent.putExtra("flag", flag);	
		if(data!=null){//有数据就填充
			intent.putExtra("data", data);
		}
		activity.startActivity(intent);
	}
	
	
	
	
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		if(keyCode == KeyEvent.KEYCODE_BACK ){
			this.finish();
		}
		return true;
	}
	
	
	
}
