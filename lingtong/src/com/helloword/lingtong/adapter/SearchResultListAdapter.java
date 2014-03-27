package com.helloword.lingtong.adapter;

import java.util.List;

import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.helloword.lingtong.R;
import com.helloword.lingtong.model.MsgClassifyData;
import com.helloword.lingtong.model.SearchResultData;
import com.helloword.lingtong.model.SearchResultData.SearchResult;

public class SearchResultListAdapter extends BaseAdapter {
	private Context context;
	private List<SearchResult> list;

	public SearchResultListAdapter(Context context, List<SearchResult> list) {
		this.list = list;
		this.context = context;

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View contentView, ViewGroup parent) {
		ViewHolder holder;
		holder = new ViewHolder();
		if (contentView == null) {
			ViewHolder t_hHolder = new ViewHolder();
			contentView = LayoutInflater.from(context).inflate(
					R.layout.adapter_search_result, null);
			t_hHolder.result_content = (TextView) contentView
					.findViewById(R.id.result_content);
			t_hHolder.post_time = (TextView) contentView
					.findViewById(R.id.post_time);

			contentView.setTag(t_hHolder);
		}
		holder = (ViewHolder) contentView.getTag();
		holder.result_content.setText(list.get(position).getContent());
		holder.post_time.setText(list.get(position).getTime());

		return contentView;

	}

	private class ViewHolder {
		TextView result_content;
		TextView post_time;
	}

}
