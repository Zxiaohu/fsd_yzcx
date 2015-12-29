package com.fsd.yzcx.global;
 

/**
 * 
 * @author 业主信息
 *
 */
public class YzInfo {
	/**
	 * 返回json flag info提示信息 登录状态 username 业主编号 uname 业主姓名
	 * */
	public  static String USER_NAME;//业主的id
	public  static String UNAME;//业主的姓名
	
	public  int flag;
	public 	String info;
	public 	String username;//用户的电话号码
	public  String nickname;//用户的昵称
	@Override
	public String toString() {
		return "YzInfo [flag=" + flag + ", info=" + info + ", username="
				+ username + ", nickname=" + nickname + "]";
	}
	
	
}
