package com.helloword.lingtong;

import java.util.ArrayList;
import java.util.List;

import com.helloword.lingtong.model.Result;
import com.helloword.lingtong.util.GetDataUtil;

import android.app.Activity;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 登录
 * 
 * @author Administrator
 * 
 */
public class RegisterActivity extends BaseActivity  {
	private EditText uname;
	private EditText company;
	private EditText pnum;
	private Button verify_btn;// 验证手机
	private TextView content;// 特别声明
	private TextView lt_content;// 灵通特别声明
	private TextView title;
	private Button back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		initView();
	}

	private void initView() {

		uname = (EditText) findViewById(R.id.uname_edit);
		company = (EditText) findViewById(R.id.conpany_edit);
		pnum = (EditText) findViewById(R.id.pnum_edit);
		
		uname.clearFocus();
		company.clearFocus();
		pnum.clearFocus();
		verify_btn = (Button) findViewById(R.id.verify_btn);
		content = (TextView) findViewById(R.id.statement_content);
		lt_content = (TextView) findViewById(R.id.lt_statement_content);
		title = (TextView) findViewById(R.id.tittle);
		title.setText("注册");

		back = (Button) findViewById(R.id.back);
		back.setOnClickListener(this);
		verify_btn.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		int vid = v.getId();
		if (vid == R.id.verify_btn) {
			if(Check()){
				List<Object> list=new ArrayList<Object>();
				list.add(uname.getText().toString());
				list.add(company.getText().toString());
				list.add(pnum.getText().toString());
				GetDataUtil.getInstance(this).getData(REG, list, true, new Handler(){

					@Override
					public void handleMessage(Message msg) {
						// TODO Auto-generated method stub
						if(msg.obj!=null){
							Result result=(Result) msg.obj;
							if(result.getCode().equals("0000")){
								ShowToast("注册成功");
								Editor edit=getSharedPreferences(getPackageName(), 0).edit();
								edit.putString("uname", "");
								edit.putString("pass","");
								edit.commit();
								RegisterActivity.this.finish();
							}else{
								ShowToast(result.getMsg());
							}
						}
					}
					
				});
			}
		} else if (vid == R.id.back) {
			this.finish();
			overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
		}
	}
	
	public boolean Check(){
		if(uname.getText().toString().equals("")){
			ShowToast("用户名不能为空");
			return false;
		}else if(company.getText().toString().equals("")){
			ShowToast("公司名字不能为空");
		}else if(pnum.getText().toString().equals("")){
			ShowToast("电话号码不能为空");
		}
		return true;
	}
}
