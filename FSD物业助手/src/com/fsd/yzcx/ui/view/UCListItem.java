package com.fsd.yzcx.ui.view;

import com.fsd.yzcx.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * UCListItem
 * @author zxh
 * 用户中心下的条目选项
 */
public class UCListItem extends LinearLayout {

	private View mRootView;
	@ViewInject(R.id.tv_title)
	private TextView tv_title;
	@ViewInject(R.id.iv_content)
	private ImageView iv_content;
	@ViewInject(R.id.iv_title)
	private ImageView iv_title;
	@ViewInject(R.id.ly_content)
	private LinearLayout ly_content;
	@ViewInject(R.id.tv_content)
	private TextView tv_content;
	
	String mString;
	
	public UCListItem(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray typeArray = context.obtainStyledAttributes(attrs,R.styleable.zxh); 
		mString = typeArray.getString(R.styleable.zxh_text);
		
		initViews();
	}
	public UCListItem(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public UCListItem(Context context) {
		super(context);
		initViews();
	}

	private void initViews() {
		mRootView = View.inflate(getContext(), R.layout.uc_list_item, this);
		ViewUtils.inject(this,mRootView);
		if(mString!=null){
			tv_title.setText(mString);
		}
	}

	/**
	 * 
	 * @param iconID
	 */
	public void setIVtitle(int iconID){
		iv_title.setVisibility(View.VISIBLE);
		iv_title.setImageResource(iconID);
	
	}
	/**
	 * 设置标题
	 * @param title
	 */
	public void setTitle(String title){
		tv_title.setText(title);
	}

	public void setIvContent(int iconID){
		iv_content.setImageResource(iconID);
	}

	public void setTvContent(String content){
		
		tv_content.setVisibility(View.VISIBLE);
		
		tv_content.setText(content);
		
	}
	
	/**
	 * 点击监听事件
	 * @param listener
	 */
	public void setOnClickListener(final MyOnClickListener listener){
		ly_content.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				listener.onClick(v);
			}
		});
	}

	public interface MyOnClickListener{
		void onClick(View v);
	}

}
