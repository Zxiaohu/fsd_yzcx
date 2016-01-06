package com.fsd.yzcx.dao.user;

/**
 * UserInfo
 * @author zxh
 * 用户信息 
 * 2016年1月5日15:13:57
 */
public class UserInfo {
	/**
	 *  userinfo.asp 业主个人信息
	 *  
		对接参数 userid 登录帐号手机	
		返回 json 
		 score 积分 
		 photo 图像 
		 address 收货地址
	 * 
	 * */
	private String score;
	private String photo;
	private String address;
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	

}
