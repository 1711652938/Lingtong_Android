package com.helloword.lingtong;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import cn.jpush.android.api.JPushInterface;

import com.helloword.lingtong.util.HttpUrl;

public class BaseActivity extends Activity implements HttpUrl,OnClickListener{
	private Toast toast;
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		JPushInterface.onResume(this);
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		JPushInterface.onPause(this);
	}
	public void ShowToast(String st){
		if(toast==null){
			toast=Toast.makeText(this,st, Toast.LENGTH_SHORT);
		}else{
			toast.setText(st);
		}
		toast.show();
	}
}
