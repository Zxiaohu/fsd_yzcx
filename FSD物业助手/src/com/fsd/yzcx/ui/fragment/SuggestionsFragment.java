package com.fsd.yzcx.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fsd.yzcx.R;
import com.fsd.yzcx.ui.fragment.base.BaseFragment;

public class SuggestionsFragment extends BaseFragment {

	
	public View initView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mRootView = inflater.inflate(R.layout.fragment_suggestion, null);
		return mRootView;
	}
	public void initData(Bundle bundle) {
		
	}

}
