package com.fsd.yzcx.ui.fragment.order;

import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.fsd.yzcx.R;
import com.fsd.yzcx.dao.order.FetchListener;
import com.fsd.yzcx.dao.order.OrderDao;
import com.fsd.yzcx.tools.LogUtil;
import com.fsd.yzcx.tools.SystemTools;
import com.fsd.yzcx.ui.adapter.CommonAdapter;
import com.fsd.yzcx.ui.adapter.ViewHolder;
import com.fsd.yzcx.ui.fragment.base.BaseFragment;
import com.fsd.yzcx.ui.view.MyListView;
import com.fsd.yzcx.ui.view.MyListView.OnRefreshListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * DetailsStateFragment
 * @author zxh
 * 服务订单状态fragment
 * 2016年1月18日16:04:28
 */
public class DetailsStateFragment extends BaseFragment {

	private String data; //从上个页面传来的数据
	private List<RateInfo> lists; //进度信息

	@ViewInject(R.id.lv)
	private MyListView listView;//列表控件
	public View initView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		data = getArguments().getString("data");
		mRootView = inflater.inflate(R.layout.fragment_details_state, null);
		ViewUtils.inject(this, mRootView);
		return mRootView;
	}

	class RateInfo{
		public String id;
		public String complainid;
		public String remark;//进度描述
		public String logindate;//登入时间
	}

	public void initData(Bundle bundle) {
		//请求网络获取订单的进度
		OrderDao dao = new OrderDao(mActivity);
		dao.fetchRate(data, new FetchListener() {
			public void getDetails(String response) {

				//解析订单的进度信息
				lists = new Gson().fromJson(response,new TypeToken<List<RateInfo>>(){}.getType());

				//添加适配器
				CommonAdapter<RateInfo> rateAdapter = new CommonAdapter<RateInfo>(mActivity,lists,R.layout.adapter_rates) {
					public void convert(ViewHolder helper, RateInfo item) {
						TextView tv_remark = helper.getView(R.id.tv_remark);
						TextView tv_time = helper.getView(R.id.tv_time);
						tv_remark.setText(item.remark);
						tv_time.setText(item.logindate);
					}
				}; 

				if(rateAdapter != null){
					listView.setAdapter(rateAdapter);
				}	
				listView.setonRefreshListener(MyRefreshListener);

			}


		});


	}

	OnRefreshListener MyRefreshListener = new OnRefreshListener() {
		public void onRefresh() {
			SystemTools.showToastInfo(mActivity, "刷刷刷", 3000, 1);
			listView.onRefreshComplete();
		}
	};
}
