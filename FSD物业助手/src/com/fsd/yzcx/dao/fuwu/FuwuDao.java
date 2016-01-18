package com.fsd.yzcx.dao.fuwu;
import android.content.Context;
import com.fsd.yzcx.tools.SystemTools;
import com.fsd.yzcx.tools.http.BaseHttpTools;
import com.fsd.yzcx.tools.http.DialogHttp;
import com.fsd.yzcx.tools.http.HttpTools;
import com.lidroid.xutils.http.RequestParams;
import com.fsd.yzcx.tools.http.impl.MyHttpListener;
import com.fsd.yzcx.ui.actvity.base.MyApplication;
import com.google.gson.Gson;

/**
 * 服务相关的操作
 * @author zxh
 *
 */
public class FuwuDao {

	private final String CONFIG = HttpTools.URL+"config.asp"+HttpTools.ROOT;
	private final String PAIGONG = HttpTools.URL_YZ+"paigong.asp"+HttpTools.ROOT;

	private Context context;//上下文对象
	public FuwuDao(Context context){
		this.context=context;
	}

	class Info{
		int flag;
		String info;
	}
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
				listener.fetchInfo(response);//回调接口
			}
		});

		dialogHttp.send();//发送网络请求

	}


	/**
	 * 派工
	 * @param houseid  房间名称id
	 * @param Complainer 派单人 业主姓名
	 * @param TelNumber 业主电话
	 * @param Content 内容
	 * @param ComplainTypeID主服务id
	 * @param ComplainSubID子服务项id
	 * @param paigongType派工类型 1 指派 2接待 空值都可以
	 */
	public void pullPaiGong(
			String houseid,
			String Complainer,
			String TelNumber,
			String Content,
			String ComplainTypeID,
			String ComplainSubID,final MyFWdaoListener listener){

		RequestParams params = new RequestParams();
		params.addBodyParameter("houseid",houseid);
		params.addBodyParameter("Complainer",Complainer);
		params.addBodyParameter("TelNumber",TelNumber);
		params.addBodyParameter("Content",Content);
		params.addBodyParameter("ComplainTypeID",ComplainTypeID);
		params.addBodyParameter("ComplainSubID",ComplainSubID);
		params.addBodyParameter("submit","ok");

		//带进度条的网络请求
		BaseHttpTools dialogHttp = new DialogHttp(context,PAIGONG, params, "提交服务订单", new MyHttpListener() {
			public void fetchResponse(String response) {
				String info = response.substring(1, response.length()-1);
				Info info2 = new Gson().fromJson(info, Info.class);
				if(info2.flag==1){
					listener.fetchInfo(info2.info);			
				}else{
					SystemTools.showToastInfo(MyApplication.getContext(), info2.info, 3000, 1);
				}
			}
		});

		dialogHttp.send();//发送网络请求
	}

	/**
	 * 获取客服中心的联系电话
	 */
	public void fetchTelInfo(final MyFWdaoListener listener){
		RequestParams params = new RequestParams();
		params.addBodyParameter("configtype","telephone");
		//带进度条的网络请求
		BaseHttpTools dialogHttp = new DialogHttp(context,CONFIG, params, "提交服务订单", new MyHttpListener() {
			public void fetchResponse(String response) {
				listener.fetchInfo(response);//回调接口
			}
		});

		dialogHttp.send();//发送网络请求
	}
	
	
	/**
	 * 
	 * @author zxh 获取信息的接口
	 *
	 */
	public interface MyFWdaoListener{
		public void fetchInfo(String response);
	}

}
