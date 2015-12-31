package com.fsd.yzcx.fragment.base;
import com.fsd.yzcx.tools.LogUtil;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseFragment extends Fragment {

	public View mRootView;
	public FragmentActivity mActivity;
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mRootView=initView(inflater,container,savedInstanceState);
		mActivity=getActivity();
		return mRootView;
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		LogUtil.i(this.getClass().getSimpleName(), "我被执行了");
		
		initData(savedInstanceState);
	}

	abstract public View initView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState);
	abstract public void initData(Bundle bundle);

}
