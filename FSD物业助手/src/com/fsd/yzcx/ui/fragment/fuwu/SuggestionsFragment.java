package com.fsd.yzcx.ui.fragment.fuwu;

import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import com.fsd.yzcx.R;
import com.fsd.yzcx.tools.LogUtil;
import com.fsd.yzcx.ui.adapter.CommonAdapter;
import com.fsd.yzcx.ui.adapter.ViewHolder;
import com.fsd.yzcx.ui.fragment.base.BaseFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.ViewUtils;

public class SuggestionsFragment extends BaseFragment {

	private List<ConfigInfo> configInfos;//返回的结果
	private Spinner sp_fuwu_item;//投诉

	public class ConfigInfo{
		public String Configid;
		public String Configvalue;
	}
	
	public View initView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mRootView = inflater.inflate(R.layout.fragment_suggestion, null);
		//获取子服务项信息
		String config_info = getArguments().getString("config_info");
		if(config_info!=null){
			configInfos = new Gson().fromJson(config_info,new TypeToken<List<ConfigInfo>>(){}.getType());
		}

		//初始化当前界面
		initCurrentView();

		return mRootView;
	}

	


	/**
	 * 初始化当前view
	 */
	private void initCurrentView() {
		
		sp_fuwu_item = (Spinner) mRootView.findViewById(R.id.sp_fuwu_item);
		
		//初始化适配器
		CommonAdapter<ConfigInfo> myAdapter = initMyadapter();
		
		//设置数据适配器
		if(myAdapter!=null){
			sp_fuwu_item.setAdapter(myAdapter);
		}
	}



	/**
	 * 初始化我自己适配器
	 * @return
	 */
	private CommonAdapter<ConfigInfo> initMyadapter() {
		/**
		 * 适配器
		 */
		CommonAdapter<ConfigInfo> myAdapter= new CommonAdapter<SuggestionsFragment.ConfigInfo>(mActivity,configInfos,R.layout.adapter_simple) {
			public void convert(ViewHolder helper, ConfigInfo item) {
				TextView tv_title=helper.getView(R.id.tv_fuwu_title);
				tv_title.setText(item.Configvalue);
			}
		};
		return myAdapter;
	}
	

	public void initData(Bundle bundle) {
		
	}

}
