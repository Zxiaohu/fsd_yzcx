package com.fsd.yzcx.ui.fragment.user;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.fsd.yzcx.R;
import com.fsd.yzcx.dao.db.SharedPfDao;
import com.fsd.yzcx.dao.user.UserDao;
import com.fsd.yzcx.dao.user.UserDao.UpdateUserListener;
import com.fsd.yzcx.dao.user.Param;
import com.fsd.yzcx.tools.SystemTools;
import com.fsd.yzcx.ui.fragment.base.BaseFragment;
import com.fsd.yzcx.ui.view.dialog.TipDialog;
import com.fsd.yzcx.ui.view.dialog.TipDialog.callBackOk;
import com.google.gson.Gson;

public class AlertPwdFragment extends BaseFragment {
	private TextView tv_old_tip;
	private TextView tv_new_tip;
	private TextView tv_new_tip1;
	private EditText et_old_pwd;
	private EditText et_new_pwd;
	private EditText et_new_pwd1;

	private Button btn_save;
	public View initView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mRootView = inflater.inflate(R.layout.update_pwd, null);

		//初始化控件信息
		tv_old_tip =initCurrentTvView("原密码:",R.id.old_pwd);
		tv_new_tip=initCurrentTvView("新密码:",R.id.new_pwd);
		tv_new_tip1=initCurrentTvView("确认密码:",R.id.new_pwd1);

		et_old_pwd=initCurrentEtView(R.id.old_pwd);
		et_new_pwd=initCurrentEtView(R.id.new_pwd);
		et_new_pwd1=initCurrentEtView(R.id.new_pwd1);

		btn_save = (Button) mRootView.findViewById(R.id.btn_save);

		return mRootView;
	}

	class Info{//服务器返回的结果信息
		public int flag;
		public String info;
	}
	/**
	 * 
	 * @param tips
	 * @param id include的id
	 * @return
	 */
	private TextView initCurrentTvView(String tips,int id) {
		TextView tv=(TextView) mRootView.findViewById(id).findViewById(R.id.tv_tip);
		tv.setText(tips);
		return tv;
	}
	private EditText initCurrentEtView(int id) {
		EditText et=(EditText) mRootView.findViewById(id).findViewById(R.id.et_old_pwd);
		return et;
	}


	/**
	 *初始化事件的信息
	 */
	public void initData(Bundle bundle) {

		btn_save.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				//判断用户输入密码 的完整性
				final String oldpwd = et_old_pwd.getText().toString().trim();
				String newpwd = et_new_pwd.getText().toString().trim();
				final String newpwd1 = et_new_pwd1.getText().toString().trim();

				if(TextUtils.isEmpty(oldpwd)||TextUtils.isEmpty(newpwd)||TextUtils.isEmpty(newpwd1)){
					SystemTools.showToastInfo(mActivity, "请填写完整的信息", 3000, 2);
				}else{
					if(newpwd.equals(newpwd1)){
						//弹对话框
						final TipDialog dialog = new TipDialog();
						dialog.setCallBackOK(new callBackOk() {
							public void onOkClick() {
								//服务器修改
								UserDao userDao = new UserDao(mActivity);
								userDao.alterPwd(SharedPfDao.queryStr(Param.UNAME.getName()), 
										oldpwd, 
										newpwd1, new UpdateUserListener() {
									public void updateUserInfo(String info) {
										Info info1= new Gson().fromJson(info, Info.class);
										if(info1.flag==1){
											SystemTools.showToastInfo(mActivity, info1.info, 3000, 1);
											
											//清空之前所有的数据
											SharedPfDao.delAll();
											
											dialog.dismiss();
											mActivity.finish();
										}else{
											SystemTools.showToastInfo(mActivity, info1.info, 3000, 2);
											dialog.dismiss();
										}
									}
								});
							}
						}, "您确定要提交修改吗？提交后成功后您将需要重新登录");
						dialog.show(frgManager, "alertpwd_dialog");
					}else{
						SystemTools.showToastInfo(mActivity, "两次密码填写不一致", 3000, 2);
					}
				}


			}
		});

	}

}
