package com.helloword.lingtong.util;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.text.SpannableString;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.helloword.lingtong.model.Result;

public class JsonUtil {
	public static JsonUtil instance;
	private Gson gson;

	private JsonUtil() {
		gson = new GsonBuilder().create();
	}

	public static JsonUtil getInstance() {
		if (instance == null) {
			synchronized (JsonUtil.class) {
				if (instance == null) {
					instance = new JsonUtil();
				}
			}
		}
		return instance;
	}
	public List<NameValuePair> LoginMessageList(List<Object> list)  {
		List<NameValuePair> nameValuePairs=new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("username", getString(list.get(0))));
		nameValuePairs.add(new BasicNameValuePair("password", getString(list.get(1))));
		return nameValuePairs;
	}
	
	public String LoginMessage(List<Object> list)  {
		JsonObject jsonObject=new JsonObject();
		jsonObject.addProperty("username", getString(list.get(0)));
		jsonObject.addProperty("password", getString(list.get(1)));
		return jsonObject.toString();
	}
	

	
	public String RegMessage(List<Object> list)  {
		JsonObject jsonObject=new JsonObject();
		jsonObject.addProperty("username", getString(list.get(0)));
		jsonObject.addProperty("password", getString(list.get(1)));
		jsonObject.addProperty("company", getString(list.get(2)));
		jsonObject.addProperty("phone", getString(list.get(3)));
		jsonObject.addProperty("type", getString(list.get(4)));
		return jsonObject.toString();
	}
	
	public List<NameValuePair> RegMessageList(List<Object> list) throws JSONException  {
		List<NameValuePair> nameValuePairs=new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("username", getString(list.get(0))));
		nameValuePairs.add(new BasicNameValuePair("company", getString(list.get(1))));
		nameValuePairs.add(new BasicNameValuePair("phone", getString(list.get(2))));
		return nameValuePairs;
	}

	public List<NameValuePair> GETConfigMessageList(List<Object> list) throws JSONException  {
		List<NameValuePair> nameValuePairs=new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("id", getString(list.get(0))));
		return nameValuePairs;
	}
	
	public List<NameValuePair> SaveConfigMessageList(List<Object> list) throws JSONException  {
		List<NameValuePair> nameValuePairs=new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("id", getString(list.get(0))));
		nameValuePairs.add(new BasicNameValuePair("key", "synchronization"));
		nameValuePairs.add(new BasicNameValuePair("synchronization", getString(list.get(1))));
		return nameValuePairs;
	}
	
	public List<NameValuePair> SaveSingerConfigMessageList(List<Object> list) throws JSONException  {
		List<NameValuePair> nameValuePairs=new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("id", getString(list.get(0))));
		nameValuePairs.add(new BasicNameValuePair("key", getString(list.get(1))));
		nameValuePairs.add(new BasicNameValuePair(getString(list.get(1)), getString(list.get(2))));
		return nameValuePairs;
	}
	
	public List<NameValuePair> SaveOpiniosMessageList(List<Object> list) throws JSONException  {
		List<NameValuePair> nameValuePairs=new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("content", getString(list.get(1))));
		nameValuePairs.add(new BasicNameValuePair("from", getString(list.get(0))));
		return nameValuePairs;
	}
	
	public List<NameValuePair> GETArticleByDateMessageList(List<Object> list) throws JSONException  {
		List<NameValuePair> nameValuePairs=new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("date", getString(list.get(0))));
		return nameValuePairs;
	}
	
	public List<NameValuePair> SearchMessageList(List<Object> list) throws JSONException  {
		List<NameValuePair> nameValuePairs=new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("keyword", getString(list.get(0))));
		nameValuePairs.add(new BasicNameValuePair("pageNum", getString(list.get(1))));
		nameValuePairs.add(new BasicNameValuePair("listRows", getString(list.get(2))));
		return nameValuePairs;
	}
	
	public List<NameValuePair> SystemMessageList(List<Object> list) throws JSONException  {
		List<NameValuePair> nameValuePairs=new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("key", getString(list.get(0))));
		return nameValuePairs;
	}
	public List<NameValuePair> SetChannelMessageList(List<Object> list) throws JSONException  {
		List<NameValuePair> nameValuePairs=new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("id", getString(list.get(0))));
		nameValuePairs.add(new BasicNameValuePair(getString(list.get(1)), getString(list.get(2))));
		return nameValuePairs;
	}
	
	public List<NameValuePair> GetChannelMessageList(List<Object> list) throws JSONException  {
		List<NameValuePair> nameValuePairs=new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("id", getString(list.get(0))));
		return nameValuePairs;
	}
	
	/**
	 * 解析服务器返回JSON字符串，得到Result<T>对象
	 * 
	 * @param <T> 泛型方法定义
	 * @param json 返回JSON字符�?
	 * @param type 类型
	 * @return
	 */
	public  <T> Result<T> getResult(String json, Type type) {
		return gson.fromJson(json, type);
	}
	
	public static SimpleDateFormat dateFormat  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static SimpleDateFormat shortDateFormat  = new SimpleDateFormat("yyyy-MM-dd");
	public static SimpleDateFormat timeFormat  = new SimpleDateFormat("HH:mm:ss");
	public static SimpleDateFormat shortDateFormat2  = new SimpleDateFormat("yyyy/MM/dd");
	
	public static Double getDouble(Object obj){
		Double res = 0.0;
		if(obj != null){
			String doubleString=obj.toString();
			try{
				if(doubleString.indexOf(",")!=-1){
					doubleString=doubleString.replaceAll(",", "");
				}
				res = Double.valueOf(doubleString);
			} catch (Exception e){
			}
		}
		return res;
	}
	
	public static String getDoubleString(Object obj){
		String res = "";
		if(obj != null){
			String doubleString=obj.toString();
			try{
				if(doubleString.indexOf("E")!=-1){
					
						java.text.NumberFormat nf = java.text.NumberFormat.getInstance();
						nf.setGroupingUsed(false); 
						doubleString=nf.format(obj);
						
					
				}
				res =doubleString;
			} catch (Exception e){
			}
		}
		return res;
	}
	
	public static Float getFloat(Object obj){
		Float res =0.0f;
		if(obj != null){
			String floatString=obj.toString();
			if("".equals(floatString)){
				return res;
			}
			try{
				res = Float.parseFloat(floatString);
			} catch (Exception e){
				e.printStackTrace();
			}
		}
		return res;
	}
	
	public static Integer getInteger(Object obj){
		Integer res = 0;
		if(obj != null){
			try{
				res = Integer.valueOf(obj + "");
			} catch (Exception e){
			}
		}
		return res;
	}
	
	public static Long getLong(Object obj){
		Long res = 0L;
		if(obj != null){
			try{
				res = Long.valueOf(obj + "");
			} catch (Exception e){
			}
		}
		return res;
	}
	
	public static String getString(Object obj){
		String res = "";
		if(obj != null){
			try{
				res = obj + "";
			} catch (Exception e){
			}
		}
		return res;
	}
	
	public static SpannableString getSpannableString(Object obj){
		SpannableString res=null ;
		if(obj != null){
			try{
				res = (SpannableString)obj;
			} catch (Exception e){
			}
		}
		return res;
	}
	
	public static String getTimeString(Object obj){
		String date = getString(obj);
		if(date != null && !date.equals("")){
			try{
				if(obj instanceof Date){
					date=timeFormat.format(obj);
				} else {
					
					Date d = new Date(Date.parse(date));
					date = timeFormat.format(d);
				}
			} catch(Exception ex){
				ex.printStackTrace();
			}
		}
		return date;
	}
	public static String getShortDateString(Object obj){
		String date = getString(obj);
		if(date != null && !date.equals("")){
			try{
				if(obj instanceof Date){
					date=shortDateFormat.format(obj);
				} else {
					Date d = new Date(Date.parse(date));
					date = shortDateFormat.format(d);
				}
			} catch(Exception ex){
				ex.printStackTrace();
			}
		}
		return date;
	}
	public static String getShortDateString2(Object obj){
		String date = getString(obj);
		if(date != null && !date.equals("")){
			try{
				if(obj instanceof Date){
					date=shortDateFormat2.format(obj);
				} else {
					Date d = new Date(Date.parse(date));
					date = shortDateFormat.format(d);
				}
			} catch(Exception ex){
				ex.printStackTrace();
			}
		}
		return date;
	}
	public static String getDateString(Object obj){
		String date = getString(obj);
		if(date != null && !date.equals("")){
			try{
				if(obj instanceof Date){
					date=dateFormat.format(obj);
				} else {
					date = date.replace('T', ' ');
					Date d = new Date(Date.parse(date));
					date = dateFormat.format(d);
				}
			} catch(Exception ex){
				ex.printStackTrace();
			}
		}
		return date;
	}
	public static Date getDate(Object obj){
		Date res = new Date();
		if(obj != null){
			try{
				if(obj instanceof Date) return (Date)obj;
				String dateString = getString(obj);
				dateString = dateString.replace('T', ' ');
				res = dateFormat.parse(dateString);
			} catch (Exception e){
				e.printStackTrace();
			}
		}
		return res;
	}
	
	public static Date getShortDate(Object obj){
		Date res = new Date();
		if(obj != null){
			try{
				if(obj instanceof Date) return (Date)obj;
				String dateString = getString(obj);
				dateString = dateString.replace('T', ' ');
				res = shortDateFormat.parse(dateString);
			} catch (Exception e){
				e.printStackTrace();
			}
		}
		return res;
	}
	
	public static Boolean getBoolean(Object obj){
		Boolean res = false;
		if(obj != null){
			try{
				res = Boolean.parseBoolean(obj.toString());
			} catch (Exception e){
				
			}
		}
		return res;
	}

	public static Boolean IsEmpty(String v){
		boolean flag = true;
		if(v != null && !"".equals(v)){
			flag = false;
		}
		return flag;
	}
}
