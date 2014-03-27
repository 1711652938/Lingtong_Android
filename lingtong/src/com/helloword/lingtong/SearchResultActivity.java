package com.helloword.lingtong;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.helloword.lingtong.adapter.SearchResultListAdapter;
import com.helloword.lingtong.model.Result;
import com.helloword.lingtong.model.SearchResultData;
import com.helloword.lingtong.model.SearchResultData.SearchResult;
import com.helloword.lingtong.util.DateUtil;
import com.helloword.lingtong.util.GetDataUtil;
import com.helloword.lingtong.view.PullToRefreshView;
import com.helloword.lingtong.view.PullToRefreshView.OnFooterRefreshListener;
import com.helloword.lingtong.view.PullToRefreshView.OnHeaderRefreshListener;

public class SearchResultActivity extends BaseActivity implements OnHeaderRefreshListener,OnFooterRefreshListener{
	private TextView title;
	private Button back;
	private TextView search_btn;
	private EditText search_edit;
	private ListView search_rasult_list;
	private SearchResultListAdapter adapter;
	private List<SearchResult> slist;
	private PullToRefreshView pullview;
	private int oPage=1;
	private static int pagesize=10;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_result);
		initView();
	}

	private void initView() {
		title = (TextView) findViewById(R.id.tittle);
		title.setText("云端搜索");
		search_btn = (TextView) findViewById(R.id.search_btn);
		search_edit = (EditText) findViewById(R.id.search_edit);
		search_rasult_list = (ListView) findViewById(R.id.search_rasult_list);
		back = (Button) findViewById(R.id.back);
		back.setOnClickListener(this);
		search_btn.setOnClickListener(this);
		slist=new ArrayList<SearchResult>();
		adapter=new SearchResultListAdapter(this, slist);
		search_rasult_list=(ListView) findViewById(R.id.search_rasult_list);
		search_rasult_list.setAdapter(adapter);
		String temp=getIntent().getStringExtra("data");
		search_edit.setText(temp);
		search_edit.setSelection(temp.length());
		search_edit.clearFocus();
		getData(getIntent().getStringExtra("data"),1,oPage);
		pullview=(PullToRefreshView) findViewById(R.id.main_pull_refresh_view);
		pullview.setOnFooterRefreshListener(this);
		pullview.setOnHeaderRefreshListener(this);
	}

	public void getData(String st,final int mode,int page) {
		List<Object> list = new ArrayList<Object>();
		list.add(st);
		Log.e("TAG",page+"");
		list.add(page);
		list.add(pagesize);
		GetDataUtil.getInstance(this).getData(SEARCH, list, true, true,
				new Handler() {

					@Override
					public void handleMessage(Message msg) {
						// TODO Auto-generated method stub
						Result<SearchResultData> result=(Result<SearchResultData>) msg.obj;
						if(result.getCode().equals("0000")){
							if(mode==1){
								slist.clear();
							}
							if(result.getData()!=null){
								if(result.getData().getList()!=null){
									slist.addAll(result.getData().getList());
								}else{
									ShowToast("对不起，没有您要搜索的内容");
								}
							}else{
								if(mode==0&&slist.size()!=0){
									ShowToast("对不起，美誉下一页的内容了");
								}else{
									ShowToast("对不起，没有您要搜索的内容");
								}
							}
							adapter.notifyDataSetChanged();
						}
						pullview.onFooterRefreshComplete();
						if(mode==0){
							pullview.onHeaderRefreshComplete();
						}else{
							pullview.onHeaderRefreshComplete("更新于："+DateUtil.getTime());
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
		} else if (vid == R.id.search_btn) {
			if(search_edit.getText().toString().equals("")){
				ShowToast("搜索内容不能为空");
			}else{
				getData(search_edit.getText().toString(),1,pagesize);
			}
		}
	}

	@Override
	public void onFooterRefresh(PullToRefreshView view) {
		// TODO Auto-generated method stub
		getData(search_edit.getText().toString(),0,++oPage);
	}

	@Override
	public void onHeaderRefresh(PullToRefreshView view) {
		// TODO Auto-generated method stub
		getData(search_edit.getText().toString(),1,1);
	}
}
