package com.fsd.yzcx.fragment;

import com.fsd.yzcx.R;
import com.fsd.yzcx.fragment.base.BaseFragment;
import com.fsd.yzcx.view.UCListItem;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FuWuFragment extends BaseFragment {


	//投诉建议
	private UCListItem ucli_suggestions;
	//物业报修
	private UCListItem ucli_Property;
	//有偿服务
	private UCListItem ucli_paid;


	public View initView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mRootView=inflater.inflate(R.layout.fragment_fuwu, null);
		ViewUtils.inject(this, mRootView);
		initCurrentView();
		
		return mRootView;
	}

	private void initCurrentView() {
		ucli_suggestions=(UCListItem) mRootView.findViewById(R.id.ucli_suggestions);
		ucli_Property = (UCListItem) mRootView.findViewById(R.id.ucli_Property);
		ucli_paid=(UCListItem) mRootView.findViewById(R.id.ucli_paid);
		ucli_suggestions.setIVtitle(R.drawable.btn_check_off_disable_focused);	
		ucli_Property.setIVtitle(R.drawable.btn_check_off_disable);
		ucli_paid.setIVtitle(R.drawable.btn_check_off);
	}

	public void initData(Bundle bundle) {

	}

}
