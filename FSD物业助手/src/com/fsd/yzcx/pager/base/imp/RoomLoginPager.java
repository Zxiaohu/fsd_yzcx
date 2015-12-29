package com.fsd.yzcx.pager.base.imp;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.fsd.yzcx.R;
import com.fsd.yzcx.model.yzinfo.YzInfo;
import com.fsd.yzcx.pager.base.BasePager;
import com.fsd.yzcx.tools.http.HttpTools;
import com.fsd.yzcx.tools.http.HttpTools.MyHttpListener;
import com.fsd.yzcx.tools.JsonTools;
import com.fsd.yzcx.tools.LogUtil;
import com.fsd.yzcx.tools.SystemTools;
import com.fsd.yzcx.view.dialog.RoomChDialog;
import com.fsd.yzcx.view.dialog.RoomChDialog.MyDialogItf;
import com.google.gson.Gson;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * RoomLoginPager
 * @author zxh
 * 选择房号登录
 */
public class RoomLoginPager extends BasePager {
	String tag="RoomLoginPager";

	@ViewInject(R.id.tv_chroom)
	public TextView tv_chooseroom;
	@ViewInject(R.id.tv_yzname)
	private TextView tv_yzname;
	@ViewInject(R.id.tv_phonenum)
	private TextView tv_yzphone;
	@ViewInject(R.id.et_phonenum)
	private TextView et_phonenum;
	public RoomLoginPager(Activity activity) {
		super(activity);
	}

	public View initView() {
		mRootView=View.inflate(mActivity, R.layout.pager_room_login, null);
		ViewUtils.inject(this, mRootView);
		return mRootView;
	}

	public void initData() {

		LogUtil.i(tag, "第二页数据加载中");
		//给选择房号做点击事件
		setChClick();
	}

	/**
	 * 点击事件
	 */
	public void setChClick(){
		tv_chooseroom.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {



				//弹出对话框
				FragmentManager fragmentManager = ((FragmentActivity)mActivity).getSupportFragmentManager();
				RoomChDialog chDialog= new RoomChDialog();
				if(!chDialog.isAdded()){
					chDialog.show(fragmentManager, "chDialog");
				}
				//选择完成后湖区用户编号,并请求网络获取用户 的姓名和电话
				chDialog.setItf(new MyDialogItf() {
					public void onOk(String yz_num) {
						//发送网络请求获取用户信息
						getUserInfo4server(yz_num);
					}

				});
			}
		});
	}

	/**
	 * 获取用户的信息
	 * @param yz_num
	 */
	private void getUserInfo4server(String yz_num) {
		RequestParams params =  new RequestParams();
		String [] yz_nums=yz_num.split("-");

		LogUtil.i(tag,yz_num);

		params.addBodyParameter("PrecinctID",yz_nums[0]);
		params.addBodyParameter("BuildingID",yz_nums[1]);
		params.addBodyParameter("FloorID",yz_nums[2]);
		params.addBodyParameter("RoomID",yz_nums[3]);

		for (int i = 0; i < yz_nums.length; i++) {
			String string = yz_nums[i];
			LogUtil.i(tag,string);
		}

		HttpTools.send(HttpTools.ROOM_INFO, params, mActivity,new MyHttpListener() {
			public void finish(String response) {

				LogUtil.i(tag,response);
				
				String newRes=response.substring(1,response.length()-1);
				if(JsonTools.isJson(newRes)){
					SystemTools.showToastInfo(mActivity, "查询的数据有误", 3000, 2);
				}else{//如果是json数据开始解析
					//设置用户相关信息
					setUserIn4(newRes);
				}
			}

		},"获取业主信息");
	}
	/***
	 * 设置用户信息
	 * @param response
	 */
	private void setUserIn4(String newRes) {
		String string1="无结果";
		String string2="无结果";
		
		LogUtil.d(tag, newRes);
		Gson gson = new Gson();				
		
		YzInfo info = gson.fromJson(newRes, YzInfo.class);
		string1=info.getMastername();
		string2 =info.getTelnumber();
		
		et_phonenum.setText(string2);
		tv_yzname.setText(string1);
		tv_yzphone.setText(string2);
	}
	
	
}
