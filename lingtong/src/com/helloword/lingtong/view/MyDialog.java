package com.helloword.lingtong.view;

import java.util.ArrayList;
import java.util.List;

import com.helloword.lingtong.R;
import com.helloword.lingtong.R.layout;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

public class MyDialog extends Dialog {
	private Context context;
	private String[]typeSize=new String[]{"小","中","大","特大"};
	private String[]time=new String[]{"60天","90天","120天","永久"};
	private List<TextView> tvList=new ArrayList<TextView>();
	private List<CheckBox> boxList=new ArrayList<CheckBox>();
	private int old=-1;
	private TextView title;
	private OnChangeListener changeListener;
	private int mode;
	public MyDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
	}

	public MyDialog(Context context, int theme) {
		super(context, theme);
		this.setContentView(R.layout.dialog);
		this.getWindow().getAttributes().gravity = Gravity.CENTER;
		Window window = this.getWindow();
		WindowManager.LayoutParams lp = window.getAttributes();
		lp.alpha = 1.0f;
		lp.dimAmount = 0.5f;
		DisplayMetrics metric = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay()
				.getMetrics(metric);
		int width = metric.widthPixels; // 屏幕宽度（像素）
		lp.width = width - 80;
		setOwnerActivity((Activity) context);
		this.setCanceledOnTouchOutside(true);
		window.setAttributes(lp);
		this.context = context;
		for(int i=1;i<5;i++){
			TextView tv=(TextView) findViewById(context.getResources().getIdentifier("tv"+i, "id", context.getPackageName()));
			tvList.add(tv);
			CheckBox box= (CheckBox) findViewById(context.getResources().getIdentifier("check"+i, "id", context.getPackageName()));
			box.setTag(i-1);
			box.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
					// TODO Auto-generated method stub
					int index=(Integer) arg0.getTag();
					if(index==old){
						boolean isCheck=false;
						for(int i=0;i<boxList.size();i++){
							if(boxList.get(i).isChecked()){
								isCheck=true;
								break;
							}
						}
						if(!isCheck){
							arg0.setChecked(true);
						}
					}else{
						if(changeListener!=null){
							changeListener.setOnChange(mode,index+1);
						}
						boxList.get(old).setChecked(false);
					}
					old=index;
				}
			});
			boxList.add(box);
		}
		title=(TextView) findViewById(R.id.title);
	}
	
	
	public void changeResult(int index){
		if(index!=old){
			boxList.get(index).setChecked(true);
			boxList.get(old).setChecked(false);
		}
		old=index;
	}
	
	public void setOnChangeListener(OnChangeListener changeListener){
		this.changeListener=changeListener;
	}
	public void setData(int mode,int data){
		String[]temp;
		this.mode=mode;
		if(mode==0){
			temp=typeSize;
			title.setText("字体大小设置:");
		}else{
			temp=time;
			title.setText("信息缓存时间保持时间设置:");
		}
		for(int i=0;i<tvList.size();i++){
			tvList.get(i).setText(temp[i]);
			if (mode!=0) {
				tvList.get(i).setTextSize(16);
			}
		}
		old=data-1;
		boxList.get(data-1).setChecked(true);
	}
	
	public interface OnChangeListener{
		public void setOnChange(int mode,int st);
	}
}