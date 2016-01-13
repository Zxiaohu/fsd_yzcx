package com.fsd.yzcx.ui.view.dialog;
import com.fsd.yzcx.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class TipDialog extends DialogFragment implements OnClickListener{


	private View view;
	private TextView tv_content;
	private Button btn_ok;
	private Button btn_cancel;
	private callBackOk backOk;
	private CharSequence tipContent;

	/**
	 * 
	 * @param backOk 回调函数
	 * @param tipContent 提示信息
	 */
	//设置监听事件
	public void setCallBackOK(callBackOk backOk,CharSequence tipContent){
		this.backOk=backOk;
		this.tipContent=tipContent;
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//初始化控件
		view = View.inflate(getActivity(), R.layout.dialog_tip, null);
		tv_content = (TextView) view.findViewById(R.id.tv_content);
		btn_ok = (Button) view.findViewById(R.id.btn_ok);
		btn_cancel = (Button) view.findViewById(R.id.btn_cancel);

		tv_content.setText(tipContent);
		
		//添加事件
		btn_ok.setOnClickListener(this);
		btn_cancel.setOnClickListener(this);
	}
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setView(view);			
		return builder.create();
	}
	
	/**
	 * 设置提示内容
	 * @param content
	 */
	public void setContent(String content){
		tv_content.setText(content);
	}
	
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_cancel://取消

			this.dismiss();
			break;
		case R.id.btn_ok://确认
			//来，搞一个回调
			backOk.onOkClick();
			
			break;
		default:
			break;
		}
	}

	public interface callBackOk{
		public void onOkClick();
	}
	
}
