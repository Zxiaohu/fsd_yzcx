package com.fsd.yzcx.dao.fuwu;

import android.app.Activity;

import com.fsd.yzcx.tools.http.BaseHttpTools;
import com.fsd.yzcx.tools.http.DialogHttp;
import com.fsd.yzcx.tools.http.HttpTools;
import com.lidroid.xutils.http.RequestParams;
import com.fsd.yzcx.tools.http.impl.MyHttpListener;

/**
 * 服务相关的操作
 * @author zxh
 *
 */
public class FuwuDao {

	private Activity context;//上下文对象
	public FuwuDao(Activity context){
		this.context=context;
	}
	private final String CONFIG = HttpTools.URL+"config.asp"+HttpTools.ROOT;
	
	/**
	 * 
	 * @param upid 主服务的id
	 * @param configtype 配置的类型
	 * @return 获取的信息
	 */
	public void fetchSubService(String upid,String configtype,final MyFWdaoListener listener){
		RequestParams params = new RequestParams();
		params.addBodyParameter("upid",upid);
		params.addBodyParameter("configtype",configtype);
		
		//带进度条的网络请求
		BaseHttpTools dialogHttp = new DialogHttp(context, CONFIG, params, "请求服务项", new MyHttpListener() {
			public void fetchResponse(String response) {
				listener.fetchInfo(response);
			}
		});
		
		dialogHttp.send();//发送网络请求
		
	}
	public interface MyFWdaoListener{
		public void fetchInfo(String response);
	}
	
}
