package com.fsd.yzcx.ui.fragment;

import com.fsd.yzcx.R;
import com.fsd.yzcx.tools.SystemTools;
import com.fsd.yzcx.ui.actvity.MainActivity;
import com.fsd.yzcx.ui.actvity.TempActivity;
import com.fsd.yzcx.ui.fragment.base.BaseFragment;
import com.fsd.yzcx.ui.view.UCListItem;
import com.fsd.yzcx.ui.view.UCListItem.MyOnClickListener;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 服务的fragment
 * @author zxh
 * 这是控制器
 */
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
		initCurrentView();//初始化当前的view

		return mRootView;
	}

	/**
	 * 初始化当前view并设置好数据
	 */
	private void initCurrentView() {
		ucli_suggestions=(UCListItem) mRootView.findViewById(R.id.ucli_suggestions);
		ucli_Property = (UCListItem) mRootView.findViewById(R.id.ucli_Property);
		ucli_paid=(UCListItem) mRootView.findViewById(R.id.ucli_paid);
		ucli_suggestions.setIVtitle(R.drawable.btn_check_off_disable_focused);	
		ucli_Property.setIVtitle(R.drawable.btn_check_off_disable);
		ucli_paid.setIVtitle(R.drawable.btn_check_off);
	}

	/**
	 * 初始化数据啊，绑定事件
	 */
	public void initData(Bundle bundle) {

		//分别给操作项添加点击事件
		ucli_suggestions.setOnClickListener(new MyOnClickListener() {
			public void onClick(View v) {
				SystemTools.showToastInfo(mActivity, "投诉", 3000, 1);
				//跳转到投诉fragment
				Intent intent = new Intent(mActivity,TempActivity.class);
				intent.putExtra("flag", 2);
				mActivity.startActivity(intent);
			}
		});
		ucli_Property.setOnClickListener(new MyOnClickListener() {
			public void onClick(View v) {
				SystemTools.showToastInfo(mActivity, "物业报修", 3000, 1);
				//跳转到报修fragment
				//跳转到投诉fragment
				Intent intent = new Intent(mActivity,TempActivity.class);
				intent.putExtra("flag", 3);
				mActivity.startActivity(intent);
				
			}
		});
		ucli_paid.setOnClickListener(new MyOnClickListener() {
			public void onClick(View v) {
				SystemTools.showToastInfo(mActivity, "有偿服务", 3000, 1);
				//跳转到有偿服务fragment
				//跳转到投诉fragment
				Intent intent = new Intent(mActivity,TempActivity.class);
				intent.putExtra("flag", 4);
				mActivity.startActivity(intent);
				
			}
		});
		
	}
	
}
