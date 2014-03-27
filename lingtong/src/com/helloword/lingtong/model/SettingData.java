package com.helloword.lingtong.model;

import com.google.gson.annotations.SerializedName;

public class SettingData {
	@SerializedName("voice")
	private String voice;
	@SerializedName("font-size")
	private int size;
	@SerializedName("cache")
	private String cache;
	@SerializedName("channel")
	private String channel;
	public String getVoice() {
		return voice;
	}
	public void setVoice(String voice) {
		this.voice = voice;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public String getCache() {
		return cache;
	}
	public void setCache(String cache) {
		this.cache = cache;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	
}
