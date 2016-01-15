package com.fsd.yzcx.dao.user;

public enum Param {
	/**
	 * score 积分 
	photo 图像 
	address 收货地址 
	nickname 昵称 
	houseid，
	housename 
	 */
	UNAME("uname"),
	NICKNAME("nickname"),
	SCORE("score"),
	PHOTO("photo"),
	ADDRESS("address"),
	HOUSE_ID("houseid"),
	HOUSE_NAME("housename"),
	PASSWORD("password"),
	OLDPASSWORD("oldpassword"),
	DETAILS("details");
	private String name;
	private Param(String name){
		this.name=name;
	}
	public String getName(){
		return this.name;
	}
}
