package com.fsd.yzcx.test;

import com.fsd.yzcx.dao.fuwu.FuwuDao;
import com.fsd.yzcx.dao.fuwu.FuwuDao.MyFWdaoListener;
import com.fsd.yzcx.dao.user.UserDao;
import com.fsd.yzcx.dao.user.UserDao.UserDaoListener;
import com.fsd.yzcx.tools.DataTools;
import com.fsd.yzcx.tools.LogUtil;

import android.test.AndroidTestCase;

public class MyTest extends AndroidTestCase {

	FuwuDao fuwuDao ;
	private String 傻逼;
	private String 逗逼;
	//获取那个控件给它赋值登录测试
	protected void setUp() throws Exception {
		super.setUp();
		傻逼 = "sb";
		逗逼 = "db";
	}

	//测试方法
	public void test(){
//		fuwuDao.pullPaiGong(
//				"0003|A|1|101",
//				"请叫我zx", 
//				"13871520805",
//				"大门坏了", 
//				"service03",
//				"subservice03", new MyFWdaoListener() {
//					public void fetchInfo(String response) {
//						LogUtil.i("test", response);
//					}
//				});
//		
		
		//String json=DataTools.mStrArr2Json(new String[]{"你好啊","为什么","你好"},"arrname","key");
		
		LogUtil.i("test", 逗逼+傻逼);
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	//启动一个activity
}
