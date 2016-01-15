package com.fsd.yzcx.ui.fragment.base;
import java.util.List;

import com.fsd.yzcx.R;
import com.fsd.yzcx.tools.LogUtil;
import com.fsd.yzcx.ui.adapter.CommonAdapter;
import com.fsd.yzcx.ui.adapter.ViewHolder;
import com.fsd.yzcx.ui.fragment.fuwu.SuggestionsFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public abstract class BaseFragment extends Fragment {

	public View mRootView;
	public FragmentActivity mActivity;
	
	public FragmentManager frgManager;
	protected CommonAdapter<ConfigInfo> myConfigInfoAdapter;//万能的适配器
	public List<ConfigInfo> configInfos;//返回的服务项的数据
	
	public class ConfigInfo{//服务项信息
		public String Configid;//服务项id
		public String Configvalue;//服务项值
	}
	
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mRootView=initView(inflater,container,savedInstanceState);
		
		return mRootView;
	}

	
	
	public void onAttach(Activity activity) {
		mActivity=getActivity();
		frgManager=getFragmentManager();
		super.onAttach(activity);
	}
	public void onDetach() {
		super.onDetach();
		if(mActivity!=null){
			mActivity=null;
			}
	}
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		LogUtil.i(this.getClass().getSimpleName(), "我被执行了");

		initData(savedInstanceState);
	}

	
	
	
	
	/**
	 * 获取子服务项信息
	 */
	protected void getSubServices() {
		String config_info = this.getArguments().getString("data");//获取服务配置信息
		
		LogUtil.v("test_argment", config_info);
		
		if(config_info!=null){
			configInfos = new Gson().fromJson(config_info,new TypeToken<List<ConfigInfo>>(){}.getType());
		}
	}
	
	
	
	
	/**
	 * 初始化适配器的方法
	 * @return
	 */
	protected CommonAdapter<ConfigInfo> initMyadapter() {
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
	abstract public View initView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState);
	abstract public void initData(Bundle bundle);

}
