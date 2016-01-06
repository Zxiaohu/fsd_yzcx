package com.fsd.yzcx.model.yzinfo;

/**
 * 
 * @author zxh
 *
 */
public class YzInfo {
	/**
	 * houseid 房间信息id housename 房间信息名称 mastername 业主姓名 telnumber 业主电话

	 */
	private String houseid;
	private	String mastername;
	private String telnumber;
	private String housename;
	
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
	public String getMastername() {
		return mastername;
	}
	public void setMastername(String mastername) {
		this.mastername = mastername;
	}
	public String getTelnumber() {
		return telnumber;
	}
	public void setTelnumber(String telnumber) {
		this.telnumber = telnumber;
	}

	
}
