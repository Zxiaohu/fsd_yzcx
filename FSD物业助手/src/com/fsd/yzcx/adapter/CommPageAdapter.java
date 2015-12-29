package com.fsd.yzcx.adapter;

import java.util.List;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public abstract class CommPageAdapter<T> extends PagerAdapter {
	
	private List<T> mDatas;
	public CommPageAdapter(List<T> datas){
		this.mDatas=datas;
	}
	public int getCount() {
		return mDatas.size();
	}
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0==arg1;
	}
	public Object instantiateItem(ViewGroup container, int position) {
		
		View view =getView(position,mDatas);
		container.addView(view);
		return view;
	}
	
	/**
	 * 
	 * @param container 容器
	 * @param position 
	 * @param mDatas
	 * @return
	 */
	public abstract View getView(int position, List<T> mDatas);
	
	public void destroyItem(ViewGroup container, int position, Object object) {
		super.destroyItem(container, position, object);
		container.removeView((View) object);
	}

}
