package com.helloword.lingtong.model;

import java.io.Serializable;

import com.google.gson.annotations.SerializedName;

public class Result<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7141454293552363897L;
	@SerializedName("data")
	private T data;
	@SerializedName("code")
	private String code;
	@SerializedName("msg")
	private String msg;
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
