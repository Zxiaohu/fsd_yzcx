package com.fsd.yzcx.dao.order;
import com.fsd.yzcx.tools.LogUtil;
import com.fsd.yzcx.tools.http.DialogHttp;
import com.fsd.yzcx.tools.http.HttpTools;
import com.fsd.yzcx.tools.http.impl.MyHttpListener;
import com.lidroid.xutils.http.RequestParams;

import android.content.Context;

/**
 * 订单相关的网络处理
 * @author zxh
 *
 */
public class OrderDao {
	
	private String PAIGONG_LIST=HttpTools.URL_YZ+"paigonglist.asp"+HttpTools.ROOT;//派工列表
	
	//派工进度
	private String PAIGONG_RATE=HttpTools.URL+"paigongrate.asp"+HttpTools.ROOT;
	
	
	private Context context;
	public OrderDao(Context context) {
	this.context=context;
	}
	
	/**
	 * 获取所有订单的信息
	 * paigonglist.asp  所有派工列表
		uname 用户名
		返回 派工id   
		pressstate 状态 
		content 派工内容 
		ComplainDate 派工时间 
	 */
	public void fetchDetails(String uname,final FetchListener listener){
			RequestParams params = new RequestParams();
			params.addBodyParameter("uname",uname);
			DialogHttp dialogHttp = new DialogHttp(context, PAIGONG_LIST, params, "请求服务详情列表", new MyHttpListener() {
				public void fetchResponse(String response) {
					listener.getDetails(response);
				}
			});
			dialogHttp.send();
	}
	/**
	 * paigongrate.asp  派工进度

	 */
	public void fetchRate(String complainid,final FetchListener listener){
		
		RequestParams params = new RequestParams();
		params.addBodyParameter("complainid",complainid);
		DialogHttp dialogHttp = new DialogHttp(context, PAIGONG_RATE, params, "请求服务进度", new MyHttpListener() {
			public void fetchResponse(String response) {
				LogUtil.d("test1", response);
				listener.getDetails(response);
			}
		});
		dialogHttp.send();
		
	}
}
