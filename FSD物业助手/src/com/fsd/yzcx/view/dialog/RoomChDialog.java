package com.fsd.yzcx.view.dialog;

import com.fsd.yzcx.R;
import com.fsd.yzcx.tools.SystemTools;
import com.fsd.yzcx.view.RoomInfoChooser;
import com.fsd.yzcx.view.RoomInfoChooser.AllSelectedListener;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
/**
 * 
 * @author zxh
 * RoomChDialog
 */
public class RoomChDialog extends DialogFragment {

	//定义接口
	private MyDialogItf myDialogItf;//我的对话框点击之后的操作
	

	public void setItf(MyDialogItf myDialogItf){
		this.myDialogItf=myDialogItf;
	}
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		//获取自定义布局
		LayoutInflater inflater = getActivity().getLayoutInflater();  
		View view = inflater.inflate(R.layout.dialog_basic, null);
		final TextView tv_title=(TextView) view.findViewById(R.id.dialog_title);

		final RoomInfoChooser room_chooser = (RoomInfoChooser) view.findViewById(R.id.room_chooser);
		room_chooser.initSpinner(view);

		//房间选择器的监听
		room_chooser.setAllSelectedListener(new AllSelectedListener() {

			public void onAllSelected(String num, String roomName) {
				if(0!=room_chooser.getSpinByIndex(3).getSelectedItemPosition()){
					tv_title.setText(roomName);
					//yz_num=num;
				}
			}
		});


		builder.setView(view).setNegativeButton("关闭", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				//
			}
		}).setPositiveButton("确认", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {

				if(!(tv_title.getText().toString().equals("选择房号"))){
					String yz_num;//用户编号
					String string1=room_chooser.getSpinSelectedId(0);
					String string2=room_chooser.getSpinSelectedId(1);
					String string3=room_chooser.getSpinSelectedId(2);
					String string4=room_chooser.getSpinSelectedId(3);
					
					yz_num=string1+"-"+string2+"-"+string3+"-"+string4;
					
					myDialogItf.onOk(yz_num);
					
				}else{
					SystemTools.showToastInfo(getActivity(), "请填写完整信息",3000, 2);
				}
			}
		});	

		return builder.create();
	}

	/**
	 * 点击事件的接口
	 * @author zxh
	 * 
	 */
	public interface MyDialogItf{
		/**
		 * 点击确认之后的操作
		 */
		void onOk(String yz_num);

		/**
		 * 
		 */
	}
}
