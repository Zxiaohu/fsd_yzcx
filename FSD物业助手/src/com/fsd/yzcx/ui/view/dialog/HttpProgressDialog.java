package com.fsd.yzcx.ui.view.dialog;

import com.fsd.yzcx.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;

/**
 * 自定义网络加载弹框
 * @author zxh
 *
 */
public class HttpProgressDialog extends DialogFragment {

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		
		//获取自定义布局
		View view = View.inflate(getActivity(), R.layout.http_progress, null);
		builder.setView(view);
		
		return builder.create();
	}
	
}
