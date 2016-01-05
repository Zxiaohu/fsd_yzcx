package com.fsd.yzcx.ui.pager.base;

import android.app.Activity;
import android.view.View;

/**
 * 页面
 * @author zxh
 *
 */
public abstract class BasePager {
	public View mRootView;//布局对象
	public Activity mActivity;//上下文对象
	
	public BasePager(Activity activity){
		mActivity=activity;

		mRootView=initView();
	}
	/**
	 * //初始化view
	 * @return
	 */
	abstract public View initView();
	/**
	 * //填充数据
	 */
	abstract public void initData();
}
