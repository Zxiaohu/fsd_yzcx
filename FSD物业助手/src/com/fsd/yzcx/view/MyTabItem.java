package com.fsd.yzcx.view;

import com.fsd.yzcx.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * MyTabItem
 * @author zxh
 * 主界面tab底部的按钮样式view
 */
public class MyTabItem extends LinearLayout {

	private View mRootView;
	
	@ViewInject(R.id.tv_title)
	private TextView tv_title;//tab下小标题
	
	@ViewInject(R.id.iv_content)
	private ImageView iv_content;//tab下的小图标
	
	int mNormalIconId;
	int mFocusIconId;
	
	public MyTabItem(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initView();
	}

	public MyTabItem(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		initView();
	}

	public MyTabItem(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		initView();
	}
	
	/**
	 * 初始化布局
	 */
	private void initView(){
		mRootView = View.inflate(getContext(), R.layout.indicator_mian, this);
		ViewUtils.inject(this,mRootView);
	}
	
	
	/***
	 * 设置小标题
	 * @param content
	 */
	public void setTitle(String content){
		tv_title.setText(content);
	}
	
	 
	 /**
	  * 设置默认小图标
	  * @param normalIconId
	  * @param focusIconId
	  */
	public void setImage(int normalIconId,int focusIconId){
		mNormalIconId=normalIconId;
		mFocusIconId=focusIconId;
		iv_content.setImageResource(normalIconId);
	}

	/**
	 * 设置是否选中
	 * @param isSelected
	 */
	public void setSelect(boolean isSelected){
		if(isSelected){
			iv_content.setImageResource(mFocusIconId);
		}else{
			iv_content.setImageResource(mNormalIconId);
		}
	}
	
}
