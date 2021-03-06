package com.fsd.yzcx.dao.user;
import android.content.Context;

import com.fsd.yzcx.tools.JsonTools;
import com.fsd.yzcx.tools.LogUtil;
import com.fsd.yzcx.tools.http.DialogHttp;
import com.fsd.yzcx.tools.http.HttpTools;
import com.fsd.yzcx.tools.http.NormalHttp;
import com.lidroid.xutils.http.RequestParams;
import com.fsd.yzcx.tools.http.impl.MyHttpListener;
/**
 * 
 * @author zxh
 * 用户信息的相关逻辑
 */
public class UserDao {

	private Context context;
	public UserDao(Context context) {
	
		this.context=context;
		
	}
	//请求用户信息的地址
	final String  USER_INFO=HttpTools.URL_YZ+"userinfo.asp"+HttpTools.ROOT;
	final String  UPDATE_USERINFO=HttpTools.URL_YZ+"updateuserinfo.asp"+HttpTools.ROOT;

	/**
	 * 
	 * 通过手机号码去从服务器获取用户的信息
	 * @param userid
	 */
	public void fetchUserInfo(String phonenum,final UserDaoListener daoListener){

		RequestParams params = new RequestParams();
		params.addBodyParameter("uname", phonenum);
		
		DialogHttp http = new DialogHttp(context,USER_INFO, params, "用户信息", new MyHttpListener() {
			public void fetchResponse(String response) {
				if(JsonTools.isJson(response)){
					daoListener.fetchUserInfo(response.substring(1, response.length()-1));
				}
			}
		});
		
		http.send();
		
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
		
		//电话号码
		params.addBodyParameter(Param.UNAME.getName(),uname);

		//修改地址
		if(tag.equals(Param.ADDRESS.getName())){
			params.addBodyParameter(Param.ADDRESS.getName(),content);
		}
		//修改昵称
		if(tag.equals(Param.NICKNAME.getName())){
			params.addBodyParameter(Param.NICKNAME.getName(),content);
		}
	
		DialogHttp http = new DialogHttp(context,UPDATE_USERINFO, params, "用户信息", new MyHttpListener() {
			public void fetchResponse(String response) {
				if(JsonTools.isJson(response)){
					updateUserListener.updateUserInfo(response.substring(1, response.length()-1));
				}

			}
		});
		
		http.send();//发送请求
	}	

	/**
	 * 
	 * @param uname 手机号
	 * @param oldpwd 旧密码
	 * @param newpwd 新密码
	 * @param listener 修改密码的监听事件
	 */
	public  void alterPwd(String uname,String oldpwd,String newpwd,final UpdateUserListener listener){
		
		RequestParams params = new RequestParams();
		params.addBodyParameter(Param.UNAME.getName(),uname);//手机号
		params.addBodyParameter(Param.OLDPASSWORD.getName(),oldpwd);//旧密码
		params.addBodyParameter(Param.PASSWORD.getName(),newpwd);//新密码
		DialogHttp http = new DialogHttp(context,UPDATE_USERINFO, params , "用户信息", new MyHttpListener() {
			public void fetchResponse(String response) {
				if(JsonTools.isJson(response)){
					listener.updateUserInfo(response.substring(1, response.length()-1));
				}
			}
		});
		
		http.send();//发送请求
		
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
