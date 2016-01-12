package com.fsd.yzcx.dao.user;

/**
 * UserInfo
 * @author zxh
 * 用户信息 
 * 2016年1月5日15:13:57
 */
public class UserInfo {
	/**
	score 积分 
	photo 图像 
	address 收货地址 
	nickname 昵称 
	houseid，
	housename 
	 * */
	private String score;
	private String photo;
	private String address;
	private String nickname;
	private String houseid;
	private String housename;
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
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getHouseid() {
		return houseid;
	}
	public void setHouseid(String houseid) {
		this.houseid = houseid;
	}
	public String getHousename() {
		return housename;
	}
	public void setHousename(String housename) {
		this.housename = housename;
	}
	
	
	

}
