package com.fsd.yzcx.tools.http;

public abstract class MyHttpListener {
	abstract public void finish(String response);
	public void onloading(long total, long current, boolean isUploading) {
	}
}
