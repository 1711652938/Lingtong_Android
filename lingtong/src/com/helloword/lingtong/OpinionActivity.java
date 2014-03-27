package com.helloword.lingtong;

import java.util.ArrayList;
import java.util.List;

import com.helloword.lingtong.model.Result;
import com.helloword.lingtong.util.GetDataUtil;

import android.app.Activity;
import android.content.Context;
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
 * 意见反馈页面
 * 
 * @author Administrator
 * 
 */
public class OpinionActivity extends BaseActivity {
	private TextView title;
	private Button back;
	private Button submit;
	private EditText opinion_edit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_opinion);
		initView();
	}

	private void initView() {
		title = (TextView) findViewById(R.id.tittle);
		title.setText("意见与投诉");

		opinion_edit = (EditText) findViewById(R.id.opinion_edit);
		submit = (Button) findViewById(R.id.submit);
		back = (Button) findViewById(R.id.back);
		back.setOnClickListener(this);
		submit.setOnClickListener(this);
		InputMethodManager imm = (InputMethodManager) this
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(opinion_edit, InputMethodManager.RESULT_SHOWN);
		imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
				InputMethodManager.HIDE_IMPLICIT_ONLY);
	}

	@Override
	public void onClick(View v) {
		int vid = v.getId();
		if (vid == R.id.back) {
			InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE); 
			imm.hideSoftInputFromWindow(opinion_edit.getWindowToken(), 0);
			this.finish();
			overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
		} else if (vid == R.id.submit) {
			if (opinion_edit.getText().toString().equals("")) {
				ShowToast("反馈意见不能为空");
			} else {
				List<Object> objects = new ArrayList<Object>();
				objects.add(((lingtongApplication) getApplication())
						.getUserData().getId());
				objects.add(opinion_edit.getText().toString());
				GetDataUtil.getInstance(this).getData(SAVEOPINIONS, objects,
						true, true, new Handler() {

							@Override
							public void handleMessage(Message msg) {
								// TODO Auto-generated method stub
								Result result = (Result) msg.obj;
								if (result.getCode().equals("0000")) {
									ShowToast("反馈成功，感谢您的建议");
									OpinionActivity.this.finish();
									overridePendingTransition(
											R.anim.in_from_left,
											R.anim.out_to_right);
								} else {
									ShowToast(result.getMsg());
								}

							}
						});
			}
		}

	}
}
