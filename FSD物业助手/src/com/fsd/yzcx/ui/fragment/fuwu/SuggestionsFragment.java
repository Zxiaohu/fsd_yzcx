package com.fsd.yzcx.ui.fragment.fuwu;

import java.io.FileNotFoundException;
import java.util.List;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fsd.yzcx.R;
import com.fsd.yzcx.tools.LogUtil;
import com.fsd.yzcx.ui.adapter.CommonAdapter;
import com.fsd.yzcx.ui.adapter.ViewHolder;
import com.fsd.yzcx.ui.fragment.base.BaseFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.ViewUtils;

public class SuggestionsFragment extends BaseFragment {

	
	private Spinner sp_fuwu_item;//投诉
	private TextView tv_photo;//选择照片或拍照
	private ImageView iv_content;//选择照片或拍照
	

	public View initView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mRootView = inflater.inflate(R.layout.fragment_suggestion, null);
		
		//初始化当前界面
		initCurrentView();
		
		return mRootView;
	}



	/**
	 * 初始化当前view
	 */
	private void initCurrentView() {
		
		//获取子服务项信息
				getSubServices();
		
		sp_fuwu_item = (Spinner) mRootView.findViewById(R.id.sp_fuwu_item);
		tv_photo =(TextView) mRootView.findViewById(R.id.tv_photo);
		iv_content =(ImageView) mRootView.findViewById(R.id.iv_content);
		
		
		myConfigInfoAdapter = initMyadapter();
		
		//设置数据适配器
		if(myConfigInfoAdapter!=null){
			sp_fuwu_item.setAdapter(myConfigInfoAdapter);
		}
		
	}

	

	public void initData(Bundle bundle) {
		//设置事件
		//图片选择事件
		initSelectPhoto();
	}


	
	
	android.view.View.OnClickListener zxh= new android.view.View.OnClickListener() {
		public void onClick(View v) {
			CharSequence[] items = {"相机", "相册"};    
			   new AlertDialog.Builder(mActivity)  
			    .setTitle("选择图片来源")  
			    .setItems(items, new OnClickListener() {  
			        public void onClick(DialogInterface dialog, int which) {  
			            if( which == 1 ){  
			                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);  
			                intent.addCategory(Intent.CATEGORY_OPENABLE);  
			                intent.setType("image/*");  
			                startActivityForResult(Intent.createChooser(intent, "选择图片"), 1);   
			            }else{  
			                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);    
			                startActivityForResult(intent, 2);    
			            }  
			        }  
			    })  
			    .create().show(); 
			
		}
	};
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {  
	    super.onActivityResult(requestCode, resultCode, data);  
	    if(requestCode==1 ){  
	        //选择图片  
	        Uri uri = data.getData();   
	        ContentResolver cr = mActivity.getContentResolver();   
	        Bitmap bmp = null;
			try {          	
	            if(bmp != null)//如果不释放的话，不断取图片，将会内存不够  
	                bmp.recycle();  
	            bmp = BitmapFactory.decodeStream(cr.openInputStream(uri));  
	        } catch (FileNotFoundException e) {  
	            // TODO Auto-generated catch block  
	            e.printStackTrace();  
	        }  
	        System.out.println("the bmp toString: " + bmp);  
	        iv_content.setImageBitmap(bmp);  
	    }else if(requestCode==2){
	    	  //选择图片  
	        Uri uri = data.getData();   
	        ContentResolver cr = mActivity.getContentResolver();   
	        Bitmap bmp = null;
			try {          	
	            if(bmp != null)//如果不释放的话，不断取图片，将会内存不够  
	                bmp.recycle();  
	            bmp = BitmapFactory.decodeStream(cr.openInputStream(uri));  
	        } catch (FileNotFoundException e) {  
	            // TODO Auto-generated catch block  
	            e.printStackTrace();  
	        }  
	        System.out.println("the bmp toString: " + bmp);  
	        iv_content.setImageBitmap(bmp); 
	    }
	    else{  
	        Toast.makeText(mActivity, "请重新选择图片", Toast.LENGTH_SHORT).show();  
	    }  
	          
	}  
	
	
	private void initSelectPhoto() {
		
		tv_photo.setOnClickListener(zxh);
	
	}

}
