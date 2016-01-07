package com.fsd.yzcx.test;

import com.fsd.yzcx.dao.user.UserDao;
import com.fsd.yzcx.dao.user.UserDao.UserDaoListener;
import com.fsd.yzcx.tools.LogUtil;

import android.test.AndroidTestCase;

public class MyTest extends AndroidTestCase {


	//获取那个控件给它赋值登录测试
	protected void setUp() throws Exception {
		super.setUp();
		
	}

	//测试方法
	public void test(){
		UserDao dao = new UserDao(getContext());
		LogUtil.i("test", "单元测试我被执行了");
		dao.fetchUserInfo("15827249218", new UserDaoListener() {
			public void fetchUserInfo(String jsonUserInfo) {

				LogUtil.i("test", jsonUserInfo);

			}
		});
		LogUtil.i("test", "单元测试结束了");
		
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	//启动一个activity
}
