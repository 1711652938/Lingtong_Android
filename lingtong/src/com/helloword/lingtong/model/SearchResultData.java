package com.helloword.lingtong.model;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class SearchResultData {
	@SerializedName("list")
	private List<SearchResult> list;
	
	
	public List<SearchResult> getList() {
		return list;
	}


	public void setList(List<SearchResult> list) {
		this.list = list;
	}


	public class SearchResult{
		public SearchResult(){
			
		}
		@SerializedName("id")
		private String id;
		@SerializedName("content")
		private String content;
		@SerializedName("dateline")
		private String time;
		@SerializedName("type")
		private String type;
		@SerializedName("region")
		private String region;
		@SerializedName("division")
		private String division;
		@SerializedName("cate")
		private String cate;
		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public String getTime() {
			return time;
		}

		public void setTime(String time) {
			this.time = time;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getRegion() {
			return region;
		}

		public void setRegion(String region) {
			this.region = region;
		}

		public String getDivision() {
			return division;
		}

		public void setDivision(String division) {
			this.division = division;
		}

		public String getCate() {
			return cate;
		}

		public void setCate(String cate) {
			this.cate = cate;
		}
	}
}
