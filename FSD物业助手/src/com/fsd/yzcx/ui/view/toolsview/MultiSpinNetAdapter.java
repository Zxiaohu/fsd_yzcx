package com.fsd.yzcx.ui.view.toolsview;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.fsd.yzcx.tools.http.HttpTools;
import com.fsd.yzcx.tools.http.HttpTools.MyHttpListener;
import com.fsd.yzcx.tools.JsonTools;
import com.fsd.yzcx.tools.LogUtil;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

/**
 * 多Spinner网络适配器，需要传递Activity， 适合于几个Spinner耦合性较高的情况，如：
 * 上一个Spinner的取值决定下一个Spinner的请求网络的参数
 * 
 * @author Paul
 * 
 * 
 */
public class MultiSpinNetAdapter {
	public interface SpinListener {
		/**
		 * 根据Spinners的顺位获取对应的URL参数params。
		 * 适配器内部提供了根据当前顺位获取选中Spinner的item的id的方法，
		 * 请使用{@link MultiSpinNetAdapter#getSpinSelectedId(int index)}
		 * 
		 * @param index
		 *            Spinner的顺位
		 * @return
		 */
		public RequestParams getParamsByindex(int index);

		/** 初始化Spinner完成 时，进行处理 **/
		public void onFinish();

	}

	protected static final String tag = "MultiSpinNetAdapter";

	private View view;

	/**
	 * 用于存放spinner的id的数组
	 */
	private int[] spIds;

	/**
	 * 用于存放请求Spinner的数据时，接收其条目内容的键名
	 */
	private String[] itemTextKeys;
	/**
	 * 用于存放请求Spinner的数据时，接收其条目id的键名
	 */
	private String[] itemIdKeys;
	/**
	 * 用于存放各个spinner的数据的数组
	 */
	private String[][] contentArrayList;

	/**
	 * 用于存放各个spinner的数据id数组的二维数组
	 */
	private String[][] idArrayList;

	/**
	 * 存放各个Spinner对应的Url
	 */
	private String[] spinUrls;

	private SpinListener listener;

	/**
	 * 
	 * @param view
	 * @param spIds
	 *            Spinner的引用
	 * @param itemTextKeys
	 *            接收数据时item的内容键名
	 * @param itemIdKeys
	 *            接收数据时item的id键名
	 * @param listener
	 *            用于动态获取URL的监听器
	 */
	public MultiSpinNetAdapter(View view, int[] spIds,
			String[] itemTextKeys, String[] itemIdKeys, String[] spinUrls) {
		this.view = view;
		this.spIds = spIds;
		this.spinUrls = spinUrls;
		this.itemTextKeys = itemTextKeys;
		this.itemIdKeys = itemIdKeys;
	}

	/**
	 * 请求网络时，如果发送的参数是从上一个Spinner获取的 就加入一个监听器，对Params进行动态的获取
	 * 
	 * @param listener
	 *            Params参数的监听
	 */
	public void start(SpinListener listener) {
		this.listener = listener;
		start();
	}

	public void start() {
		// 准备工作
		contentArrayList = new String[spIds.length][];
		idArrayList = new String[spIds.length][];

		requestData(0);

	}

	/**
	 * 更新spinner的数据
	 * 
	 * @param index
	 *            Spinner的顺位
	 */
	private void showData(final int index) {
		Spinner sp = (Spinner)view.findViewById(spIds[index]);
		if(sp == null)
			return;
		sp.setAdapter(new ArrayAdapter<String>(view.getContext(),
				android.R.layout.simple_dropdown_item_1line,
				contentArrayList[index]));
		sp.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				if (index != spIds.length - 1) {
					// 请求下一个spinner的数据
					requestData(index + 1);
				} else {
					if (listener != null) {
						listener.onFinish();
					}
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});

	}

	/**
	 * 请求网络得到数据
	 * 
	 * @param index
	 */
	private void requestData(final int index) {
		String url = spinUrls[index];
		RequestParams params = null;
		if (listener != null) {
			params = listener.getParamsByindex(index);
		}
		HttpUtils http = new HttpUtils();
		http.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				Toast.makeText(view.getContext(), "连接失败", Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				Toast.makeText(view.getContext(), "请求成功", Toast.LENGTH_SHORT).show();
				
					String[][] data = JsonTools.jArray2StrArrays(
							arg0.result.toString(), itemTextKeys[index],
							itemIdKeys[index]);
					if(data!= null){
					contentArrayList[index] = data[0];
					idArrayList[index] = data[1];
					showData(index);
					}
				
			}
		});
//		
//		HttpTools.send(url, params, (FragmentActivity)view.getContext(), new MyHttpListener() {
//			
//			public void finish(String response) {
//				String[][] data = JsonTools.jArray2StrArrays(
//					response, itemTextKeys[index],
//						itemIdKeys[index]);
//				if(data!= null){
//				contentArrayList[index] = data[0];
//				idArrayList[index] = data[1];
//				showData(index);
//				}
//			}
//		}, "请求房号");
	}

	/**
	 * 根据传的index值找到其对应的spinner的选中的item对应网络请求的id
	 * 
	 * @param index
	 *            Spinner的id或顺位index
	 * @return position
	 */
	public String getSpinSelectedId(int index) {
		String itemId = null;
		// 对index进行判断 ，大于50（实现上大于5就已经很少了），则会是Spinner在布局中的id值，是一个很大的数
		int spId = -1;
		int spinPosition = 0;
		if (index > 50) {
			spId = index;
			for (int i = 0; i < spIds.length; i++) {
				if (spIds[i] == index) {
					spinPosition = i;
					break;
				}
			}

		} else {
			spId = spIds[index];
			spinPosition = index;
		}
		if(idArrayList[spinPosition] != null){
		int itemPosition = ((Spinner)view.findViewById(spId))
				.getSelectedItemPosition();
		itemId = idArrayList[spinPosition][itemPosition];
		return itemId;
		}
		return null;
	}

}
