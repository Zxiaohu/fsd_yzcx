package com.fsd.yzcx.ui.fragment.user;

import java.util.Set;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.fsd.yzcx.R;
import com.fsd.yzcx.dao.db.SharedPfDao;
import com.fsd.yzcx.tools.DataTools;
import com.fsd.yzcx.tools.SystemTools;
import com.fsd.yzcx.ui.fragment.base.BaseFragment;
import com.fsd.yzcx.ui.view.UCListItem;
import com.fsd.yzcx.ui.view.UCListItem.MyOnClickListener;
import com.fsd.yzcx.ui.view.dialog.UserInfoUpdateDialog;
import com.fsd.yzcx.ui.view.dialog.UserInfoUpdateDialog.MyListener;
import com.google.gson.Gson;

public class UserInfoFragment extends BaseFragment {


	private UCListItem uclt_photo;//相册
	private UCListItem uclt_nickname;//昵称
	private UCListItem uclt_score;//积分
	private UCListItem uclt_address;//地址
	private UCListItem uclt_update_pwd;//修改密码
	private UCListItem uclt_room;//地址
	private UserInfo userInfo;//用户信息
	private FragmentManager supportFragmentManager;

	class UserInfo{//用户信息
		public String flag;//标记
		public String info;//提示信息
		public String nickname;
		public String photo;//相片
		public String address;//地址
		public String housename;//房间
		public String houseid;//房号
		public int score;//积分
	}

	public View initView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mRootView = inflater.inflate(R.layout.fragment_userinfo, null);


		String userinfo=getArguments().getString("userinfo");
		//解析对象
		this.userInfo=new Gson().fromJson(userinfo, UserInfo.class);


		//初始化当前的view
		initCurrentView();

		return mRootView;
	}


	private void initCurrentView() {

		uclt_photo =(UCListItem) mRootView.findViewById(R.id.ucli_photo);
		uclt_nickname=(UCListItem) mRootView.findViewById(R.id.ucli_nickname);
		uclt_score=(UCListItem) mRootView.findViewById(R.id.ucli_score);
		uclt_address=(UCListItem) mRootView.findViewById(R.id.ucli_address);
		uclt_room=(UCListItem) mRootView.findViewById(R.id.ucli_room);
		uclt_update_pwd=(UCListItem) mRootView.findViewById(R.id.ucli_update_pwd);

		//判断用户的值并设置
		String nickname="请设置信息";
		//String score="没有积分";
		String address="请设置信息";


		if(!userInfo.address.equals("")){//设置地址信息
			address=userInfo.address;
		}


		if(!userInfo.nickname.equals("")){
			nickname=userInfo.nickname;
		}

		if(userInfo.photo.equals("")){//设置头像信息
			uclt_photo.setIVtitle(R.drawable.user_96px);
			uclt_photo.setTvContent("请设置头像");
		}

		uclt_room.setTvContent(userInfo.housename);
		uclt_nickname.setTvContent(nickname);
		uclt_score.setTvContent(userInfo.score+"分");
		uclt_address.setTvContent(address);

	}



	//初始化操作的方法
	public void initData(Bundle bundle) {

		//获取fragmentmanager
		supportFragmentManager = mActivity.getSupportFragmentManager();
		
		//统一将用户的信息填充到 本地文件
		addUserInfo2pf();

		final String uname=SharedPfDao.queryStr("uname");//获取用户的信息

		//判断手机号不等于null再继续执行
		if(uname!=null){
			final UserInfoUpdateDialog dialog = new UserInfoUpdateDialog();
			//设置修改地址的方法
			setUcLiEvent(uclt_address, uname,"address", dialog);
			//设置用户昵称的方法
			setUcLiEvent(uclt_nickname, uname,"nickname", dialog);
			//设置修改密码的方法
		}
	}

	/***
	 * 将用户的信息存到本地文件中
	 * 
	 * 房间个能一个用户拥有多间房号,
	 * 0005|5|2|1001@
	 * 0005|6|1|2801@
	 * 0005|6|1|2802@
	 * 0005|6|1|2803, 
	 * "housename" :怡江苑(H5)-E栋-2单元-1001|
	 * 	                    怡江苑(H5)-F栋-1单元-2801|
	 *              怡江苑(H5)-F栋-1单元-2802|
	 *              怡江苑(H5)-F栋-1单元-2803"
	 */
	private void addUserInfo2pf() {
		
		String strHouseId=userInfo.houseid;
		String [] houseids;//房号数组
		Set<String> houseidSet;//房间号的set
		//检测该用户是否有多个房间
		if(DataTools.isHaveIn("@", strHouseId)){
			houseids = strHouseId.split("@");
			//将所有的房间转为set集合
			houseidSet = DataTools.makeStrArr2Set(houseids);
		}else{
			houseids = new String[1];
		}
	}

	/**
	 * 添加条目事件的公共方法
	 */
	private void setUcLiEvent(final UCListItem ucListItem,final String uname,final String tag,final UserInfoUpdateDialog dialog){
		//添加条目事件
		ucListItem.setOnClickListener(new MyOnClickListener() {
			public void onClick(View v) {
				if(uname!=null){//弹出对话框修改信息
					Bundle bundle = new Bundle();
					bundle.putString("content", ucListItem.getTvContent());
					bundle.putString("tag", tag);//修改的标记
					bundle.putString("uname",uname);//手机号

					dialog.setArguments(bundle);
					dialog.show(supportFragmentManager, "update");
					dialog.setMyListener(new MyListener() {//对话框的2次回调的方法
						public void setMyEvent(String content) {			

							ucListItem.setTvContent(content);//设置这里面的值
							if(ucListItem.equals(uclt_nickname)){
								//修改原文件中的值
								SharedPfDao.insertData("nickname",content);	
							}else if(ucListItem.equals(uclt_address)){
								//修改原文件中的值
								SharedPfDao.insertData("address",content);
							}
						}
					});
				}else{
					SystemTools.showToastInfo(mActivity, "您没有登录", 3000, 2);
				}


			}
		});
	}
}
