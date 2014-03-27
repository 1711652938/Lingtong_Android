package com.helloword.lingtong.util;

public interface HttpUrl {
	static final String http="http://pjl.500yun.pw/index.php/Api/";
	static final String LOGIN_URL=http+"login";
	static final int LOGIN=1;
	static final String LOGOUT_URL=http+"logout";
	static final int LOGOUT=2;
	static final String REG_URL=http+"reg";
	static final int REG=3;
	static final String GETCONFIG_URL=http+"getConfig";
	static final int GETCONFIG=4;
	static final String SAVECONFIG_URL=http+"saveConfig";
	static final int SAVECONFIG=5;
	static final String SEARCH_URL=http+"search";
	static final int SEARCH=6;
	static final String GETSYSTEM_URL=http+"getSystem";
	static final int GETSYSTEM=7;
	static final String GETARTICLEBYDATE_URL=http+"getArticleByDate";
	static final int GETARTICLEBYDATE=8;
	static final String SAVEOPINIONS_URL=http+"saveOpinions";
	static final int SAVEOPINIONS=9;
	static final String SETCHANNEL_URL=http+"setChannel";
	static final int SETCHANNEL=10;
	static final int GETCHANNEL=11;
	static final String GETCHANNEL_URL=http+"getChannel";
}
