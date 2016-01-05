package com.fsd.yzcx.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fsd.yzcx.R;
import com.fsd.yzcx.ui.fragment.base.BaseFragment;
import com.fsd.yzcx.ui.view.UCListItem;

public class UserInfoFragment extends BaseFragment {

	
	private UCListItem uclt_nickname;//昵称
	private UCListItem uclt_score;//积分
	private UCListItem uclt_address;//地址
	
	
	public View initView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mRootView = inflater.inflate(R.layout.fragment_userinfo, null);
		
		//初始化当前的view
		initCurrentView();
		
		return mRootView;
	}
	
	private void initCurrentView() {
		
	
		uclt_nickname=(UCListItem) mRootView.findViewById(R.id.ucli_nickname);
		uclt_score=(UCListItem) mRootView.findViewById(R.id.ucli_score);
		uclt_address=(UCListItem) mRootView.findViewById(R.id.ucli_address);
		
		uclt_nickname.setTvContent("张大毛");
		uclt_score.setTvContent("1000分");
		uclt_address.setTvContent("黄金市黄金区黄金楼");
		
	}

	
	
	public void initData(Bundle bundle) {
		
	}

}
