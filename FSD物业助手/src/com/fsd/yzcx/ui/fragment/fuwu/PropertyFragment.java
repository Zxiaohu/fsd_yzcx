package com.fsd.yzcx.ui.fragment.fuwu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fsd.yzcx.R;
import com.fsd.yzcx.ui.fragment.base.BaseFragment;

public class PropertyFragment extends BaseFragment {

	@Override
	public View initView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		mRootView = inflater.inflate(R.layout.fragment_property, null);
		return mRootView;

	}

	@Override
	public void initData(Bundle bundle) {
		// TODO Auto-generated method stub

	}

}
