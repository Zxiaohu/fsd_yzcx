package com.fsd.yzcx.ui.fragment.user;

import java.io.File;
import java.io.IOException;

import com.fsd.yzcx.R;
import com.fsd.yzcx.dao.user.UserDao;
import com.fsd.yzcx.dao.user.UserDao.UserDaoListener;
import com.fsd.yzcx.tools.LogUtil;
import com.fsd.yzcx.tools.SystemTools;
import com.fsd.yzcx.ui.actvity.LoginActivity;
import com.fsd.yzcx.ui.actvity.MainActivity;
import com.fsd.yzcx.ui.actvity.TempActivity;
import com.fsd.yzcx.ui.fragment.base.BaseFragment;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 用户中心的页面
 * @author zxh
 *
 */
public class UserCenterFragment extends BaseFragment {

	@ViewInject(R.id.tv_username)
	private TextView tv_username;//用户昵称

	@ViewInject(R.id.tv_fools)//用户房间的信息
	private TextView tv_fools;

	@ViewInject(R.id.iv_user)//用户的头像
	private ImageView iv_user;

	@ViewInject(R.id.btn_exit)//退出的按钮
	private Button btn_exit;


	@ViewInject(R.id.tv_userinfo)//个人信息的
	private TextView tv_userinfo;


	private SharedPreferences mPerferences ;


	@Override
	public void onResume() {
		super.onResume();
		userInfoDao();
	}
	
	public View initView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mRootView =inflater.inflate(R.layout.fragment_usercenter, null);

		ViewUtils.inject(this, mRootView);	

		return mRootView;
	}


	public void initData(Bundle bundle) {


		//用户的信息处理相关操作	
		/**
		 * 1.判断用户是否登录
		 * 	（登录过的信息会储存在本地文件）
		 *   1.1判断文件中是否存存在，存在的话就直接设置，用户的相关信息并显示 
		 * 	 1.2没有登录点击可以跳转到登录页去登录,在设置信息
		 */
		userInfoDao();
		//用户现则头像的相关操作
		//userHeadImgDao();

		//个人信息设置
		setUserinfo();

		//注销的相关设置
		exitDao();
	}

	/**
	 * 个人信息设置
	 */
	private void setUserinfo() {
		final String uname=mPerferences.getString("uname","");

		tv_userinfo.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				UserDao userDao = new UserDao(mActivity);
				if(!uname.equals("")){
					//从服务器获取用户的信息
					userDao.fetchUserInfo(uname, new UserDaoListener() {
						public void fetchUserInfo(String jsonUserInfo) {	
							Intent intent = new Intent(mActivity,TempActivity.class);
							intent.putExtra("flag", 1);
							intent.putExtra("userinfo",jsonUserInfo);
							mActivity.startActivity(intent);


						}
					});	
				}else{
					SystemTools.showToastInfo(mActivity, "请登录", 3000, 2);
				}
			}
		});
	}

	/***
	 * 用户头像处理方法
	 */
	private void userHeadImgDao() {
		/**
		 * 分析:用户点击头像的时候：
		 */

	}



	/**
	 * 注销账号的操作
	 */
	private void exitDao() {
		btn_exit.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {	
				//判处对话框询问是否真的注销
				mPerferences.edit().clear().commit();
				((MainActivity)mActivity).tabHost.setCurrentTab(0);//刷新一下
			}
		});
	}


	/**
	 * 用户登录的相关操作
	 */
	private void userInfoDao() {


		//初始化SharedPreferences
		mPerferences=PreferenceManager.getDefaultSharedPreferences(mActivity);
		//判断用户是否登录
		if(isLogin()){
			tv_username.setClickable(false);
		}else{
			///没有登录跳转到登录
			tv_username.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					mActivity.startActivity(new Intent(mActivity,LoginActivity.class));
				}
			});
		};
	}


	/**
	 * 判断用户是否登录
	 * @return true(已经登录)反之
	 */
	private boolean isLogin() {
		boolean isLogin=false;


		String uname=mPerferences.getString("uname", null);
		String nickname=mPerferences.getString("nickname",null);

		LogUtil.w(this.getClass().getSimpleName(), uname+"------"+nickname);

		if(uname!=null&&nickname!=null){

			tv_username.setText(nickname);
			tv_fools.setText("手机号:"+uname);
			if(nickname.equals("")){
				tv_username.setText("请设置昵称");
			}
			if(uname.equals("")){
				tv_fools.setText("请完善个人信息");
			}

			isLogin=true;
		}else{
			tv_username.setText("请点击登录");
			tv_fools.setText("");
		}	

		LogUtil.w(this.getClass().getSimpleName(), "判断用户是否登录执行");
		return isLogin;
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

	}

}
