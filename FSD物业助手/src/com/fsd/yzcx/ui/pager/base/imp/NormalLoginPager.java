package com.fsd.yzcx.ui.pager.base.imp;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.fsd.yzcx.R;
import com.fsd.yzcx.dao.login.LoginDao;
import com.fsd.yzcx.dao.login.LoginDao.myInterfaceCheckNormalLogin;
import com.fsd.yzcx.dao.login.LoginUserInfo;
import com.fsd.yzcx.tools.ForEmptyTool;
import com.fsd.yzcx.tools.LogUtil;
import com.fsd.yzcx.tools.SystemTools;
import com.fsd.yzcx.ui.actvity.MainActivity;
import com.fsd.yzcx.ui.pager.base.BasePager;
import com.google.gson.Gson;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.view.annotation.ViewInject;
/**
 * NormalLoginPager
 * @author zxh
 * 默认账号登录
 */
public class NormalLoginPager extends BasePager {
	String tag = "NormalLoginPager";
	@ViewInject(R.id.tv_chooseroomid)
	private TextView tv_chroom;//房号登入
	@ViewInject(R.id.btn_login)
	private Button btn_login;//登录按钮
	@ViewInject(R.id.et_yzid)
	private EditText et_name;//登录的编号
	@ViewInject(R.id.et_pwd)
	private EditText et_pwd;//登录密码

	private MyOnclickimp myOnclickimp;//接口
	private SharedPreferences preferences;

	public NormalLoginPager(Activity activity) {
		super(activity);
	}
	public View initView() {
		mRootView=View.inflate(mActivity,R.layout.pager_nomal_login, null);
		ViewUtils.inject(this,mRootView);
		return mRootView;
	}

	public void initData() {
		LogUtil.i(tag, "第一页数据加载中");
		//跳转到房号登入的接口
		setEvent4ChRoom();	
		//登录操作
		setEvent4Login();

	}

	private void setEvent4Login() {
		btn_login.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				String uid=et_name.getText().toString().trim();
				String upwd=et_pwd.getText().toString().trim();	
				//验证合法性
				if(ForEmptyTool.isAnyEmpty(new Object []{et_name,et_pwd})){

					SystemTools.showToastInfo(mActivity, "请填写完整信息",3000, 1);
				}else{
					//请求网络行验证用户名和密码
					send4serverCh(uid,upwd);
				};		
			}

		});
	}

	/**
	 * 服务器验证
	 * @param uid
	 * @param upwd
	 */
	private void send4serverCh(String uname, String upwd) {
		
		LoginDao loginDao = new LoginDao();//验证密码和用户
		loginDao.checkNormalLogin(uname, upwd, new myInterfaceCheckNormalLogin() {
			public void checkNormalLogin(LoginUserInfo userinfo) {
				if(userinfo.flag==0){
						SystemTools.showToastInfo(mActivity,userinfo.info, 3000, 2);
					}else{					
						writerUserInfo2PF(userinfo.uname,userinfo.nickname);//写入文件中
						SystemTools.showToastInfo(mActivity,userinfo.info, 3000, 1);
						mActivity.startActivity(new Intent(mActivity,MainActivity.class));
						mActivity.finish();
					}
			}
		});	
	}


	private void writerUserInfo2PF(String uname, String nickname) {

		preferences = PreferenceManager.getDefaultSharedPreferences(mActivity);
				Editor edit = preferences.edit();
				edit.clear();
				
				edit.putString("nickname", nickname);
				edit.putString("uname", uname);
				
				LogUtil.e(tag, uname+nickname);
				edit.commit();
	}
	/**
	 * 开放选择房号登录
	 */
	private void setEvent4ChRoom() {
		tv_chroom.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				myOnclickimp.onClick();
			}
		});
	}
	public interface MyOnclickimp{
		public void onClick();
	}
	public void setImp(MyOnclickimp myOnclickimp){
		this.myOnclickimp=myOnclickimp;
	}
}
