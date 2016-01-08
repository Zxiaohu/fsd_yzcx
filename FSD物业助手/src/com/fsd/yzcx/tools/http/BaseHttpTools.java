package com.fsd.yzcx.tools.http;

import com.fsd.yzcx.tools.JsonTools;
import com.fsd.yzcx.tools.LogUtil;
import com.fsd.yzcx.tools.SystemTools;
import com.fsd.yzcx.tools.http.impl.MyHttpListener;
import com.fsd.yzcx.ui.actvity.base.MyApplication;
import com.fsd.yzcx.ui.pager.base.imp.RoomLoginPager;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

/**
 * 请求网络工具
 * @author zxh
 *
 */
public abstract class BaseHttpTools {

	protected String url;
	protected RequestParams params;
	protected String tag;
	protected MyHttpListener httpListener; 
	
	
	public BaseHttpTools(String url, RequestParams params, String tag,
			MyHttpListener httpListener) {
		this.url = url;
		this.params = params;
		this.tag = tag;
		this.httpListener = httpListener;
	}




	/**
	 * 请求网络的方法
	 */
	 public void send(){
		 HttpUtils httpUtils = new HttpUtils(6 * 3000);
			//封装框架的请求接口
			httpUtils.send(HttpMethod.POST, url,params, new RequestCallBack<String>() {
				public void onFailure(HttpException arg0, String arg1) {
					//
					onHttpStop();
					SystemTools.showToastInfo(MyApplication.getContext(), tag+"请求失败", 3000, 2);
				}

				public void onSuccess(ResponseInfo<String> arg0) {
					onHttpStop();
					String response=arg0.result.toString();
					LogUtil.i(RoomLoginPager.class.getSimpleName(),response);
					if(JsonTools.isJson(response)){
						httpListener.fetchResponse(response);
					}else{
						SystemTools.showToastInfo(MyApplication.getContext(), tag+"返回的数据格式有误", 3000, 2);
						LogUtil.e(RoomLoginPager.class.getSimpleName(),tag+"返回的数据格式有误");
					}

				}

				public void onLoading(long total, long current, boolean isUploading) {
					onHttpRun();
					super.onLoading(total, current, isUploading);
					
				}

				public void onStart() {
					onHttpRun();
					super.onStart();
					
				}
			});
	 };
	
	 /**
	  * 请求正在运行时的操作
	  */
	 abstract protected void onHttpRun();
	 /**
	  * 请求结束的操作
	  */
	 abstract protected void onHttpStop();
}
