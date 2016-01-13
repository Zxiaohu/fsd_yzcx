package com.fsd.yzcx.ui.fragment.fuwu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import com.fsd.yzcx.R;
import com.fsd.yzcx.dao.db.SharedPfDao;
import com.fsd.yzcx.dao.fuwu.FuwuDao;
import com.fsd.yzcx.dao.fuwu.FuwuDao.MyFWdaoListener;
import com.fsd.yzcx.dao.user.UserParamsName;
import com.fsd.yzcx.tools.LogUtil;
import com.fsd.yzcx.tools.SystemTools;
import com.fsd.yzcx.ui.fragment.base.BaseFragment;

public class SuggestionsFragment extends BaseFragment {

	
	private Spinner sp_fuwu_item;//投诉
	private TextView tv_photo;//选择照片或拍照
	private ImageView iv_content;//选择照片或拍照
	private EditText et_content;//建议的内容
	private Button btn_submit;//提交的按钮
	
	public View initView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mRootView = inflater.inflate(R.layout.fragment_suggestion, null);
		//初始化当前界面
		initCurrentView();	
		
		return mRootView;
	}

	/**
	 * 初始化当前view
	 */
	private void initCurrentView() {

		//获取子服务项信息
		getSubServices();
		sp_fuwu_item = (Spinner) mRootView.findViewById(R.id.sp_fuwu_item);
		tv_photo =(TextView) mRootView.findViewById(R.id.tv_photo);
		iv_content =(ImageView) mRootView.findViewById(R.id.iv_content);
		et_content =(EditText) mRootView.findViewById(R.id.et_fuwu_content);
		btn_submit=(Button) mRootView.findViewById(R.id.button);
		//初始化适配器
		myConfigInfoAdapter = initMyadapter();
		//设置数据适配器
		if(myConfigInfoAdapter!=null){
			sp_fuwu_item.setAdapter(myConfigInfoAdapter);
		}
		
	}

	

	public void initData(Bundle bundle) {
		//设置事件
		//图片选择事件
		initSelectPhoto();
		//提交的事件
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
		String houseid=SharedPfDao.queryStr(UserParamsName.HOUSE_ID.getName());
		String Complainer=SharedPfDao.queryStr(UserParamsName.NICKNAME.getName());
		String TelNumber=SharedPfDao.queryStr(UserParamsName.UNAME.getName());
		String Content=et_content.getText().toString().trim();//投诉的内容
		String ComplainTypeID="service03";//主项
		
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

	private void initSelectPhoto() {
	
	}

}
