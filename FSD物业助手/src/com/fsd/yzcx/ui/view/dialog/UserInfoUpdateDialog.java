package com.fsd.yzcx.ui.view.dialog;

import com.fsd.yzcx.R;
import com.fsd.yzcx.dao.user.UserDao;
import com.fsd.yzcx.dao.user.UserDao.UpdateUserListener;
import com.fsd.yzcx.tools.LogUtil;
import com.fsd.yzcx.tools.SystemTools;
import com.fsd.yzcx.ui.actvity.base.MyApplication;
import com.google.gson.Gson;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

public class UserInfoUpdateDialog extends DialogFragment {


	private String tvcontent;//传过来的内容
	private String tag;//传过来的标记
	private EditText et_content;
	private String uname;
	private MyListener myListener;//我的回调接口

	public void setMyListener(MyListener listener){
		myListener = listener;
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle arguments = getArguments();
		tvcontent =arguments.getString("content");//内容
		tag=arguments.getString("tag");//标记
		uname = arguments.getString("uname");//手机号
	}

	public class Flag{//服务器返回的提示信息
		int flag;
		String info;
	}

	public Dialog onCreateDialog(Bundle savedInstanceState) {

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		View view = View.inflate(MyApplication.getContext(), R.layout.dialog_userinfo_update, null);
		et_content = (EditText) view.findViewById(R.id.et_content);

		et_content.setText(tvcontent);

		builder.setView(view).setNegativeButton("确认", new OnClickListener() {
			private String content;

			public void onClick(DialogInterface dialog, int which) {

				content = et_content.getText().toString().trim();//获取内容

				//判断content是否为空！

				if(!TextUtils.isEmpty(content)){

					//LogUtil.d("test",content1+"-"+content2);
					UserDao userDao = new UserDao(getActivity());

					//更新用户信息
					userDao.updtaeUserInfo(uname,tag,content,new UpdateUserListener() {
						public void updateUserInfo(String info) {
							Flag flag = new Gson().fromJson(info, Flag.class);
							if(flag.flag==0){
								SystemTools.showToastInfo(MyApplication.getContext(), flag.info, 3000, 2);
							}else{
								SystemTools.showToastInfo(MyApplication.getContext(), flag.info, 3000, 1);
								myListener.setMyEvent(content);
							}
						}
					});

					}else{
						SystemTools.showToastInfo(getActivity(), "请输入完整信息", 3000, 2);
					}
				}
			}).setPositiveButton("取消", null);
		
		return builder.create();
	}
	/**
	 * 点击后的回调接口
	 */
	public interface MyListener{
		void setMyEvent(String content);
	}

}
