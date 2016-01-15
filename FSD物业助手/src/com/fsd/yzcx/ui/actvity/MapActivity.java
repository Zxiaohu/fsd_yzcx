package com.fsd.yzcx.ui.actvity;

import android.os.Bundle;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.fsd.yzcx.R;
import com.fsd.yzcx.ui.actvity.base.BaseActivity;

public class MapActivity extends BaseActivity implements LocationSource {

	//声明变量
    private MapView mapView;
    private AMap aMap;
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		//在onCreat方法中给aMap对象赋值
	    setContentView(R.layout.activity_map);
	    
	    mapView = (MapView) findViewById(R.id.map);
	    mapView.onCreate(arg0);// 必须要写
	    aMap = mapView.getMap();
	   // aMap.setMapType(AMap.MAP_TYPE_NORMAL);// 矢量地图模
	    aMap.setLocationSource(this);// 设置定位监听
	    aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
	   // aMap.setMyLocationType(AMap.LOCATION_TYPE_MAP_ROTATE);//设置定位类型
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
	}
	public void activate(OnLocationChangedListener arg0) {
		
		
		
	}
	public void deactivate() {
		
	}
}
