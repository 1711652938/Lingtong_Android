package com.helloword.lingtong.model;


import com.google.gson.annotations.SerializedName;

public class UserData {
	@SerializedName("id")
	private String id;
	@SerializedName("name")
	private String name;
	@SerializedName("type")
	private String type;
	@SerializedName("config")
	private Config setConfig;
	@SerializedName("vipline")
	private String vipline;
	@SerializedName("status")
	private int status;
	@SerializedName("company")
	private String company;
	@SerializedName("phone")
	private String phone;
	@SerializedName("user_vip")
	private int vip;

	
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public Config getConfig() {
		return setConfig;
	}


	public void setConfig(Config config) {
		this.setConfig = config;
	}


	public String getVipline() {
		return vipline;
	}


	public void setVipline(String vipline) {
		this.vipline = vipline;
	}


	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
	}


	public String getCompany() {
		return company;
	}


	public void setCompany(String company) {
		this.company = company;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public int getVip() {
		return vip;
	}


	public void setVip(int vip) {
		this.vip = vip;
	}


	
}
