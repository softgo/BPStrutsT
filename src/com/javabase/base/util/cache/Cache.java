package com.javabase.base.util.cache;

/**
 * 自定义的cache
 * 
 * @author admin
 *
 */
public class Cache {

	private String key;// 缓存ID
	private Object value;// 缓存数据
	private boolean isLogin = false; //是否登录 true:已经登录,flase,没有登录.
	private long timeOut;// 更新时间
	private boolean expired; // 是否终止

	public Cache() {
		super();
	}

	public Cache(String key, Object value) {
		this.key = key;
		this.value = value;
	}
	
	public Cache(String key, Object value,boolean isLogin) {
		this.key = key;
		this.value = value;
		this.isLogin = isLogin;
	}
	
	
	public Cache(String key, Object value,boolean isLogin, long timeOut) {
		this.key = key;
		this.value = value;
		this.isLogin = isLogin;
		this.timeOut = timeOut;
	}
	
	public Cache(String key, Object value,boolean isLogin, long timeOut, boolean expired) {
		this.key = key;
		this.value = value;
		this.isLogin =isLogin;
		this.timeOut = timeOut;
		this.expired = expired;
	}

	public String getKey() {
		return key;
	}

	public long getTimeOut() {
		return timeOut;
	}

	public Object getValue() {
		return value;
	}

	public void setKey(String string) {
		key = string;
	}

	public void setTimeOut(long l) {
		timeOut = l;
	}

	public void setValue(Object object) {
		value = object;
	}

	public boolean isExpired() {
		return expired;
	}

	public void setExpired(boolean b) {
		expired = b;
	}
	
	public boolean getIsLogin() {
		return isLogin;
	}

	public void setIsLogin(boolean isLogin) {
		this.isLogin = isLogin;
	}
}
