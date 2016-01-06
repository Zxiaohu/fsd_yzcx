package com.fsd.yzcx.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fsd.yzcx.R;
import com.fsd.yzcx.tools.LogUtil;
import com.fsd.yzcx.ui.fragment.base.BaseFragment;
import com.fsd.yzcx.ui.view.UCListItem;
import com.google.gson.Gson;

public class UserInfoFragment extends BaseFragment {

	

	private UCListItem uclt_photo;//相册
	private UCListItem uclt_nickname;//昵称
	private UCListItem uclt_score;//积分
	private UCListItem uclt_address;//地址
	private UserInfo userInfo;//用户信息
	
	class UserInfo{//用户信息
		public String flag;//标记
		public String info;//提示信息
		public String photo;//相片
		public String address;//地址
		public int score;//积分
	}
	
	public View initView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mRootView = inflater.inflate(R.layout.fragment_userinfo, null);
		
	
		String userinfo=getArguments().getString("userinfo");
		//解析对象
		this.userInfo=new Gson().fromJson(userinfo, UserInfo.class);
		
		//初始化当前的view
		initCurrentView();
		
		return mRootView;
	}
	
	private void initCurrentView() {
		
		uclt_photo =(UCListItem) mRootView.findViewById(R.id.ucli_photo);
		uclt_nickname=(UCListItem) mRootView.findViewById(R.id.ucli_nickname);
		uclt_score=(UCListItem) mRootView.findViewById(R.id.ucli_score);
		uclt_address=(UCListItem) mRootView.findViewById(R.id.ucli_address);
		
		
		//判断用户的值并设置
		String nickname="请设置信息";
		String score="没有积分";
		String address="请设置信息";
		
		
		if(!userInfo.address.equals("")){
			address=userInfo.address;
		}else if(!(userInfo.score==0)){
			score=userInfo.score+"";
		}
		if(userInfo.photo.equals("")){
			uclt_photo.setIVtitle(R.drawable.user_96px);
			uclt_photo.setTvContent("请设置头像");
		}
		uclt_nickname.setTvContent(nickname);
		uclt_score.setTvContent(score);
		uclt_address.setTvContent(address);
		
	}
	public void initData(Bundle bundle) {
		
	}

}
