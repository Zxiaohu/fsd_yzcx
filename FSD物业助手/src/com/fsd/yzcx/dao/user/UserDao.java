package com.fsd.yzcx.dao.user;

import java.net.InterfaceAddress;

import com.fsd.yzcx.tools.JsonTools;
import com.fsd.yzcx.tools.http.HttpTools;
import com.fsd.yzcx.tools.http.MyHttpListener;
import com.fsd.yzcx.ui.actvity.base.MyApplication;
import com.lidroid.xutils.http.RequestParams;

/**
 * 
 * @author zxh
 * 用户信息的相关逻辑
 */
public class UserDao {


	//请求用户信息的地址
	final String  USER_INFO=HttpTools.URL+"userinfo.asp"+HttpTools.ROOT;
	final String  UPDATE_USERINFO=HttpTools.URL+"updateuserinfo.asp"+HttpTools.ROOT;

	/**
	 * 
	 * 通过手机号码去从服务器获取用户的信息
	 * @param userid
	 */
	public void fetchUserInfo(String phonenum,final UserDaoListener daoListener){

		RequestParams params = new RequestParams();
		params.addBodyParameter("uname", phonenum);
		HttpTools.send(USER_INFO,params,new MyHttpListener() {
			public void finish(String response) {
				
				if(JsonTools.isJson(response)){
					daoListener.fetchUserInfo(response.substring(1, response.length()-1));
				}
			}
		}, "用户信息");
	}

	/***
	 * 修改用户信息
	 * @param tag 修改的标记
	 * @param content
	 * @param tvcontent 
	 * @param updateUserListener
	 */
	public void updtaeUserInfo(String uname,String tag,String content, final UpdateUserListener updateUserListener){
		RequestParams params = new RequestParams();
		params.addBodyParameter("uname",uname);

		if(tag.equals("address")){
			params.addBodyParameter("address",content);
		}
		if(tag.equals("nickname")){
			params.addBodyParameter("nickname",content);
		}
	
		HttpTools.send(UPDATE_USERINFO, params, new MyHttpListener() {
			public void finish(String response) {	
				updateUserListener.updateUserInfo(response.substring(1,response.length()-1));
			}
		}, "修改信息");
		
	}	

	public interface UpdateUserListener{
		void updateUserInfo(String info);
	}
	/**
	 * 用户查询信息逻辑处理的接口
	 * @author zxh
	 *
	 */
	public interface UserDaoListener{
		/**
		 * json格式的用户信息
		 * @param jsonUserInfo
		 */
		void fetchUserInfo(String jsonUserInfo);
	}
}
