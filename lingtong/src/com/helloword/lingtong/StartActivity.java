package com.helloword.lingtong;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

public class StartActivity extends BaseActivity{
	private Handler handler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			startActivity(new Intent(StartActivity.this, MainActivity.class));
			StartActivity.this.finish();
			overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
		}
		
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		ImageView imageView=new ImageView(this);
		imageView.setBackgroundResource(R.drawable.start);
		setContentView(imageView);
		handler.sendEmptyMessageDelayed(0, 2000);
	}

}
