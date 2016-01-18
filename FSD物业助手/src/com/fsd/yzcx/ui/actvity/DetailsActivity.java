package com.fsd.yzcx.ui.actvity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.style.BulletSpan;
import android.util.DisplayMetrics;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.view.ViewGroup.LayoutParams;
import com.fsd.yzcx.R;
import com.fsd.yzcx.ui.actvity.base.BaseActivity;
import com.fsd.yzcx.ui.fragment.order.DetailsFragment;
import com.fsd.yzcx.ui.fragment.order.DetailsStateFragment;

public class DetailsActivity extends BaseActivity {
		
	private RadioGroup mRadioGroup;
	private RadioButton mRadio01;
	private RadioButton mRadio02;

	private ImageView mTabLine;// 指导线
	private int screenWidth;// 屏幕的宽度

	private ViewPager mViewPager;
	private MyFragmentAdapter mAdapter;
	private List<Fragment> mFragments = new ArrayList<Fragment>();
	
	private String complainid ;//订单的id
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details);
		
		complainid  = getIntent().getStringExtra("data");
			
		initViews();
		initEvent();
	}

	//初始化
	private void initViews() {
		mRadioGroup = (RadioGroup) findViewById(R.id.id_radioGroup);
		mRadio01 = (RadioButton) findViewById(R.id.id_tab1);
		mRadio02 = (RadioButton) findViewById(R.id.id_tab2);
		
		mTabLine = (ImageView) findViewById(R.id.id_tab_line);
		//获取屏幕的宽度  
		DisplayMetrics outMetrics=new DisplayMetrics();
		getWindow().getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
		screenWidth=outMetrics.widthPixels;
		
		//设置mTabLine宽度//获取控件的(注意：一定要用父控件的LayoutParams写LinearLayout.LayoutParams)
		LinearLayout.LayoutParams lp=(LinearLayout.LayoutParams) mTabLine.getLayoutParams();//获取控件的布局参数对象
		lp.width=screenWidth/2;
		mTabLine.setLayoutParams(lp); //设置该控件的layoutParams参数
		
		DetailsStateFragment detailsStateFragment = new DetailsStateFragment();
		
		DetailsFragment detailsFragment = new DetailsFragment();
		
		Bundle bundle = null;
		
		if(complainid != null){//
			bundle = new Bundle();
			bundle.putString("data", complainid);
		}
		if(bundle != null){
			detailsStateFragment.setArguments(bundle);
			detailsFragment.setArguments(bundle);
		}
		
		mFragments.add(detailsStateFragment);
		mFragments.add(detailsFragment);
		
		mViewPager=(ViewPager) findViewById(R.id.id_viewpager);
		mAdapter=new MyFragmentAdapter(getSupportFragmentManager(), mFragments);
		mViewPager.setAdapter(mAdapter);
	}

	private void initEvent() {
		mRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.id_tab1:
					mViewPager.setCurrentItem(0);// 选择某一页
					break;
				case R.id.id_tab2:
					mViewPager.setCurrentItem(1);
					break;
				}
			}
		});
		
		mViewPager.setOnPageChangeListener(new TabOnPageChangeListener());  
	}

	/**
	 * 页卡滑动改变事件  
	 */
	public class TabOnPageChangeListener implements OnPageChangeListener {

		/**
		 * 当滑动状态改变时调用  
		 * state=0的时候表示什么都没做，就是停在那
		 * state=1的时候表示正在滑动 
		 * state==2的时候表示滑动完毕了
		 */
		public void onPageScrollStateChanged(int state) {

		}

		/**
		 * 当前页面被滑动时调用   
		 * position:当前页面
		 * positionOffset:当前页面偏移的百分比
		 * positionOffsetPixels:当前页面偏移的像素位置  
		 */
		public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
			LinearLayout.LayoutParams lp=(LinearLayout.LayoutParams) mTabLine.getLayoutParams();
			 //获取组件距离左侧组件的距离  
			lp.leftMargin=(int) ((positionOffset+position)*screenWidth/2);
			mTabLine.setLayoutParams(lp);  
		}

		//当新的页面被选中时调用  
		public void onPageSelected(int position) {
			switch (position) {
			case 0:
				mRadio01.setChecked(true);    
				break;
			case 1:
				mRadio02.setChecked(true);    
				break;
			}
		}
	}
	
	
	
	/**
	 * 
	 * @param activity 需要跳转到本activity加载的方法
	 * @param data 需要携带的数据
	 */
	public static void slikMe(Activity activity,String data){
		Intent intent = new Intent(activity,DetailsActivity.class);
		
		if(data!=null){//有数据就填充
			intent.putExtra("data", data);
		}
		activity.startActivity(intent);
	}
	
	//fragment适配器
	public class MyFragmentAdapter extends FragmentPagerAdapter{

		private List<Fragment> mFragments;

		//构造器
		public MyFragmentAdapter(FragmentManager fm, List<Fragment> mFragments) {
			super(fm);
			this.mFragments = mFragments;
		}

		//获取item
		public Fragment getItem(int position) {
			return mFragments.get(position);
		}

		//获取数目
		public int getCount() {
			return mFragments.size();
		}
		
	}
}
