package com.helloword.lingtong;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.helloword.lingtong.model.Config;
import com.helloword.lingtong.model.Result;
import com.helloword.lingtong.model.UserData;
import com.helloword.lingtong.util.GetDataUtil;
import com.helloword.lingtong.view.MyDialog;
import com.helloword.lingtong.view.SlipButton;
import com.helloword.lingtong.view.MyDialog.OnChangeListener;
import com.helloword.lingtong.view.SlipButton.OnChangedListener;

import android.animation.AnimatorSet.Builder;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 登录
 * 
 * @author Administrator
 * 
 */
public class SettingActivity extends BaseActivity {
	private TextView title;
	private TextView account;
	private TextView push_sund;
	private TextView push_classify;
	private TextView font_size;
	private TextView msg_save;
	private TextView about_our;
	private TextView opinion;
	private TextView check_new;// 检查更新
	private Button back;
	private Button cancel_login;
	private SlipButton slipButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		initView();
	}

	private void initView() {
		title = (TextView) findViewById(R.id.tittle);
		account = (TextView) findViewById(R.id.account);
		slipButton = (SlipButton) findViewById(R.id.slipbuttons);
		slipButton.setChecked(false);
		slipButton.SetOnChangedListener("", new OnChangedListener() {
			
			@Override
			public void OnChanged(String strName, boolean CheckState) {
				// TODO Auto-generated method stub
				if(CheckState){
					setSystem(1, 2);
				}else{
					setSystem(0, 2);
				}
				
			}
		});
		push_sund = (TextView) findViewById(R.id.push_sound);
		push_classify = (TextView) findViewById(R.id.push_classify);
		font_size = (TextView) findViewById(R.id.font_size);
		msg_save = (TextView) findViewById(R.id.msg_save);
		about_our = (TextView) findViewById(R.id.about_our);
		opinion = (TextView) findViewById(R.id.opinion);
		check_new = (TextView) findViewById(R.id.check_new);
		title.setText("应用设置");
		back = (Button) findViewById(R.id.back);
		cancel_login = (Button) findViewById(R.id.cancel_login);
		cancel_login.setOnClickListener(this);
		back.setOnClickListener(this);
		account.setOnClickListener(this);
		push_sund.setOnClickListener(this);
		push_classify.setOnClickListener(this);
		font_size.setOnClickListener(this);
		msg_save.setOnClickListener(this);
		about_our.setOnClickListener(this);
		opinion.setOnClickListener(this);
		check_new.setOnClickListener(this);
		List<Object> objects = new ArrayList<Object>();
		objects.add(((lingtongApplication) getApplication()).getUserData()
				.getId());
		GetDataUtil.getInstance(this).getData(GETCONFIG, objects, true, true,
				new Handler() {
					@Override
					public void handleMessage(Message msg) {
						// TODO Auto-generated method stub
						UserData data = ((lingtongApplication) getApplication())
								.getUserData();
						Result<Config> result = (Result<Config>) msg.obj;
						if (result.getCode().equals("0000")) {
							Config config =((lingtongApplication)getApplication()).getUserData().getConfig();
							if (config != null) {
								data.setConfig(config);
								if (config.getVoice() == 1) {
									slipButton.setChecked(true);
								} else {
									slipButton.setChecked(false);
								}
							}else{
								data.setConfig(result.getData());
							}
							((lingtongApplication) getApplication())
									.setUserData(data);
							account.setText(data.getName());
						}
					}

				});
	}

	@Override
	public void onClick(View v) {
		int vid = v.getId();
		if (vid == R.id.back) {
			this.finish();
			overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
		} else if (vid == R.id.check_new) {
			List<Object> objects = new ArrayList<Object>();
			objects.add("version");
			GetDataUtil.getInstance(this).getData(GETSYSTEM, objects, true,
					true, new Handler() {

						@Override
						public void handleMessage(Message msg) {
							// TODO Auto-generated method stub
							super.handleMessage(msg);
						}

					});

		} else if (vid == R.id.push_classify) {
			startActivity(new Intent(this, SettingMsgClassifyActivity.class));
			// 设置切换动画，从右边进入，左边退出
			overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
		} else if (vid == R.id.font_size) {
			MyDialog dialog = new MyDialog(this, R.style.CustomProgressDialog);
			dialog.setOnChangeListener(new OnChangeListener() {
				@Override
				public void setOnChange(int mode, int st) {
					// TODO Auto-generated method stub
					setSystem(st, mode);
				}
			});
			if (((lingtongApplication) getApplication()).getUserData()
					.getConfig() == null) {
				dialog.setData(0, 1);
			} else {
				dialog.setData(0, ((lingtongApplication) getApplication())
						.getUserData().getConfig().getSize());
			}
			dialog.show();
		} else if (vid == R.id.msg_save) {
			MyDialog dialog = new MyDialog(this, R.style.CustomProgressDialog);
			dialog.setOnChangeListener(new OnChangeListener() {
				@Override
				public void setOnChange(int mode, int st) {
					// TODO Auto-generated method stub
					setSystem(st, mode);
				}
			});
			if (((lingtongApplication) getApplication()).getUserData()
					.getConfig() == null) {
				dialog.setData(1, 1);
			} else {
				dialog.setData(1, ((lingtongApplication) getApplication())
						.getUserData().getConfig().getCache());
			}
			dialog.show();
		} else if (vid == R.id.about_our) {
			startActivity(new Intent(this, AboutActivity.class));
			// 设置切换动画，从右边进入，左边退出
			overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
		} else if (vid == R.id.opinion) {
			startActivity(new Intent(this, OpinionActivity.class));
			// 设置切换动画，从右边进入，左边退出
			overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
		} else if (vid == R.id.cancel_login) {
			GetDataUtil.getInstance(this).getData(LOGOUT, null, true,
					new Handler() {
						@Override
						public void handleMessage(Message msg) {
							// TODO Auto-generated method stub
							Result result = (Result) msg.obj;
							if (result.getCode().equals("0000")) {
								ShowToast("注销成功");
								Editor editor = getSharedPreferences(
										getPackageName(), 0).edit();
								editor.putString("uname", "");
								editor.putString("pass", "");
								editor.commit();
								startActivity(new Intent(SettingActivity.this,
										LoginActivity.class));
								SettingActivity.this.finish();
								overridePendingTransition(R.anim.in_from_left,
										R.anim.out_to_right);
							}
						}
					});

		}
	}

	public void setSystem(int data, int mode) {
		//List<Object> objects = new ArrayList<Object>();
		Config config = ((lingtongApplication) getApplication())
				.getUserData().getConfig();
//		objects.add(((lingtongApplication) getApplication()).getUserData()
//				.getId());
		final Config _config = new Config();
		if (config != null) {
			if (mode == 0) {
				config.setSize(data);
			} else if (mode == 1) {
				config.setCache(data);
			} else {
				config.setVoice(data);
			}
			((lingtongApplication) getApplication())
			.getUserData().setConfig(config);
			saveConfig(new Gson().toJson(config));
		} else {
			if (mode == 0) {
				_config.setSize(data);
			} else if (mode == 1) {
				_config.setCache(data);
			} else {
				_config.setVoice(data);
			}
			((lingtongApplication) getApplication())
			.getUserData().setConfig(_config);
			saveConfig(new Gson().toJson(_config));
		}
		
//		GetDataUtil.getInstance(SettingActivity.this).getData(SAVECONFIG,
//				objects, true, true, new Handler() {
//					@Override
//					public void handleMessage(Message msg) {
//						// TODO Auto-generated method stub
//						Result result = (Result) msg.obj;
//						if (result.getCode().equals("0000")) {
//							UserData data = ((lingtongApplication) getApplication())
//									.getUserData();
//							if (data.getConfig() == null) {
//								data.setConfig(_config);
//							} else {
//								data.setConfig(config);
//							}
//							((lingtongApplication) getApplication())
//									.setUserData(data);
//							ShowToast("修改用户设置成功");
//						}else{
//							ShowToast(result.getMsg());
//						}
//					}
//				});
	}
	
	public void saveConfig(String con){
		Editor editor=getSharedPreferences(getPackageName(), 0).edit();
		editor.putString("id",((lingtongApplication) getApplication()).getUserData().getId());
		editor.putString(((lingtongApplication) getApplication()).getUserData().getId(), con);
		editor.commit();
	}
}
