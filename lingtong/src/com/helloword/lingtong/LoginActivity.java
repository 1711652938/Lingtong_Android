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
import android.content.Intent;
import android.content.SharedPreferences.Editor;
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
public class LoginActivity extends BaseActivity {
	private EditText uname;
	private EditText password;
	private Button login_btn;
	private Button register_btn;
	private TextView title;
	private Button back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		initView();
	
	}

	private void initView() {
		uname = (EditText) findViewById(R.id.uname_edit);
		password = (EditText) findViewById(R.id.password_edit);
		login_btn = (Button) findViewById(R.id.login_btn);
		title = (TextView) findViewById(R.id.tittle);
		title.setText("登录");
		login_btn.setOnClickListener(this);
		register_btn = (Button) findViewById(R.id.register_btn);
		register_btn.setOnClickListener(this);
		back = (Button) findViewById(R.id.back);
		back.setOnClickListener(this);
		getLogin();
	}

	@Override
	public void onClick(View v) {
		int vid = v.getId();
		if (vid == R.id.login_btn) {
			login();
		} else if (vid == R.id.register_btn) {
			startActivity(new Intent(this, RegisterActivity.class));
			// 设置切换动画，从右边进入，左边退出

			overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
		} else if (vid == R.id.back) {
			this.finish();
			overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
		}

	}

	public boolean check() {
		if (uname.getText().toString().equals("")) {
			ShowToast("用户名不能为空");
			return false;
		} else if (password.getText().toString().equals("")) {
			ShowToast("密码不能为空");
			return false;
		}
		return true;
	}
	
	public void login(){
		if (check()) {
			List<Object> list = new ArrayList<Object>();
			list.add(uname.getText().toString());
			list.add(password.getText().toString());
			GetDataUtil.getInstance(this).getData(LOGIN, list, true,
					new Handler() {

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
								startActivity(new Intent(
										LoginActivity.this,
										MessageActivity.class));
								saveLogin(uname.getText().toString(),password.getText().toString());
								// 设置切换动画，从右边进入，左边退出
								JPushInterface.setAlias(LoginActivity.this,"27", new TagAliasCallback() {
									@Override
									public void gotResult(int arg0, String arg1, Set<String> arg2) {
										// TODO Auto-generated method stub

									}
								});
								overridePendingTransition(
										R.anim.in_from_right,
										R.anim.out_to_left);
								LoginActivity.this.finish();
							} else {
								ShowToast(userData.getMsg());
							}
						}
					});

		}
	}
	public void saveLogin(String uname,String pass){
		Editor editor=getSharedPreferences(getPackageName(), 0).edit();
		editor.putString("uname", uname);
		editor.putString("pass", pass);
		editor.commit();
	}
	
	public void getLogin(){
		String _uname=getSharedPreferences(getPackageName(), 0).getString("uname", "");
		String _pass=getSharedPreferences(getPackageName(), 0).getString("pass", "");
		if(!_uname.equals("")&&!_pass.equals("")){
			uname.setText(_uname);
			password.setText(_pass);
			login();
		}
	}
}
