package com.fsd.yzcx.tools.http;
import com.fsd.yzcx.tools.http.impl.MyHttpListener;
import com.lidroid.xutils.http.RequestParams;

/**
 * 普通的请求方式
 * @author zxh
 *
 */
public class NormalHttp extends BaseHttpTools {

	public NormalHttp(String url, RequestParams params, String tag,
			MyHttpListener httpListener) {
		super(url, params, tag, httpListener);
		
	}
	protected void onHttpRun() {
	//不操作
	}
	protected void onHttpStop() {
	//无操作
	}

}
