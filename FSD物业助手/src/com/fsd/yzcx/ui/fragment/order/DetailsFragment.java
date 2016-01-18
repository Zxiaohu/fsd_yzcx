package com.fsd.yzcx.ui.fragment.order;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fsd.yzcx.R;
import com.fsd.yzcx.ui.fragment.base.BaseFragment;
/**
 * DetailsFragment
 * @author zxh
 * 服务订单详情页
 * 2016年1月18日16:06:20
 */
public class DetailsFragment extends BaseFragment {

	@Override
	public View initView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mRootView = inflater.inflate(R.layout.fragment_details, null);
		return mRootView;
	}
	public void initData(Bundle bundle) {
		
	}

}
