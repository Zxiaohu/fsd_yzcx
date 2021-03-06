package com.fsd.yzcx.tools.http;
import com.fsd.yzcx.tools.JsonTools;
import com.fsd.yzcx.tools.LogUtil;
import com.fsd.yzcx.tools.SystemTools;
import com.fsd.yzcx.ui.actvity.base.MyApplication;
import com.fsd.yzcx.ui.pager.base.imp.RoomLoginPager;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

/**
 * HttpTools
 * 
 * @author xiaohu 请求网络工具类
 */

public class HttpTools {

	//public static final String URL = "http://www.fusaide.com/aspapp/yzcx/";
	//	public static final String URL = "http://www.fusaide.com/aspapp/wygl/";

	public static final String URL = "http://192.168.8.110/aspapp/wygl/";
	public static final String URL_YZ = "http://192.168.8.110/aspapp/yzcx/";
	public static final String ROOT = "?sys=android";
	public static final String APP_LOGIN = URL + "applogin.asp" + ROOT;// 房号登录
	
	public static final String LOGIN = URL_YZ + "login.asp" + ROOT;// 普通登录
	
	public static final String SEND_MSG = URL_YZ + "sendmsg.asp" + ROOT;// 发送短信
	
	
	
	
	
	
	
	
	
	public static final String NOTICE = URL + "notice.asp" + ROOT;// 公告
	// 获取管理项的URL
	public static final String PRECINCT = URL + "precinct.asp" + ROOT;
	// 获取收费项的URL
	public static final String MONEYNAME = URL + "moneyname.asp" + ROOT;

	//查询物业费截止日期
	public static final String MONEYENDDATE = URL + "moneyenddate.asp" + ROOT;

	//查询水电费余额
	public static final String BALANCE = URL + "balance.asp" + ROOT;

	//请求收款方式的URL
	public static final String MONEYTYPE = URL + "moneytype.asp" + ROOT;

	//请求票据列表的URL
	public static final String BILL = URL + "bill.asp" + ROOT;

	//请求银行账户的URL
	public static final String BANKACCOUNT = URL + "bankaccount.asp" + ROOT;

	//收费录入
	//public static final String SAVERECORD = "http://192.168.8.32:8080/jsp/rel";

	public static final String SAVERECORD = URL + "saverecord.asp" + ROOT;
	// 查询收费记录的URL
	public static final String REQUESTBYDATE = URL + "requestbydate.asp" + ROOT;
	// 获取大楼项的URL
	public static final String BUILDING = URL + "building.asp" + ROOT;
	// 获取单元楼道的URL
	public static final String FLOOR = URL + "floor.asp" + ROOT;
	// 获取房间号的URL
	public static final String ROOM = URL + "room.asp" + ROOT;

	// 获取用户信息的的URL
	public static final String ROOM_INFO = URL + "roominfo.asp" + ROOT;

	// 诉求记录人的URL
	public static final String RECORDER_APPEAL = URL + "recordman.asp" + ROOT;
	// 诉求时效的URL
	public static final String AGEING_APPEAL = URL + "complainmain.asp" + ROOT
			+ "&catalog=complaintype";
	// 诉求方式的URL
	public static final String MODE_APPEAL = URL + "complainmain.asp" + ROOT
			+ "&catalog=complainform";
	// 保存诉求登记的URL
	public static final String SAVEAPPEAL = URL + "savecomplain.asp" + ROOT;
	//业主信息的URL
	public static final String OWNER_INFO = URL + "roominfo.asp" + ROOT;
	//查询诉求的URL
	public static final String QUERY_APPEAL = URL + "complainlist.asp" + ROOT;
	//查询设备主项的URL
	public static final String Type_FACILITY = URL + "sbmaster.asp" + ROOT;
	//查询设备子项的URL
	public static final String SUBTYPE_FACILITY = URL + "sbsub.asp" + ROOT;
	//查询保养记录的URL
	public static final String QUERY_MAINTAIN = URL + "sbmaintain.asp" + ROOT;

	//改造普通的网络请求
	/**
	 * 
	 * @param url
	 *            请求的地址
	 * @param params
	 *            请求的参数
	 * @param listener
	 *            结果监听
	 */
	public static void send(String url, RequestParams params,
			final MyHttpListener listener,final String tag
			) {
		
		HttpUtils httpUtils = new HttpUtils(6 * 3000);
		
		//封装框架的请求接口
		httpUtils.send(HttpMethod.POST, url,params, new RequestCallBack<String>() {
			
			public void onFailure(HttpException arg0, String arg1) {
			//
				
				//DialogHelper.showDialog(MyApplication.getContext(), false);
				SystemTools.showToastInfo(MyApplication.getContext(), tag+"请求失败", 3000, 2);
				
			}

			public void onSuccess(ResponseInfo<String> arg0) {
				
				//SystemTools.showToastInfo(context, tag+"请求成功", 3000, 2);
				//DialogHelper.showDialog(MyApplication.getContext(), false);
				String response=arg0.result.toString();
				LogUtil.i(RoomLoginPager.class.getSimpleName(),response);
				if(JsonTools.isJson(response)){
					listener.finish(response);
				}else{
					SystemTools.showToastInfo(MyApplication.getContext(), tag+"返回的数据格式有误", 3000, 2);
					LogUtil.e(RoomLoginPager.class.getSimpleName(),tag+"返回的数据格式有误");
				}
				
			}

			public void onLoading(long total, long current, boolean isUploading) {
				super.onLoading(total, current, isUploading);

				//DialogHelper.showDialog(MyApplication.getContext(), true);
				listener.onloading(total, current, isUploading);
				
			}

			public void onStart() {
				super.onStart();

				//DialogHelper.showDialog(MyApplication.getContext(), true);

			}
		});

	}
	
}
