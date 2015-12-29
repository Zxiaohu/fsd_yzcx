package com.fsd.yzcx.model;

/**
 * 业主信息
 * @author Administrator
 *
 */
public class OwnerInfo {

	private String houseid;
	private String housename;
	private String mastername;
	private String telnumber;
	
	public OwnerInfo() {
		super();
		// TODO Auto-generated constructor stub
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

	@Override
	public String toString() {
		return "OwnerInfo [houseid=" + houseid + ", housename=" + housename
				+ ", mastername=" + mastername + ", telnumber=" + telnumber
				+ "]";
	}
}
