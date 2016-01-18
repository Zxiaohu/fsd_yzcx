package com.fsd.yzcx.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.fsd.yzcx.R;
import com.fsd.yzcx.ui.fragment.base.BaseFragment;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * CustomerserviceFragment
 * @author zxh
 * 客服中心相关的
 * 2016年1月18日16:03:27
 */
public class CustomerserviceFragment extends BaseFragment {

	@ViewInject(R.id.lv_telList)
	private ListView lv_tel;
	public View initView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mRootView = inflater.inflate(R.layout.fragment_customerservice, null);
		ViewUtils.inject(this,mRootView);
		return mRootView;
	}

	public void initData(Bundle bundle) {
		//请求网络获取客服中心的联系方式
		
		//初始化适配器
		
		
	}
	
}
