package com.fsd.yzcx.ui.fragment.fuwu;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.fsd.yzcx.R;
import com.fsd.yzcx.dao.db.SharedPfDao;
import com.fsd.yzcx.dao.fuwu.FuwuDao;
import com.fsd.yzcx.dao.fuwu.FuwuDao.MyFWdaoListener;
import com.fsd.yzcx.dao.user.Param;
import com.fsd.yzcx.tools.JsonTools;
import com.fsd.yzcx.tools.LogUtil;
import com.fsd.yzcx.tools.SystemTools;
import com.fsd.yzcx.ui.fragment.base.BaseFragment;
import com.fsd.yzcx.ui.helper.RoomSelectHelper;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class PropertyFragment extends BaseFragment {


	//@ViewInject(R.id.sp_fuwu_item)
	private Spinner sp_fuwu_item;//服务项
	@ViewInject(R.id.et_fuwu_content)
	private EditText et_content;//建议的内容

	@ViewInject(R.id.tv_address)
	private TextView tv_address;//确认地址

	private Spinner sp_room;//地址选择器

	@ViewInject(R.id.button)
	private Button btn_submit;//提交的按钮


	public View initView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		mRootView = inflater.inflate(R.layout.fragment_property, null);	
		ViewUtils.inject(this, mRootView);

		initCurrentView();

		return mRootView;

	}

	private void initCurrentView() {

		sp_fuwu_item =(Spinner) mRootView.findViewById(R.id.sp_fuwu_item).findViewById(R.id.sp_item);
		sp_room=(Spinner) mRootView.findViewById(R.id.sp_room_item).findViewById(R.id.sp_item);


		//获取子服务项信息
		getSubServices();
		myConfigInfoAdapter = initMyadapter();

		//服务项的数据适配器
		if(myConfigInfoAdapter!=null){//设置适配器
			sp_fuwu_item.setAdapter(myConfigInfoAdapter);
		}

		//选择地址的适配器
		/**
		 * 首先要判断是不是多用户,
		 * 然后进行分开的解析我在此要抽取这个操作相关的方法
		 */
		BaseAdapter roomAdapter = RoomSelectHelper.getCommonAdapter(mActivity, SharedPfDao.queryStr(Param.HOUSE_NAME.getName()));
		if(roomAdapter!=null){
			sp_room.setAdapter(roomAdapter);
		}
	}

	public void initData(Bundle bundle) {
		
		//提交的事件
		setSubmit();
		
	}
	/**
	 * 提交事件
	 */
	private void setSubmit() {
		btn_submit.setOnClickListener(new android.view.View.OnClickListener() {
			public void onClick(View v) {
				//提交数据
				send2server();
			}
		});

	}

	/**
	 * 向服务器提交
	 */
	private void send2server() {
		
		
		String houseid = getHouseId();
		
		LogUtil.e("test1", houseid);	
		//昵称
		String Complainer = SharedPfDao.queryStr(Param.NICKNAME.getName());
		
		String TelNumber = SharedPfDao.queryStr(Param.UNAME.getName());
		String Content = et_content.getText().toString().trim();//投诉的内容
		String ComplainTypeID = "service01";//主项

		if(configInfos != null && houseid != null && Complainer != null && TelNumber != null){
			String ComplainSubID = configInfos.get(sp_fuwu_item.getSelectedItemPosition()).Configid;//子项的id
			//服务相关的逻辑处理
			LogUtil.e("test", houseid+"-pppp-"+Complainer+"-pppp-"+TelNumber+"-pppp-"+Content+"-pppp-"+ComplainTypeID+"-pppp-"+ComplainSubID);

			FuwuDao dao = new FuwuDao(mActivity);
			dao.pullPaiGong(houseid, Complainer, TelNumber, Content, ComplainTypeID, ComplainSubID,new MyFWdaoListener() {	
				public void fetchInfo(String response) {
					SystemTools.showToastInfo(mActivity, response, 3000, 1);
				}
			});
		}else{
			SystemTools.showToastInfo(mActivity,"数据有误,提交失败", 3000, 2);
		}
	}

	private String getHouseId() {
		//获取服务项的值
		String houseid="";
		String houseid_temp=SharedPfDao.queryStr(Param.HOUSE_ID.getName());
		if(JsonTools.isJson(houseid_temp)){//看id
			//是多房间的话
			houseid = RoomSelectHelper.getRoomInfo(houseid_temp,
					sp_room.getSelectedItemPosition(),
					"jsonHouseid","houseid");
			LogUtil.w("test1", houseid);
		}else{
			houseid = houseid_temp;//房间的id
		}
		return houseid;
	}
}
