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
/**
 * 有偿服务
 * @author zxh
 *
 */
public class PaidFragment extends BaseFragment {

	private Spinner sp_fuwu_item;
	@ViewInject(R.id.et_fuwu_content)
	private EditText et_content;//建议的内容
	@ViewInject(R.id.button)
	private Button btn_submit;//提交的按钮
	
	private Spinner sp_room;//房间
	public View initView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mRootView=inflater.inflate(R.layout.fragment_paid, null);
		ViewUtils.inject(this,mRootView);
		
	
		initCurrentView();
		
		return mRootView;

	}

	private void initCurrentView() {
		
		sp_fuwu_item =(Spinner) mRootView.findViewById(R.id.sp_fuwu_item).findViewById(R.id.sp_item);
		sp_room =(Spinner) mRootView.findViewById(R.id.sp_room_item).findViewById(R.id.sp_item);
		
		//获取子服务项信息
		getSubServices();
		myConfigInfoAdapter = initMyadapter();
		
		if(myConfigInfoAdapter!=null){//设置适配器
			sp_fuwu_item.setAdapter(myConfigInfoAdapter);
		}
		//设置选择地址的适配器
		BaseAdapter roomAdapter=RoomSelectHelper.getCommonAdapter(mActivity, SharedPfDao.queryStr(Param.HOUSE_NAME.getName()));
		if(roomAdapter!=null){
			sp_room.setAdapter(roomAdapter);
		}
	}

	public void initData(Bundle bundle) {
		setSubmit();
	}
	/**
	 * 提交事件
	 */
	private void setSubmit() {
		btn_submit.setOnClickListener(new android.view.View.OnClickListener() {
			public void onClick(View v) {
				send2server();
			}
		});
		
	}

	/**
	 * 向服务器提交
	 */
	private void send2server() {
		
		//获取服务项的值
		String houseid=getHouseId();
		
		String Complainer=SharedPfDao.queryStr(Param.NICKNAME.getName());
		String TelNumber=SharedPfDao.queryStr(Param.UNAME.getName());
		String Content=et_content.getText().toString().trim();//投诉的内容
		String ComplainTypeID="service02";//主项
		
		if(configInfos!=null&&houseid!=null&&Complainer!=null&&TelNumber!=null){
			String ComplainSubID=configInfos.get(sp_fuwu_item.getSelectedItemPosition()).Configid;//子项的id
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
	
	/**
	 * 获取房间的id
	 * @return
	 */
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
