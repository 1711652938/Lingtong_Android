package com.helloword.lingtong.model;

import com.google.gson.annotations.SerializedName;

public class SystemData {
	@SerializedName("id")
	private String id;
	@SerializedName("value")
	private String value;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
}
