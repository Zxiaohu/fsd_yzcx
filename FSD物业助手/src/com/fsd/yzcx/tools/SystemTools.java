package com.fsd.yzcx.tools;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import com.fsd.yzcx.R;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;
/**
 * SystemTools
 * @author zxh
 * 系统相关工具类
 * 1.获取应用的版本号
 * 2.自定义toast信息提示
 */

public class SystemTools {
	public final static String TAG="----system---test-----";
	
	public static String getAppVersionName(Context context) {  
		String versionName = "";  
		try {  
			// ---get the package info---  
			PackageManager pm = context.getPackageManager();  
			PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);  
			versionName = pi.versionName;  
			int versioncode = pi.versionCode;
			if (versionName == null || versionName.length() <= 0) {  
				return "";  
			}  
		} catch (Exception e) {  
			Log.e("VersionInfo", "Exception", e);  
		}  
		return versionName;  
	}  
	/**
	 * 
	 * @param context 上下文
	 * @param content 要显示的内容
	 * @param time   显示的时间
	 * @param type 显示的类型 1是提示，2是警告
	 */
	public static void showToastInfo(Context context,String content,int time,int type){
		View toastRoot=null;
		Toast toast=new Toast(context);
		if(type==1){
			toastRoot =View.inflate(context,R.layout.toast_layout_info, null);
			toast.setView(toastRoot);		
			TextView tv=(TextView)toastRoot.findViewById(R.id.tv_toast_info);  
			tv.setText(content); 
		}else{
			toastRoot =View.inflate(context,R.layout.toast_layout_danger, null);
			toast.setView(toastRoot);		
			TextView tv=(TextView)toastRoot.findViewById(R.id.tv_toast_danger);  
			tv.setText(content); 
		}
		toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
		toast.setDuration(time);
		toast.show(); 
	}
	public static void connectFail(Context context){
		showToastInfo(context, "网络连接失败", Toast.LENGTH_SHORT, 1);
	}
	
	//获取当前时间
	public static String ShowNowTime(){

		//获取当前系统时间
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		
		return df.format(date);
	}
	

	/**
	 * 
	 * @param context
	 * @param DateFormat 时间格式
	 * @param myDateListener 处理返回的时间字符串的接口
	 */
	public static void setDate(final Context context,
			final String DateFormat,
			final MyDateListener myDateListener){
		Date now = new Date(); 
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");//可以方便地修改日期格式


		String hehe = dateFormat.format( now ); 
	
		Calendar c = Calendar.getInstance();//可以对每个时间域单独修改

		 


		int year = c.get(Calendar.YEAR); 
		int month = c.get(Calendar.MONTH); 
		int date = c.get(Calendar.DATE); 
		
		DatePickerDialog datePicker=new DatePickerDialog(context, new OnDateSetListener() {

			public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) {

				Calendar calendar = Calendar.getInstance();
				calendar.set(year, monthOfYear, dayOfMonth);
				SimpleDateFormat format = new SimpleDateFormat(
						DateFormat);
				
				String dateInfo=format.format(calendar.getTime());
				myDateListener.afterSetDate(dateInfo);
				SystemTools.showToastInfo(context, dateInfo, 3000, 1);
				
				
			}

		}, year, month, date);
		datePicker.show();
		
	}
	public interface MyDateListener{
		public void afterSetDate(String dateinfo);
	}
}
