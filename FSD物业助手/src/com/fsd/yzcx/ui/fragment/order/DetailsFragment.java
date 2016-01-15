package com.fsd.yzcx.ui.fragment.order;
import java.util.ArrayList;
import java.util.List;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.fsd.yzcx.R;
import com.fsd.yzcx.dao.db.SharedPfDao;
import com.fsd.yzcx.dao.order.OrderDao;
import com.fsd.yzcx.dao.order.OrderDao.FetchDetailsListener;
import com.fsd.yzcx.dao.user.Param;
import com.fsd.yzcx.tools.LogUtil;
import com.fsd.yzcx.ui.adapter.CommonAdapter;
import com.fsd.yzcx.ui.adapter.ViewHolder;
import com.fsd.yzcx.ui.fragment.base.BaseFragment;
import com.fsd.yzcx.ui.view.MyListView;
import com.fsd.yzcx.ui.view.MyListView.OnRefreshListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class DetailsFragment extends BaseFragment {

	//	@ViewInject(R.id.lv_details)
	//	private ListView lv_details;
	@ViewInject(R.id.lv)
	private MyListView lv;

	private CommonAdapter<Details> detailsAdapter;
	private List<Details> list;
	public View initView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mRootView = inflater.inflate(R.layout.fragment_details, null);
		String details = getArguments().getString("data");

		//解析数据
		list=new Gson().fromJson(details, new TypeToken<List<Details>>(){}.getType());


		ViewUtils.inject(this, mRootView);
		return mRootView;
	}

	class Details{
		String id;//id
		String pressstate;//状态
		String content;//内容
		private String ComplainDate;//时间

	}
	public void initData(Bundle bundle) {


		//适配器
		detailsAdapter = new CommonAdapter<Details>(mActivity,list,R.layout.details_list) {
			public void convert(ViewHolder helper, Details item) {
				TextView tv1=helper.getView(R.id.tv_type);
				TextView tv2=helper.getView(R.id.tv_content);
				TextView tv3=helper.getView(R.id.tv_date);			
				tv1.setText(item.pressstate);
				tv2.setText(item.content);
				tv3.setText(item.ComplainDate);
			}
		};

		if(detailsAdapter!=null){
			lv.setAdapter(detailsAdapter);
		}

		lv.setonRefreshListener(mylistener);
	}

	OnRefreshListener mylistener=new OnRefreshListener() {
		public void onRefresh() {
			new AsyncTask<Void, Void, Void>() {  
				protected Void doInBackground(Void... params) {  
					try {  
						Thread.sleep(3000);  
					} catch (Exception e) {  
						e.printStackTrace();  
					}  
					//list.add(new Details("新","新","新"));

					return null;  
				}  

				protected void onPostExecute(Void result) { 

					detailsAdapter.notifyDataSetChanged();  
					lv.onRefreshComplete(); 




				}  
			}.execute(null, null, null);  
		}
	};

}
