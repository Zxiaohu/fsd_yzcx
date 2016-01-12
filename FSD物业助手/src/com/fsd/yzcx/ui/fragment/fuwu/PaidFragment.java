package com.fsd.yzcx.ui.fragment.fuwu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.fsd.yzcx.R;
import com.fsd.yzcx.ui.fragment.base.BaseFragment;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class PaidFragment extends BaseFragment {

	@ViewInject(R.id.sp_fuwu_item)
	private Spinner sp_fuwu_item;
	
	
	public View initView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mRootView=inflater.inflate(R.layout.fragment_paid, null);
		ViewUtils.inject(this,mRootView);
		
	
		initCurrentView();
		
		return mRootView;

	}

	private void initCurrentView() {
		//获取子服务项信息
			getSubServices();
		myConfigInfoAdapter = initMyadapter();
		
		if(myConfigInfoAdapter!=null){//设置适配器
			sp_fuwu_item.setAdapter(myConfigInfoAdapter);
		}
		
	}

	public void initData(Bundle bundle) {
	
	}

}
