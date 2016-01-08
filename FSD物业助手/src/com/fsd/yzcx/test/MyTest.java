package com.fsd.yzcx.test;

import com.fsd.yzcx.dao.db.SharedPfDao;
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
		
		LogUtil.e("test",SharedPfDao.getAll());
		
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	//启动一个activity
}
