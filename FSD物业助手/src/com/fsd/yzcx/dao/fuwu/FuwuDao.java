package com.fsd.yzcx.dao.fuwu;

import android.content.Context;

import com.fsd.yzcx.dao.DialogHelper;
import com.fsd.yzcx.tools.http.HttpTools;
import com.fsd.yzcx.tools.http.MyHttpListener;
import com.lidroid.xutils.http.RequestParams;


/**
 * 服务相关的操作
 * @author zxh
 *
 */
public class FuwuDao {

	private Context context;//上下文对象
	public FuwuDao(Context context){
		this.context=context;
	}
	private final String CONFIG = HttpTools.URL+"config.asp"+HttpTools.ROOT;
	
	/**
	 * 
	 * @param upid 主服务的id
	 * @param configtype 配置的类型
	 * @return 获取的信息
	 */
	public String fetchSubService(String upid,String configtype,final MyFWdaoListener listener){
		RequestParams params = new RequestParams();
		params.addBodyParameter("upid",upid);
		params.addBodyParameter("configtype",configtype);
		
		//发送请求获取子服务项
		HttpTools.send(CONFIG, params, new MyHttpListener() {
			public void finish(String response) {
				//将结果返回出去
				listener.fetchSubService(response);
			}
			public void onloading(long total, long current, boolean isUploading) {
				super.onloading(total, current, isUploading);
				//显示进度条
				DialogHelper.showDialog(context, isUploading);	
			}
		}, "请求服务");
		
		
		return "";
	}
	public interface MyFWdaoListener{
		void fetchSubService(String response);
	}
}
