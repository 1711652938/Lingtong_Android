package com.helloword.lingtong.view;

import com.helloword.lingtong.R;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

/**
 * @author MuFe
 * 
 */
public class CustomProgressDialog extends Dialog {
	@SuppressWarnings("unused")
	private Context context;
	public CustomProgressDialog(Context context) {
		super(context);
		this.context=context;
	}

	public CustomProgressDialog(Context context, int theme) {
		super(context, theme);
		setContentView(R.layout.view_customprogressdialog);
		this.getWindow().getAttributes().gravity = Gravity.CENTER;
		Window window = this.getWindow();
		WindowManager.LayoutParams lp = window.getAttributes();
		lp.alpha = 1.0f;
		lp.dimAmount = 0.0f;
		setOwnerActivity((Activity) context);
		this.setCanceledOnTouchOutside(true);
		window.setAttributes(lp);
		this.context=context;
	}



	public void OwnDismiss() {
		if(!((Activity) ((ContextThemeWrapper) this.getContext()).getBaseContext()).isFinishing()){
			try{
				
				dismiss();
			}catch(Exception e){
				
			}
		}
	}

	@Override
	public void dismiss() {
		if (!((Activity) ((ContextThemeWrapper) this.getContext())
				.getBaseContext()).isFinishing()) {
			ImageView imageView = (ImageView) this
					.findViewById(R.id.loadingImageView);
			AnimationDrawable animationDrawable = (AnimationDrawable) imageView
					.getTag();
			imageView.clearAnimation();
			if (animationDrawable != null) {
				animationDrawable.stop();
			}
		}
		CustomProgressDialog.super.dismiss();
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		if(!((Activity) ((ContextThemeWrapper) this.getContext()).getBaseContext()).isFinishing()){
			super.show();
			ImageView imageView = (ImageView) this
					.findViewById(R.id.loadingImageView);
			AnimationDrawable animationDrawable = (AnimationDrawable) imageView
					.getBackground();
			animationDrawable.start();
			imageView.setTag(animationDrawable);
		}
		//((Activity)context).showDialog(0);
	}

}
