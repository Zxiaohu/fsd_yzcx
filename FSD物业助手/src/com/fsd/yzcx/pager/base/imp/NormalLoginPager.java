package com.fsd.yzcx.pager.base.imp;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.fsd.yzcx.R;
import com.fsd.yzcx.actvity.MainActivity;
import com.fsd.yzcx.global.YzInfo;
import com.fsd.yzcx.pager.base.BasePager;
import com.fsd.yzcx.tools.ForEmptyTool;
import com.fsd.yzcx.tools.http.HttpTools;
import com.fsd.yzcx.tools.http.HttpTools.MyHttpListener;
import com.fsd.yzcx.tools.LogUtil;
import com.fsd.yzcx.tools.SystemTools;
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
	private void send4serverCh(String uid, String upwd) {
		RequestParams params = new RequestParams("utf-8");
		params.addBodyParameter("uname",uid);
		params.addBodyParameter("upwd",upwd);
		LogUtil.i("NormalLoginPager", HttpTools.LOGIN);
		
		HttpTools.send(HttpTools.LOGIN,params, mActivity, new MyHttpListener() {			
			public void finish(String response) {
				//去掉中括号将结果解析成对象
				String newRes=response.substring(1, response.length()-1);
				LogUtil.d(tag,newRes);
				
				Gson gson= new Gson();
				
				//普通登录返回的业主信息
				YzInfo yzInfo= gson.fromJson(newRes,YzInfo.class);
				
				LogUtil.w(tag, yzInfo.toString());
				
				if(yzInfo.flag==1){
					SystemTools.showToastInfo(mActivity, "登录成功", 3000, 1);
					mActivity.startActivity(new Intent(mActivity,MainActivity.class));
				}else{
					SystemTools.showToastInfo(mActivity, yzInfo.info, 3000, 2);
				}		
			}
		},"登录服务器");
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
