package com.helloword.lingtong.model;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Config {
	public Config(Config config){
		this.cache=config.cache;
		this.size=config.size;
		this.voice=config.voice;
	}
	public Config(){
		
	}
	@SerializedName("voice")
	private int voice=0;
	@SerializedName("font-size")
	private int size=1;
	@SerializedName("cache")
	private int cache=1;
	
	
	private transient List<ChannelData> list;
	
	
	public int getVoice() {
		return voice;
	}
	public void setVoice(int voice) {
		this.voice = voice;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public int getCache() {
		return cache;
	}
	public void setCache(int cache) {
		this.cache = cache;
	}
	public List<ChannelData> getList() {
		return list;
	}
	public void setList(List<ChannelData> list) {
		this.list = list;
	}

	
}