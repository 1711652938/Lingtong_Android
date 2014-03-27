package com.helloword.lingtong;

import java.util.ArrayList;
import java.util.List;

import com.helloword.lingtong.R.id;
import com.helloword.lingtong.adapter.MessageAdapter;
import com.helloword.lingtong.adapter.SearchResultListAdapter;
import com.helloword.lingtong.model.SearchResultData;
import com.helloword.lingtong.model.SearchResultData.SearchResult;
import com.helloword.lingtong.util.DateUtil;
import com.helloword.lingtong.util.GetDataUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class MessageActivity extends BaseActivity {
	private List<TextView> tvList = new ArrayList<TextView>();
	private List<TextView> datList = new ArrayList<TextView>();
	private List<View> layList = new ArrayList<View>();
	private View day_lay;
	private View content_lay;
	private TextView first_day;
	private TextView first_weekday;
	private TextView first_num;
	private EditText editText;
	private TextView search_btn;
	private String[] weekdays = DateUtil.getWeekDays();
	private String[] days = DateUtil.getDays();
	private ListView msg_info_list;
	private List<SearchResult> slist;
	private MessageAdapter adapter;
	private int[] sizes = new int[] { 10, 16, 24, 30 };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initView();

	}

	

	public void initView() {
		setContentView(R.layout.activity_msg);
		((TextView) findViewById(R.id.tittle)).setText("灵通短讯");
		findViewById(R.id.back).setVisibility(View.GONE);
		findViewById(R.id.right_btn).setVisibility(View.VISIBLE);
		findViewById(R.id.right_btn).setBackgroundResource(R.drawable.setting);
		findViewById(R.id.right_btn).setOnClickListener(this);
		msg_info_list = (ListView) findViewById(R.id.msg_info_list);
		slist = new ArrayList<SearchResult>();
		adapter = new MessageAdapter(this, slist);
		msg_info_list.setAdapter(adapter);
		// findViewById(R.id.back).seton
		editText = (EditText) findViewById(R.id.search_edit);
		search_btn = (TextView) findViewById(R.id.search_btn);
		search_btn.setOnClickListener(this);
		for (int i = 1; i < 8; i++) {
			TextView tv = (TextView) findViewById(getResources().getIdentifier(
					"week_day" + i, "id", this.getPackageName()));
			tv.setText(weekdays[i - 1]);
			TextView tv1 = (TextView) findViewById(getResources()
					.getIdentifier("msg_time" + i, "id", this.getPackageName()));
			View view = findViewById(getResources().getIdentifier("lay" + i,
					"id", this.getPackageName()));
			tv1.setText(days[i - 1]);
			tvList.add(tv);
			datList.add(tv1);
			view.setTag((i - 1));
			view.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					int num = (Integer) arg0.getTag();
					day_lay.setVisibility(View.GONE);
					content_lay.setVisibility(View.VISIBLE);
					first_weekday.setText(weekdays[num]);
					first_day.setText(days[num]);
					findViewById(R.id.first_msg_re).setTag(days[num]);
					getData(days[num]);
				}
			});
			layList.add(view);
		}
		day_lay = findViewById(R.id.daylay);
		content_lay = findViewById(R.id.contentlay);
		findViewById(R.id.first_down_btn).setOnClickListener(this);
		findViewById(R.id.first_msg_re).setOnClickListener(this);
		first_weekday = (TextView) findViewById(R.id.first_week_day);
		first_day = (TextView) findViewById(R.id.first_msg_time);
		first_num = (TextView) findViewById(R.id.first_msg_num);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		switch (id) {
		case R.id.right_btn:
			startActivity(new Intent(this, SettingActivity.class));
			overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
			break;
		case R.id.first_down_btn:
			day_lay.setVisibility(View.VISIBLE);
			content_lay.setVisibility(View.GONE);
			break;
		case R.id.first_msg_re:
			getData((String) v.getTag());
			break;
		case R.id.search_btn:
			if (editText.getText().toString().equals("")) {
				ShowToast("搜索内容不能为空");
			} else {
				Intent intent = new Intent(this, SearchResultActivity.class);
				intent.putExtra("data", editText.getText().toString());
				startActivity(intent);
				overridePendingTransition(R.anim.in_from_right,
						R.anim.out_to_left);
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
			}
			break;
		}
	}

	public void getData(String day) {
		SearchResultData data = new SearchResultData();
		SearchResult result = data.new SearchResult();
		result.setContent("铜价大跌");
		result.setTime("2014-03-02");
		result.setType("广州现货铜");
		List<SearchResult> l = new ArrayList<SearchResultData.SearchResult>();
		l.add(result);
		l.add(result);
		l.add(result);
		l.add(result);
		l.add(result);
		l.add(result);
		l.add(result);
		l.add(result);
		slist.clear();
		adapter.setSize(sizes[((lingtongApplication) getApplication())
				.getUserData().getConfig().getSize() - 1]);
		slist.addAll(l);
		adapter.notifyDataSetChanged();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (adapter != null) {
			adapter.setSize(sizes[((lingtongApplication) getApplication())
					.getUserData().getConfig().getSize() - 1]);
			adapter.notifyDataSetInvalidated();
		}
	}

}
