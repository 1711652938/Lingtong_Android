package com.helloword.lingtong.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.SimpleFormatter;

public class DateUtil {
	private static SimpleDateFormat simpleFormatter=new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat simpleFormatter1=new SimpleDateFormat("MM-dd hh:mm");
	public static String[] getDays(){
		Calendar calendar=Calendar.getInstance();
		String []temp=new String[7];
		for(int i=0;i<7;i++){
			temp[i]=getDay(calendar.getTime());
			calendar.add(Calendar.DATE, -1);
		}
		
		return temp;
	}
	
	public static String getTime(){
		return simpleFormatter1.format(new Date());
	}
	private static String getDay(Date date){
		return simpleFormatter.format(date);
	}
	public static String[] getWeekDays(){
		int i=Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
		String []temp=new String[7];
		temp[0]="今天";
		for(int j=1;j<7;j++){
			temp[j]=getWeekDay(i-j);
		}
		return temp;
	}
	
	private static String getWeekDay(int weekday){
		String st="";
		switch((weekday+7)%7){
		case 2:st="星期一";break;
		case 3:st="星期二";break;
		case 4:st="星期三";break;
		case 5:st="星期四";break;
		case 6:st="星期五";break;
		case 0:st="星期六";break;
		case 1:st="星期天";break;
		}
		return st;
	}
} 
