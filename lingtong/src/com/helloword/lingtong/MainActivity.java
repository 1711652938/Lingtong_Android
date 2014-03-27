package com.helloword.lingtong;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

import com.google.gson.Gson;
import com.helloword.lingtong.model.Config;
import com.helloword.lingtong.model.Result;
import com.helloword.lingtong.model.UserData;
import com.helloword.lingtong.util.GetDataUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * 登录
 * 
 * @author Administrator
 * 
 */
public class MainActivity extends BaseActivity {
	private EditText search_edit;
	private TextView search_btn;
	private Button login_btn;
	private Button register_btn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
	}

	private void initView() {
		search_edit = (EditText) findViewById(R.id.search_edit);
		search_btn = (TextView) findViewById(R.id.search_btn);
		login_btn = (Button) findViewById(R.id.login_btn);
		search_btn.setOnClickListener(this);
		login_btn.setOnClickListener(this);
		register_btn = (Button) findViewById(R.id.register_btn);
		register_btn.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		int vid = v.getId();
		if (vid == R.id.search_btn) {
			if (search_edit.getText().toString().equals("")) {
				ShowToast("搜索内容不能为空");
			} else {
				Intent intent = new Intent(this, SearchResultActivity.class);
				intent.putExtra("data", search_edit.getText().toString());
				startActivity(intent);
				overridePendingTransition(R.anim.in_from_right,
						R.anim.out_to_left);
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(search_edit.getWindowToken(), 0);
			}
		} else if (vid == R.id.login_btn) {
			getLogin();
		} else if (vid == R.id.register_btn) {
			startActivity(new Intent(this, RegisterActivity.class));
			// 设置切换动画，从右边进入，左边退出

			overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
		}
	}

	public void saveLogin(String uname, String pass) {
		Editor editor = getSharedPreferences(getPackageName(), 0).edit();
		editor.putString("uname", uname);
		editor.putString("pass", pass);
		editor.commit();
	}

	public void getLogin() {
		String _uname = getSharedPreferences(getPackageName(), 0).getString(
				"uname", "");
		String _pass = getSharedPreferences(getPackageName(), 0).getString(
				"pass", "");
		if (!_uname.equals("") && !_pass.equals("")) {
			login(_uname,_pass);
		}else{
			startActivity(new Intent(this, LoginActivity.class));
			// 设置切换动画，从右边进入，左边退出

			overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
		}
	}

	public void login(final String uname, final String pass) {
		List<Object> list = new ArrayList<Object>();
		list.add(uname);
		list.add(pass);
		GetDataUtil.getInstance(this).getData(LOGIN, list, true, new Handler() {

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				Result<UserData> userData = (Result<UserData>) msg.obj;
				if (userData.getCode().equals("0000")) {
					UserData data=userData.getData();
					String st=getSharedPreferences(getPackageName(), 0).getString("id", "");
					if(!st.equals("")){
						String temp=getSharedPreferences(getPackageName(), 0).getString(st, "");
						if(!temp.equals("")){
							Config config=new Gson().fromJson(temp, Config.class);
							data.setConfig(config);
						}
					}else{
						Config fing=new Config();
						fing.setSize(1);
						data.setConfig(fing);
					}
					lingtongApplication application = (lingtongApplication) getApplication();
					application.setUserData(data);
					startActivity(new Intent(MainActivity.this,
							MessageActivity.class));
					saveLogin(uname, pass);
					// 设置切换动画，从右边进入，左边退出
					JPushInterface.setAlias(MainActivity.this, "27", new TagAliasCallback() {
						@Override
						public void gotResult(int arg0, String arg1,
								Set<String> arg2) {
							// TODO Auto-generated method stub

						}
					});
					overridePendingTransition(R.anim.in_from_right,
							R.anim.out_to_left);
				} else {
					ShowToast(userData.getMsg());
				}
			}
		});
	}
}
