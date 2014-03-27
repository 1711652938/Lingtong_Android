package com.helloword.lingtong.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import junit.runner.Version;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import com.helloword.lingtong.R;
import com.helloword.lingtong.model.ChannelData;
import com.helloword.lingtong.model.Config;
import com.helloword.lingtong.model.Result;
import com.helloword.lingtong.model.SearchResultData;
import com.helloword.lingtong.model.SettingData;
import com.helloword.lingtong.model.UserData;
import com.helloword.lingtong.view.CustomProgressDialog;
import android.content.Context;
import android.net.wifi.p2p.WifiP2pManager.Channel;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

/**
 * @author MuFe
 */

public class GetDataUtil implements HttpUrl {
	private static final int TIME_OUT = 10 * 1000;
	private CustomProgressDialog progressDlg;
	private Handler handler;
	private ExecutorService threadPoolExecutor;
	private static GetDataUtil instance = null;
	private Context context;
	private DefaultHttpClient client;
	private JsonUtil jsonUtil;

	public static GetDataUtil getInstance(Context context) {
		if (instance == null) {
			synchronized (GetDataUtil.class) {
				if (null == instance) {
					instance = new GetDataUtil(context);
				}
			}
		}
		instance.context = context;
		return instance;
	}

	private void CreaterDialog() {
		progressDlg = new CustomProgressDialog(context,
				R.style.CustomProgressDialog);
		progressDlg.setCancelable(true);
	}

	public void getData(int urlFlag, List<Object> list, boolean isPost,
			boolean iSTips, Handler handler) {
		MyThread myThread = new MyThread(urlFlag, list, handler, iSTips, isPost);
		threadPoolExecutor.execute(myThread);
	}

	public void getData(int urlFlag, List<Object> list, boolean isPost,
			Handler handler) {
		MyThread myThread = new MyThread(urlFlag, list, handler, true, isPost);
		threadPoolExecutor.execute(myThread);
	}

	public void getData(int urlFlag, List<Object> list, Handler handler) {
		MyThread myThread = new MyThread(urlFlag, list, handler, false, false);
		threadPoolExecutor.execute(myThread);
	}

	public void getData(int urlFlag, List<Object> list) {
		MyThread myThread = new MyThread(urlFlag, list, null, false, true);
		threadPoolExecutor.execute(myThread);
	}

	private GetDataUtil(Context context) {
		this.context = context;
		client = new DefaultHttpClient();
		client.getParams().setParameter(
				CoreConnectionPNames.CONNECTION_TIMEOUT, TIME_OUT);
		threadPoolExecutor = Executors.newFixedThreadPool(2);
		jsonUtil = JsonUtil.getInstance();
		if (Looper.myLooper() != null) {
			handler = new Handler(Looper.myLooper()) {
				@Override
				public void handleMessage(Message msg) {
					// TODO Auto-generated method stub
					if (msg.what == 1) {
						CreaterDialog();
						progressDlg.show();
					} else if (msg.what == 0) {
						if (progressDlg.isShowing()) {
							progressDlg.OwnDismiss();
						}
					} else {
						CreaterDialog();
					}

				}
			};
		} else {
			handler = new Handler() {
				@Override
				public void handleMessage(Message msg) {
					// TODO Auto-generated method stub
					if (msg.what == 1) {
						CreaterDialog();
						progressDlg.show();
					} else if (msg.what == 0) {
						if (progressDlg.isShowing()) {
							progressDlg.OwnDismiss();
						}
					} else {
						CreaterDialog();
					}

				}
			};
		}

	}

	// private String DoingBackGround_GET(List<NameValuePair> nameValuePairs,
	// String url) {
	// HttpUriRequest request = null;
	// int connCount = 0;
	// StringBuffer result = null;
	// BufferedReader br = null;
	// HttpEntity entity = null;
	// boolean isok = false;
	// while (!isok && connCount < 1) {
	// try {
	// StringBuilder sb = new StringBuilder(url);
	// if (nameValuePairs != null && !nameValuePairs.isEmpty()) {
	// sb.append('?');
	// for (int i = 0; i < nameValuePairs.size(); i++) {
	// sb.append(nameValuePairs.get(i).getName())
	// .append('=')
	// .append(URLEncoder.encode(nameValuePairs.get(i)
	// .getValue(), HTTP.UTF_8)).append('&');
	// }
	// sb.deleteCharAt(sb.length() - 1);
	// }
	//
	// request = new HttpGet(sb.toString());
	// HttpResponse resp = client.execute(request);
	// int code = resp.getStatusLine().getStatusCode();
	// if (code == HttpStatus.SC_OK) {
	// entity = resp.getEntity();
	// }
	// br = new BufferedReader(new InputStreamReader(
	// entity.getContent(), "UTF-8"));
	// result = new StringBuffer();
	// String read = br.readLine();
	// while (read != null) {
	// result.append(read);
	// read = br.readLine();
	// }
	// br.close();
	// br = null;
	// isok = true;
	// } catch (Exception e) {
	// // TODO: handle except
	// e.printStackTrace();
	// } finally {
	// try {
	// if (br != null) {
	// br.close();
	// br = null;
	// }
	// } catch (Exception e2) {
	// // TODO: handle exception
	// e2.printStackTrace();
	// }
	//
	// }
	// connCount++;
	// }
	// if (!isok || "".equals(result) || result == null) {
	// return null;
	// }
	// String resulttamp = null;
	// try {
	// resulttamp = new String(result.toString().getBytes("UTF-8"));
	//
	// } catch (Exception e) {
	// // TODO: handle exception
	// e.printStackTrace();
	// return null;
	// }
	// return resulttamp;
	// }

	@SuppressWarnings("unused")
	private String DoingBackGround(List<NameValuePair> list, String st,
			String url) {
		HttpPost post;
		int connCount = 0;
		StringBuffer result = null;
		BufferedReader br = null;
		HttpEntity entity = null;
		boolean isok = false;
		while (!isok && connCount < 1) {
			try {
				post = new HttpPost(url);
				result = new StringBuffer();
				if (list == null) {
					post.setEntity(new StringEntity(st, HTTP.UTF_8));
				} else {
					post.setEntity(new UrlEncodedFormEntity(list, HTTP.UTF_8));
				}
				HttpResponse resp = client.execute(post);
				int code = resp.getStatusLine().getStatusCode();

				if (code == HttpStatus.SC_OK) {
					entity = resp.getEntity();
				}
				br = new BufferedReader(new InputStreamReader(
						entity.getContent(), "UTF-8"));

				String read = br.readLine();
				while (read != null) {
					result.append(read);
					read = br.readLine();
				}
				br.close();
				br = null;
				isok = true;

			} catch (Exception e) {
				// TODO: handle except
				e.printStackTrace();
				result.append("{\"code\":0001,\"msg\":\"网络不给力\"}");
			} finally {
				try {
					if (br != null) {
						br.close();
						br = null;
					}
				} catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
				}

			}
			connCount++;
		}
		String resulttamp = null;
		if ("".equals("") || null == result) {
			resulttamp = ("{\"code\":0001,\"msg\":\"网络不给力\"}");
		}
		try {
			resulttamp = new String(result.toString().getBytes("UTF-8"));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "{\"code\":0001,\"msg\":" + e.getMessage() + "}";
		}
		return resulttamp;
	}

	class MyThread extends Thread {
		private String st;
		private String url;
		private Handler myhandler;
		private boolean isTips;
		private boolean isPost;
		private int _urlFlag;
		private List<NameValuePair> nameValuePairs;

		public MyThread(int urlFlag, List<Object> list, Handler handler,
				boolean isTips, boolean isPost) {
			_urlFlag = urlFlag;
			try {
				switch (urlFlag) {
				case LOGIN:
					url = LOGIN_URL;
					nameValuePairs = jsonUtil.LoginMessageList(list);
					break;
				case REG:
					url = REG_URL;
					nameValuePairs = jsonUtil.RegMessageList(list);
					break;
				case LOGOUT:
					url = LOGOUT_URL;
					nameValuePairs = new ArrayList<NameValuePair>();
					break;
				case GETCONFIG:
					url = GETCONFIG_URL;
					nameValuePairs = jsonUtil.GETConfigMessageList(list);
					break;
				case SAVECONFIG:
					url = SAVECONFIG_URL;
					nameValuePairs = jsonUtil.SaveConfigMessageList(list);
					break;
				case SAVEOPINIONS:
					url = SAVEOPINIONS_URL;
					nameValuePairs = jsonUtil.SaveOpiniosMessageList(list);
					break;
				case GETARTICLEBYDATE:
					url = GETARTICLEBYDATE_URL;
					nameValuePairs = jsonUtil.GETArticleByDateMessageList(list);
					break;
				case SEARCH:
					url = SEARCH_URL;
					nameValuePairs = jsonUtil.SearchMessageList(list);
					break;
				case GETSYSTEM:
					url = GETSYSTEM_URL;
					nameValuePairs = jsonUtil.SystemMessageList(list);
					break;
				case SETCHANNEL:
					url = SETCHANNEL_URL;
					nameValuePairs = jsonUtil.SetChannelMessageList(list);
					break;
				case GETCHANNEL:
					url = GETCHANNEL_URL;
					nameValuePairs = jsonUtil.GetChannelMessageList(list);
					break;	
				}
			} catch (Exception e) {

			}
			this.isPost = isPost;
			this.isTips = isTips;
			if (isTips) {
				instance.handler.sendEmptyMessage(1);
			}
			this.myhandler = handler;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			String result = "";
			if (isPost) {
				if (st == null) {
					result = DoingBackGround(nameValuePairs, st, url);
				} else {
					result = DoingBackGround(null, st, url);
				}
			} else {
				// stDoingBackGround(nameValuePairs, url);
			}
			Message msg = new Message();
			Log.e("TAG", result);

			Type type = new com.google.gson.reflect.TypeToken<Result>() {
			}.getType();
			Result result2;
			try {
				switch (_urlFlag) {
				case LOGIN:
					type = new com.google.gson.reflect.TypeToken<Result<UserData>>() {
					}.getType();
					Result<UserData> userData = jsonUtil
							.getResult(result, type);
					msg.obj = userData;
					break;
				case REG:
					result2 = jsonUtil.getResult(result, type);
					msg.obj = result2;
					break;
				case LOGOUT:
					result2 = jsonUtil.getResult(result, type);
					msg.obj = result2;
					break;
				case GETCONFIG:
					type = new com.google.gson.reflect.TypeToken<Result<Config>>() {
					}.getType();
					Result<Config> result3 = jsonUtil.getResult(result, type);
					msg.obj = result3;
					break;
				case SAVECONFIG:
					result2 = jsonUtil.getResult(result, type);
					msg.obj = result2;
					break;
				case SAVEOPINIONS:
					result2 = jsonUtil.getResult(result, type);
					msg.obj = result2;
					break;
				case SEARCH:
					type = new com.google.gson.reflect.TypeToken<Result<SearchResultData>>() {
					}.getType();
					Result<SearchResultData> result4 = jsonUtil.getResult(
							result, type);
					msg.obj = result4;
					break;
				case SETCHANNEL:
					result2 = jsonUtil.getResult(result, type);
					msg.obj = result2;
					break;
				case GETCHANNEL:
					type = new com.google.gson.reflect.TypeToken<Result<List<ChannelData>>>() {
					}.getType();
					Result<List<ChannelData>> result5 = jsonUtil.getResult(
							result, type);
					msg.obj = result5;
					break;
				// case GETSYSTEM:
				// if()
				// type = new
				// com.google.gson.reflect.TypeToken<Result<Version>>() {
				// }.getType();
				// Result<SearchResultData> result4=jsonUtil.getResult(result,
				// type);
				// msg.obj = result4;
				// break;
				}
			} catch (Exception e) {
			
			}
			if(msg.obj==null){
				result2 = new Result();
				result2.setCode("0001");
				result2.setMsg("数据返回出错，请检查网络");
				msg.obj = result2;
			}
			if (isTips) {
				handler.sendEmptyMessage(0);
			}
			msg.what = 0;
			if (myhandler != null) {
				try {
					myhandler.sendMessage(msg);
				} catch (Exception e) {
				}
			}
		}
	}

}
