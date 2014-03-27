package com.helloword.lingtong.model;

import com.google.gson.annotations.SerializedName;

public class ChannelData {
	@SerializedName("channel")
	private String channel;
	@SerializedName("status")
	private String status;
	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
