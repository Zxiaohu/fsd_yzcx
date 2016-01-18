package com.fsd.yzcx.ui.view.toolsview;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;

import com.fsd.yzcx.R;
import com.fsd.yzcx.tools.ForEmptyTool;
import com.fsd.yzcx.tools.http.HttpTools;
import com.fsd.yzcx.ui.view.toolsview.MultiSpinNetAdapter.SpinListener;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * 封装好的一个房间选择器 提供了一些get类方法获取各个组件的引用与id
 * 
 * @author Administrator
 * 
 */
public class RoomInfoChooser extends TableLayout {
	private Context context;
	@ViewInject(R.id.sp_precinct_chooser)
	Spinner sp_precinct_chooser;
	@ViewInject(R.id.sp_building_chooser)
	Spinner sp_building_chooser;
	@ViewInject(R.id.sp_floor_chooser)
	Spinner sp_floor_chooser;
	@ViewInject(R.id.sp_room_chooser)
	Spinner sp_room_chooser;
	@ViewInject(R.id.tv_tip_precinct)
	TextView tv_tip_precinct;
	@ViewInject(R.id.tv_tip_building)
	TextView tv_tip_building;
	@ViewInject(R.id.tv_tip_floor)
	TextView tv_tip_floor;
	@ViewInject(R.id.tv_tip_room)
	TextView tv_tip_room;
	private MultiSpinNetAdapter linkage;

	private PrecinctListener listener;
	protected AllSelectedListener allSelectedListener;

	public void setAllSelectedListener(AllSelectedListener allSelectedListener) {
		this.allSelectedListener = allSelectedListener;
	}

	public void setPrecinctListener(PrecinctListener listener) {
		this.listener = listener;
	}

	/**
	 * 定义一个接口，将Precinct选中时要做的操作往外抛 让使用这个控件的Activity或Fragment来实现
	 * 
	 * @author Administrator
	 * 
	 */
	public interface PrecinctListener {
		/**
		 * 当管理区被选择时要做出的处理
		 * 
		 * @param preId
		 *            管理区选中的item对应的网络请求id 没选中时preId为空
		 */
		public void onPrecinctSelected(String preId);
	}

	/**
	 * 定义一个接口，将所有Spinner初始化完成并且被选中时要做的操作往外抛 让使用这个控件的Activity或Fragment来实现
	 * 
	 * @author Administrator
	 * 
	 */
	public interface AllSelectedListener {
		/** 当所有都被选择时要做出的处理 
		 * @param roomName 完整房间名
		 * @param num 业主编号*/
		public void onAllSelected(String num, String roomName);
	}

	public RoomInfoChooser(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		initView();

	}
	
	public RoomInfoChooser(Context context) {
		super(context);
		this.context = context;
		initView();
		
	}
	
	private void initView() {
		View.inflate(context,R.layout.room_chooser, this);
		ViewUtils.inject(this); 

	}

	
	public void initSpinner(View view){
		int[] spIds2 = new int[] {R.id.sp_precinct_chooser, R.id.sp_building_chooser, R.id.sp_floor_chooser,
				R.id.sp_room_chooser };
		String[] itemTextKeys2 = new String[] {"PrecinctName", "BuildingName", "FloorName",
				"RoomID" };
		String[] itemIdKeys2 = new String[] {"PrecinctID", "BuildingID", "FloorID", "RoomID" };
		String[] spinUrls2 = new String[] { HttpTools.PRECINCT,HttpTools.BUILDING,
				HttpTools.FLOOR, HttpTools.ROOM };
		linkage = new MultiSpinNetAdapter(view, spIds2, itemTextKeys2,
				itemIdKeys2, spinUrls2);
		linkage.start(new SpinListener() {

			public RequestParams getParamsByindex(int index) {
				RequestParams params = new RequestParams();
				switch (index) {
				case 1:
					// 请求大楼的URL的参数
					params.addBodyParameter("PrecinctID", linkage.getSpinSelectedId(0));
					break;
				case 2:
					// 请求单元楼道的URL参数
					params.addBodyParameter("PrecinctID", linkage.getSpinSelectedId(0));
					params.addBodyParameter("BuildingID",
							linkage.getSpinSelectedId(1));
					break;
				case 3:
					// 请求房间号的URL参数
					params.addBodyParameter("PrecinctID", linkage.getSpinSelectedId(0));
					params.addBodyParameter("BuildingID",
							linkage.getSpinSelectedId(1));
					params.addBodyParameter("FloorID",
							linkage.getSpinSelectedId(2));
					break;
				default:
					break;
				}
				return params;
			}

			@Override
			public void onFinish() {
				if (allSelectedListener != null)
					allSelectedListener.onAllSelected(getOwnerNum(),getWholeRoomName());
			}

		});

	}

	/***
	 * 获取Spinner选中的item对应的id 如果没有选择，返回null
	 * 
	 * @param index
	 * @return
	 */
	public String getSpinSelectedId(int index) {
		if (index == 0) {
			return linkage.getSpinSelectedId(0);
		}
		return linkage.getSpinSelectedId(index);
	}

	public Spinner getPrecinct() {

		return sp_precinct_chooser;

	}

	public Spinner getSpinByIndex(int index) {

		switch (index) {
		case 0:
			return sp_precinct_chooser;
		case 1:

			return sp_building_chooser;
		case 2:

			return sp_floor_chooser;
		case 3:

			return sp_room_chooser;

		default:
			break;
		}
		return null;
	}

	public TextView getTipTextByIndex(int index) {
		switch (index) {
		case 0:
			return tv_tip_precinct;
		case 1:

			return tv_tip_building;
		case 2:

			return tv_tip_floor;
		case 3:

			return tv_tip_room;

		default:
			break;

		}
		return null;
	}

	public boolean isAnyEmpty() {
		return ForEmptyTool.isAnyEmpty(new Object[] { R.id.sp_precinct_chooser,
				R.id.sp_building_chooser, R.id.sp_floor_chooser, R.id.sp_room_chooser });
	}
	/** 获取业主编号 ***/
	public String getOwnerNum(){
		String temp = "";
		for (int i = 0; i < 4; i++) {
			if(getSpinSelectedId(i) == null){
				return null;
			}
			temp += getSpinSelectedId(i)+"-";
		}
		return temp;
	}
	/** 获取完整的房间名 ***/
	public String getWholeRoomName(){
		return (String) sp_precinct_chooser.getSelectedItem() + "-" +(String) sp_building_chooser.getSelectedItem()
				+ "-" +(String) sp_floor_chooser.getSelectedItem()
				+ "-" +(String) sp_room_chooser.getSelectedItem();
		
	}
}
