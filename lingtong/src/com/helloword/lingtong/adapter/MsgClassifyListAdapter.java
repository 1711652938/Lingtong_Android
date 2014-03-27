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
import com.helloword.lingtong.view.SlipButton;
import com.helloword.lingtong.view.SlipButton.OnChangedListener;

public class MsgClassifyListAdapter extends BaseAdapter {
	private Context context;
	private List<MsgClassifyData> list;
	private AdapterSlipOnChangeListener listener;
	public MsgClassifyListAdapter(Context context, List<MsgClassifyData> list) {
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
					R.layout.adapter_msglist, null);
			t_hHolder.msg_name = (TextView) contentView
					.findViewById(R.id.msg_name);
			t_hHolder.slipButton=(SlipButton) contentView.findViewById(R.id.slipbuttons);
			contentView.setTag(t_hHolder);
		}
		holder = (ViewHolder) contentView.getTag();
		//
		holder.msg_name.setText(list.get(position).getMsgname());
		if(list.get(position).getOpen().equals("1")){
			holder.slipButton.setChecked(true);
		}else{
			holder.slipButton.setChecked(false);
		}
		holder.slipButton.SetOnChangedListener(String.valueOf(position), new OnChangedListener() {
			
			@Override
			public void OnChanged(String strName, boolean CheckState) {
				// TODO Auto-generated method stub
				if(listener!=null){
					listener.onChange(Integer.parseInt(strName), CheckState);
				}
			}
		});
		return contentView;

	}
	
	private class ViewHolder {
		TextView msg_name;
		SlipButton slipButton;
	}
	
	public void setAdapterListener(AdapterSlipOnChangeListener listener){
		this.listener=listener;
	}
	public interface AdapterSlipOnChangeListener{
		public void onChange(int index,boolean isCheck);
	}
}
