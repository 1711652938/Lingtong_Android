package com.helloword.lingtong;

import java.util.ArrayList;
import java.util.List;

import com.helloword.lingtong.util.GetDataUtil;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * 登录
 * 
 * @author Administrator
 * 
 */
public class AboutActivity extends BaseActivity {
	private TextView title;
	private Button back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		initView();
	}

	private void initView() {
		title = (TextView) findViewById(R.id.tittle);
		title.setText("关于灵通资讯");
		back = (Button) findViewById(R.id.back);
		back.setOnClickListener(this);
		List<Object> objects = new ArrayList<Object>();
		objects.add("backgroup_image");
		GetDataUtil.getInstance(this).getData(GETSYSTEM, objects, true,
				true, new Handler() {

					@Override
					public void handleMessage(Message msg) {
						// TODO Auto-generated method stub
						super.handleMessage(msg);
					}

				});
	}

	@Override
	public void onClick(View v) {
		int vid = v.getId();
		if (vid == R.id.back) {
			this.finish();
			overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
		}

	}
}
