package com.fsd.yzcx.ui.fragment.user;
import com.fsd.yzcx.R;
import com.fsd.yzcx.dao.db.SharedPfDao;
import com.fsd.yzcx.dao.user.UserDao;
import com.fsd.yzcx.dao.user.UserParamsName;
import com.fsd.yzcx.dao.user.UserDao.UserDaoListener;
import com.fsd.yzcx.dao.user.UserInfo;
import com.fsd.yzcx.tools.DataTools;
import com.fsd.yzcx.tools.LogUtil;
import com.fsd.yzcx.tools.SystemTools;
import com.fsd.yzcx.ui.actvity.LoginActivity;
import com.fsd.yzcx.ui.actvity.MainActivity;
import com.fsd.yzcx.ui.actvity.MapActivity;
import com.fsd.yzcx.ui.actvity.TempActivity;
import com.fsd.yzcx.ui.fragment.base.BaseFragment;
import com.fsd.yzcx.ui.view.dialog.TipDialog;
import com.fsd.yzcx.ui.view.dialog.TipDialog.callBackOk;
import com.google.gson.Gson;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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

	@ViewInject(R.id.tv_fuwu_details)//个人信息的
	private TextView tv_fuwu_details;



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
		//当前页面的用户信息的设置
		userInfoDao();

		//注销的相关设置
		exitDao();
		//用户现则头像的相关操作
		//userHeadImgDao();
		//个人信息设置
		setUserinfo();
		
		//服务详情设置
		setFuwudetails();
	}

	private void setFuwudetails() {
		
		tv_fuwu_details.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
			
				mActivity.startActivity(new Intent(mActivity,MapActivity.class));
			}
		});
		
	}

	/**
	 * 个人信息修改设置
	 */
	private void setUserinfo() {
		tv_userinfo.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				//先判断是否登录
				final String uname=SharedPfDao.queryStr(UserParamsName.UNAME.getName());
				if(uname!=null){
					//跳转页面
					UserDao userDao = new UserDao(mActivity);
					//从服务器获取用户的信息
					userDao.fetchUserInfo(uname, new UserDaoListener() {
						public void fetchUserInfo(String jsonUserInfo) {	
							LogUtil.d("test", jsonUserInfo);
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
		if(isLogin()){//判断是否登录
			btn_exit.setClickable(true);
			btn_exit.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {	
					//判处对话框询问是否真的注销
					final TipDialog tipDialog = new TipDialog();
					tipDialog.setCallBackOK(new callBackOk() {
						public void onOkClick() {
							SharedPfDao.delAll();//清空所有用户信息
							((MainActivity)mActivity).tabHost.setCurrentTab(0);//刷新一下
							tipDialog.dismiss();
						}
					},"您真的要注销吗？");
					tipDialog.show(frgManager, "tip_exit");
				}
			});
		}else{
			btn_exit.setClickable(false);
		}
	}


	/**
	 * 用户登录的相关操作
	 */
	private void userInfoDao() {


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
		String uname=SharedPfDao.queryStr("uname");
		String nickname=SharedPfDao.queryStr("nickname");

		if(uname!=null&&nickname!=null){
			tv_username.setText(nickname);
			tv_fools.setText("手机号:"+uname);
			if(nickname.equals("")){
				tv_username.setText("请设置昵称");
			}
			if(uname.equals("")){
				tv_fools.setText("请完善个人信息");
			}
			
			
			addUserInfo2pf(uname);//添加用户信息到本地

			
			isLogin=true;
		}else{
			tv_username.setText("请点击登录");
			tv_fools.setText("");
		}	

		LogUtil.w(this.getClass().getSimpleName(), "判断用户是否登录执行");
		return isLogin;
	}



	/***
	 * 将用户的信息存到本地文件中
	 * 
	 */
	private void addUserInfo2pf(String uname) {
		//用户信息处理类
		UserDao userDao = new UserDao(mActivity);
		userDao.fetchUserInfo(uname,new UserDaoListener() {
			public void fetchUserInfo(String jsonUserInfo) {
				//解析用户的信息数据
				UserInfo userInfo=new Gson().fromJson(jsonUserInfo, UserInfo.class);
				
				
				//判断是否是多房间的用户,
				/**
				 * 如果是多用户就用将该信息转换为json格式的数据存起来呢
				 */
				String houseid=userInfo.getHouseid();
				String housename=userInfo.getHousename();
				if(DataTools.isHaveIn("@",houseid )){
					houseid=DataTools.mStrArr2Json(houseid.split("@"),"jsonHouseid","houseid");
					housename=DataTools.mStrArr2Json(housename.split("\\|"),"jsonHousename","housename");
				}				
				//不用解析，直接存储
				SharedPfDao.insertData(
						new String[]{
								UserParamsName.NICKNAME.getName(),
								UserParamsName.ADDRESS.getName(),
								UserParamsName.HOUSE_ID.getName(),
								UserParamsName.HOUSE_NAME.getName()},
								new String[]{
								userInfo.getNickname(),
								userInfo.getAddress(),
								houseid,
								housename});
			}
		});

	}

}
