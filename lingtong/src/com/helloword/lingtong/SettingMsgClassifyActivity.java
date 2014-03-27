package com.helloword.lingtong;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.net.wifi.p2p.WifiP2pManager.Channel;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.helloword.lingtong.adapter.MsgClassifyListAdapter;
import com.helloword.lingtong.adapter.MsgClassifyListAdapter.AdapterSlipOnChangeListener;
import com.helloword.lingtong.model.ChannelData;
import com.helloword.lingtong.model.MsgClassifyData;
import com.helloword.lingtong.model.Result;
import com.helloword.lingtong.model.UserData;
import com.helloword.lingtong.model.Config;
import com.helloword.lingtong.util.GetDataUtil;

/**
 * 
 * 
 * @author Administrator
 * 
 */
public class SettingMsgClassifyActivity extends BaseActivity {
	private Button back;
	private ImageView refresh;
	private TextView title;
	private ListView listView;
	private List<MsgClassifyData> list;
	private MsgClassifyListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting_msg_classify);
		initView();
	}

	private void initView() {
		listView = (ListView) findViewById(R.id.msg_classify_list);
		title = (TextView) findViewById(R.id.tittle);
		title.setText("短讯分类");
		back = (Button) findViewById(R.id.back);
		refresh = (ImageView) findViewById(R.id.right_btn);
		refresh.setOnClickListener(this);
		refresh.setVisibility(View.VISIBLE);
		refresh.setBackgroundResource(R.drawable.refresh);

		back.setOnClickListener(this);
		list = initData();
		adapter = new MsgClassifyListAdapter(this, list);
		listView.setAdapter(adapter);
		adapter.setAdapterListener(new AdapterSlipOnChangeListener() {

			@Override
			public void onChange(int index, boolean isCheck) {
				// TODO Auto-generated method stub
				 MsgClassifyData data = (MsgClassifyData)adapter.getItem(index);
				 setChannel(isCheck,index,data.getMsgname());
			}
		});
		 getData();
	}


	public void setChannel(final boolean isorder,final int setIndex,String name){
		List<Object> objects=new ArrayList<Object>();
		objects.add(((lingtongApplication) getApplication()).getUserData()
				.getId());
		if(isorder){
			objects.add("order");
		}else{
			objects.add("cancel");
		}
		objects.add(name);
		GetDataUtil.getInstance(this).getData(SETCHANNEL, objects, true, true, new Handler(){

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				Result result = (Result) msg.obj;
				if (result.getCode().equals("0000")) {
					UserData userData=((lingtongApplication) getApplication())
							.getUserData();
					Config con1 =userData.getConfig();
					List<ChannelData> list=con1.getList();
					if(isorder){
						list.get(setIndex).setStatus("1");
					}else{
						list.get(setIndex).setStatus("0");
					}
					con1.setList(list);
					userData.setConfig(con1);
					((lingtongApplication) getApplication()).setUserData(userData);
				}
			}
		});
	}
	private List<MsgClassifyData> initData() {
		List<MsgClassifyData> mlList = new ArrayList<MsgClassifyData>();
		Config config = ((lingtongApplication) getApplication()).getUserData()
				.getConfig();
		if (config == null) {
			return mlList;
		}
		List<ChannelData> list=config.getList();
		if (list != null ) {
			for (int i = 0; i <list.size(); i++) {
				MsgClassifyData msgClassifyData = new MsgClassifyData();
				msgClassifyData.setMsgname(list.get(i).getChannel());
				msgClassifyData.setOpen(list.get(i).getStatus());
				mlList.add(msgClassifyData);
			}
		}
		return mlList;
	}

	@Override
	public void onClick(View v) {
		int vid = v.getId();
		if (vid == R.id.back) {
			this.finish();
			overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
		} else if (vid == R.id.right_btn) {
			 getData();
		}
	}
	
	public void getData(){
		List<Object> objects = new ArrayList<Object>();
		objects.add(((lingtongApplication) getApplication()).getUserData()
				.getId());
		GetDataUtil.getInstance(this).getData(GETCHANNEL, objects, true,
				true, new Handler() {

					@Override
					public void handleMessage(Message msg) {
						// TODO Auto-generated method stub
						UserData data = ((lingtongApplication) getApplication())
								.getUserData();
						Result<List<ChannelData>> result = (Result<List<ChannelData>>) msg.obj;
						if (result.getCode().equals("0000")) {
							Config config = data.getConfig();
							List<ChannelData> _list=result.getData();
							if(_list!=null){
								config.setList(_list);
								data.setConfig(config);
								((lingtongApplication) getApplication())
								.setUserData(data);
							}
							list.clear();
							list.addAll(initData());
							adapter.notifyDataSetChanged();
						}
					}

				});
	}
}
