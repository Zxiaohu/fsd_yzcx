package com.fsd.yzcx.actvity;
import java.util.ArrayList;
import java.util.List;
import com.fsd.yzcx.R;
import com.fsd.yzcx.actvity.base.BaseActivity;
import com.fsd.yzcx.adapter.CommPageAdapter;
import com.fsd.yzcx.animation.DepthPageTransformer;
import com.fsd.yzcx.pager.base.BasePager;
import com.fsd.yzcx.pager.base.imp.NormalLoginPager;
import com.fsd.yzcx.pager.base.imp.NormalLoginPager.MyOnclickimp;
import com.fsd.yzcx.pager.base.imp.RoomLoginPager;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.WindowManager;

public class LoginActivity extends BaseActivity {

	@ViewInject(R.id.vp_login)
	private ViewPager vp_login;//登录页的viewpager

	private List<BasePager> pagers= new ArrayList<BasePager>();; //pager集合

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_login);
		ViewUtils.inject(this);
		initData();
		//给viewpager设置数据
		setVPData();
	}

	private void initData() {
		NormalLoginPager normalLoginPager=new NormalLoginPager(this);
		//NormalLoginPager normalLoginPager=new NormalLoginPager(this);
		RoomLoginPager roomLoginPager = new RoomLoginPager(this);
		
		normalLoginPager.setImp(new MyOnclickimp() {
			public void onClick() {
				vp_login.setCurrentItem(1);
			}
		});
		
		//初始化pagers
		pagers.add(normalLoginPager);
		pagers.add(roomLoginPager);
	}

	private void setVPData() {

		vp_login.setCurrentItem(0);//设置当前页面
		pagers.get(0).initData();
		
		vp_login.setPageTransformer(true, new DepthPageTransformer());
		vp_login.setAdapter(new CommPageAdapter<BasePager>(pagers){
			public View getView(int position, List<BasePager> mDatas){
				return mDatas.get(position).mRootView;
			}});

		//给vp_login设置监听事件
		vp_login.setOnPageChangeListener(mypaechlis);
	}

	/**
	 * viewpager监听事件
	 */
	OnPageChangeListener mypaechlis= new OnPageChangeListener() {
		public void onPageSelected(int arg0) {
			pagers.get(arg0).initData();
			if(vp_login.getCurrentItem()==0){
				((RoomLoginPager)pagers.get(1)).tv_chooseroom.setClickable(false);
			}else{
				((RoomLoginPager)pagers.get(1)).tv_chooseroom.setClickable(true);
			}
		}
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}
		public void onPageScrollStateChanged(int arg0) {
			
		}
	};
	
	public void onBackPressed() {
		if(vp_login.getCurrentItem()==0){
			finish();
		}
		vp_login.setCurrentItem(0);

	}
}
