package com.fsd.yzcx.ui.helper;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fsd.yzcx.R;
import com.fsd.yzcx.tools.DataTools;
import com.fsd.yzcx.tools.JsonTools;
import com.fsd.yzcx.ui.adapter.CommonAdapter;
import com.fsd.yzcx.ui.adapter.ViewHolder;
import com.fsd.yzcx.ui.helper.RoomSelectHelper.RoomNameInfo.Housename;
import com.google.gson.Gson;
/**
 * 根据是否多房间显示相应的数据
 * @author zxh
 *
 *
 */
public class RoomSelectHelper {
	/**
	 * 设计分析:
	 *  1.数据就是从本地的   缓存文件中拿到:进行判断是否是多房间
	 *  
	 *  2.判断条件：如果是json格式的字符串那就是多用户
	 *  
	 *  3.返回的结果：如果是多用户,需要解析返回,单用户直接获取
	 */

	/**
	 * 
	 * @param data 原始数据
	 * @param index 索引
	 * @param strArrkey 如果是json的数组的key
	 * @param objectkey 如果是json的
	 * @return
	 */
	public static String getRoomInfo(String data,int index,String strArrkey,String objectkey){
		return DataTools.mJson2StrArr(data, strArrkey, objectkey)[index];
	}

	class RoomNameInfo{//房间信息
		public List<Housename> jsonHousename;//房间名称的集合
		class Housename{//房间名称的类
			public String housename;
		}
	}
	/**
	 * 根据不同的数据给选择地址设置不同的适配器 
	 * @param context
	 * @return
	 */
	public static BaseAdapter getCommonAdapter(Context context,String data){
		
		if(JsonTools.isJson(data)){//如果是json数据肯定是多房间
			RoomNameInfo nameInfo = new Gson().fromJson(data, RoomNameInfo.class);
			//创建适配器
			CommonAdapter<Housename> adapter = new CommonAdapter<RoomSelectHelper.RoomNameInfo.Housename>(context,nameInfo.jsonHousename,R.layout.adapter_simple) {
				public void convert(ViewHolder helper, Housename item) {
					TextView textView=helper.getView(R.id.tv_fuwu_title);
					textView.setText(item.housename);
				}
			};
			return adapter;
		}else{
			List<String> strings = new ArrayList<String>();
			strings.add(data);
			//创建适配器
			CommonAdapter<String> adapter = new CommonAdapter<String>(context,strings,R.layout.adapter_simple) {
				public void convert(ViewHolder helper, String item) {
					TextView textView=helper.getView(R.id.tv_fuwu_title);
					textView.setText(item);
				}
			};
			return adapter;
		}
	}
}
