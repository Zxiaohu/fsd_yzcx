package com.fsd.yzcx.ui.actvity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.fsd.yzcx.R;
import com.fsd.yzcx.tools.Utils;
import com.fsd.yzcx.ui.actvity.base.BaseActivity;
import com.fsd.yzcx.ui.actvity.base.MyApplication;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class MapActivity extends BaseActivity implements AMapLocationListener, OnClickListener{

	private AMapLocationClient locationClient = null;
	private AMapLocationClientOption locationOption = null;

	@ViewInject(R.id.textView1)
	private TextView tv;
	@ViewInject(R.id.button1)
	private Button btn;
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		//在onCreat方法中给aMap对象赋值
		setContentView(R.layout.activity_map);

		ViewUtils.inject(this);

		btn.setOnClickListener(this);
		
		//初始化位置相关的对象
		locationClient = new AMapLocationClient(MyApplication.getContext());
		locationOption = new AMapLocationClientOption();

		// 设置定位模式为低功耗模式
		locationOption.setLocationMode(AMapLocationMode.Battery_Saving);
		//设置为单次定位
		locationOption.setOnceLocation(true);
		// 设置定位监听
		locationClient.setLocationListener(this);
		

	}



	protected void onDestroy() {
		super.onDestroy();
		if (null != locationClient) {
			/**
			 * 如果AMapLocationClient是在当前Activity实例化的，
			 * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
			 */
			locationClient.onDestroy();
			locationClient = null;
			locationOption = null;
		}
	}

	Handler mHandler = new Handler(){
		public void dispatchMessage(android.os.Message msg) {
			switch (msg.what) {
			case Utils.MSG_LOCATION_START:
				tv.setText("正在定位...");
				break;
			//定位完成
			case Utils.MSG_LOCATION_FINISH:
				AMapLocation loc = (AMapLocation)msg.obj;
				String result = Utils.getLocationStr(loc);
				tv.setText(result);
				break;
			case Utils.MSG_LOCATION_STOP:
				tv.setText("定位停止");
				break;
			default:
				break;
			}
		};
	};
	public void onClick(View v) {
		if(v.getId()==R.id.button1){
			// 设置定位参数
			locationClient.setLocationOption(locationOption);
			// 启动定位
			locationClient.startLocation();
			
			mHandler.sendEmptyMessage(Utils.MSG_LOCATION_START);
		}
	}
	
	public void onLocationChanged(AMapLocation loc) {
		if (null != loc) {
			Message msg = mHandler.obtainMessage();
			msg.obj = loc;
			msg.what = Utils.MSG_LOCATION_FINISH;
			mHandler.sendMessage(msg);
		}
	}
}
