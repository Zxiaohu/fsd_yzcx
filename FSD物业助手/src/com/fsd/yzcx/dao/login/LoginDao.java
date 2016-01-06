package com.fsd.yzcx.dao.login;

import java.util.Random;
import org.json.JSONException;
import org.json.JSONObject;
import com.fsd.yzcx.tools.LogUtil;
import com.fsd.yzcx.tools.http.HttpTools;
import com.fsd.yzcx.tools.http.HttpTools.MyHttpListener;
import com.fsd.yzcx.ui.actvity.base.MyApplication;
import com.google.gson.Gson;
import com.lidroid.xutils.http.RequestParams;

/**
 * 用户登录的相关行为封装 
 * @author zxh
 *
 */
public class LoginDao {

	//请求服务器发送验证码

	/**
	 * @param phonenum 手机号
	 * @return 从服务器获取的验证码
	 */

	public void getCheckNum(String phonenum,final myInterfaceCheckNum interface1){

		RequestParams params = new RequestParams();
		final String yzm = get6Random()+"";
		params.addBodyParameter("username", phonenum);
		params.addBodyParameter("yzm",yzm);
		LogUtil.i("smsmag",yzm);


		HttpTools.send(HttpTools.SEND_MSG, params,
				new MyHttpListener() {
			public void finish(String response) {
				String newres= response.substring(1, response.length()-1);
				LogUtil.i("smsmag",newres);
				JSONObject object =new JSONObject();
				String flag = null;
				String info;
				try {
					flag = object.getString("flag");
					info=object.getString("info");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				boolean isChecked = false;
				LogUtil.i("smsmag",newres);
				if(!flag.equals("1")){//验证成功
					isChecked=true;
				}

				interface1.checkNum(yzm, isChecked);
			}
		}, "验证手机号");
	}

	/**
	 * 普通登录方式
	 * @param uname 用户名/房间的编号/用户的手机号的统称
	 * @param upwd  密码
	 * @param interface1 验证后的回调接口
	 */
	public void checkNormalLogin(String uname,String upwd,final myInterfaceCheckNormalLogin interface1){

		//发送网络请求验证账号并返回结果
		RequestParams params = new RequestParams("utf-8");
		params.addBodyParameter("uname",uname);
		params.addBodyParameter("upwd",upwd);
		
		HttpTools.send(HttpTools.LOGIN, params, new MyHttpListener() {
			public void finish(String response) {
				String newres= response.substring(1, response.length()-1);
				Gson gson = new Gson();
				LoginUserInfo loginUserInfo = gson.fromJson(newres, LoginUserInfo.class);//解析json
				LogUtil.i("test",newres);
				interface1.checkNormalLogin(loginUserInfo);
			}
		}, "用户登录");
	}



	public interface myInterfaceCheckNum{
		/**
		 * 就是为了查看是否发送短信
		 */
		public void checkNum(String yzm,boolean isChecked);

	}
	/**
	 * @param userinfo 用户的信息
	 */
	public interface myInterfaceCheckNormalLogin{
		public void checkNormalLogin(LoginUserInfo  userinfo);
	}
	/**
	 * 生成6位数的验证码
	 * @return
	 */
	public int get6Random(){

		int max=999999;
		int min=100000;
		Random random = new Random();

		int s = random.nextInt(max)%(max-min+1) + min;

		return s;
	}


}
