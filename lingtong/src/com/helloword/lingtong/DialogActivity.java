package com.helloword.lingtong;

import android.app.Activity;
import android.os.Bundle;

public class DialogActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initView();
	}
	
	public void initView(){
		setContentView(R.layout.dialog);
	}
}
