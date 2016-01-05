package com.fsd.yzcx.dao;

import java.util.Random;
import org.json.JSONException;
import org.json.JSONObject;
import com.fsd.yzcx.tools.LogUtil;
import com.fsd.yzcx.tools.http.HttpTools;
import com.fsd.yzcx.tools.http.HttpTools.MyHttpListener;
import com.fsd.yzcx.ui.actvity.base.MyApplication;
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

	public void getCheckNum(String phonenum,final myInterface interface1){

		RequestParams params = new RequestParams();
		final String yzm = get6Random()+"";
		params.addBodyParameter("username", phonenum);
		params.addBodyParameter("yzm",yzm);
		LogUtil.i("smsmag",yzm);
		
			
		HttpTools.send(HttpTools.SEND_MSG, params, MyApplication.getContext(),
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

				if(!flag.equals("1")){
					isChecked=true;
				}

				interface1.checkNum(yzm, isChecked);
			}
		}, "验证手机号");
	}

	public interface myInterface{
		/**
		 * 就是为了查看是否发送短信
		 */
		public void checkNum(String yzm,boolean isChecked);
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
