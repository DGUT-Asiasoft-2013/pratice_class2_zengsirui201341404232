package com.example.helloworld.entity;

import java.net.CookieManager;
import java.net.CookiePolicy;

import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class Server {
	static OkHttpClient okHttpClient;
	
	static
	{
		CookieManager cookieManager=new CookieManager();
		cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
		
		okHttpClient=new OkHttpClient
				.Builder()
				.cookieJar(new JavaNetCookieJar(cookieManager))
				.build();
	}

	public static OkHttpClient getOkHttpClient() {
		return okHttpClient;
	}
	
	/*
	 * 设置方法，封装网址，使得每次调用网址都只需要添加后缀即可
	 */
	public static Request.Builder requestuildApi(String api)
	{
		return new Request.Builder().url("http://172.27.0.34:8080/membercenter/api/"+api);
		
	}
}
