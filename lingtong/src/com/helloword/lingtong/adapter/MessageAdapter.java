package com.helloword.lingtong.adapter;

import java.util.List;

import com.helloword.lingtong.R;
import com.helloword.lingtong.model.SearchResultData.SearchResult;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MessageAdapter extends BaseAdapter {
	private Context context;
	private List<SearchResult> list;
	private int size;
	public MessageAdapter(Context context, List<SearchResult> list) {
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View contentView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		holder = new ViewHolder();
		if (contentView == null) {
			ViewHolder t_hHolder = new ViewHolder();
			contentView = LayoutInflater.from(context).inflate(
					R.layout.adapter_message, null);
			t_hHolder.result_content = (TextView) contentView
					.findViewById(R.id.result_content);
			t_hHolder.post_time = (TextView) contentView
					.findViewById(R.id.post_time);
			t_hHolder.type = (TextView) contentView
					.findViewById(R.id.type);

			contentView.setTag(t_hHolder);
		}
		holder = (ViewHolder) contentView.getTag();
		holder.result_content.setText(list.get(position).getContent());
		holder.result_content.setTextSize(size);
		holder.post_time.setText(list.get(position).getTime());
		holder.type.setText(list.get(position).getType());
		if(position!=list.size()-1){
			contentView.setBackgroundResource(R.drawable.midd);
		}else{
			contentView.setBackgroundResource(R.drawable.bottom);
		}
		return contentView;

	}

	private class ViewHolder {
		TextView result_content;
		TextView post_time;
		TextView type;
	}
	
	public void setSize(int size){
		this.size=size;
	}
}
