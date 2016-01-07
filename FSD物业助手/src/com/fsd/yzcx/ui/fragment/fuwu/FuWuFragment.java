package com.fsd.yzcx.ui.fragment.fuwu;

import com.fsd.yzcx.R;
import com.fsd.yzcx.dao.fuwu.FuwuDao;
import com.fsd.yzcx.dao.fuwu.FuwuDao.MyFWdaoListener;
import com.fsd.yzcx.tools.LogUtil;
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
		setSubItemEvent(ucli_suggestions,2);
		
		setSubItemEvent(ucli_Property,3);
		
		setSubItemEvent(ucli_paid,4);
		
	}

	
	
	private void setSubItemEvent(UCListItem ucListItem, final int flag) {
		ucListItem.setOnClickListener(new MyOnClickListener() {
			public void onClick(View v) {
				//跳转到投诉fragment
				
				//请求网络获取子服务项信息
				FuwuDao fuwuDao = new FuwuDao(mActivity);
				
				//获取数据的接口
				fuwuDao.fetchSubService(mActivity.getResources().getStringArray(R.array.pid)[flag-2],
						
						"subservice", new MyFWdaoListener() {
							public void fetchSubService(String response) {
								LogUtil.i("test", response);
								Intent intent = new Intent(mActivity,TempActivity.class);
								intent.putExtra("flag", flag);
								intent.putExtra("config_info", response);//返回的2级服务项的数据
								mActivity.startActivity(intent);
							}
						});
				
			}
		});
	}
	
}
