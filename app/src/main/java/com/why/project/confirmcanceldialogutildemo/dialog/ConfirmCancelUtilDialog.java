package com.why.project.confirmcanceldialogutildemo.dialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.why.project.confirmcanceldialogutildemo.R;


/**
 * Created by HaiyuKing
 * Used 确认取消对话框样式【含有标题、左右按钮（同时适用于一个按钮）】
 */

public class ConfirmCancelUtilDialog extends DialogFragment {

	private static final String TAG = "ConfirmCancelDialog";
	/**View实例*/
	private View myView;
	/**context实例*/
	private Context mContext;
	/**标记：用来代表是从哪个界面打开的这个对话框*/
	private String mTag;

	/**设置对话框内容和样式的监听器（标题、内容、按钮样式，包括控制隐藏）*/
	private DialogSetListener mDialogSetListener;
	/**三个按钮的点击事件监听器*/
	private DialogClickListener mDialogClickListener;

	public static ConfirmCancelUtilDialog getInstance(Context mContext, DialogSetListener mDialogSetListener){
		ConfirmCancelUtilDialog dialog = new ConfirmCancelUtilDialog();
		dialog.mContext = mContext;
		dialog.mDialogSetListener = mDialogSetListener;

		return dialog;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_NoTitleBar_Fullscreen);//全屏
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#80000000")));//设置背景为半透明，并且没有标题
		getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
		//设置窗体全屏
		getDialog().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		myView = inflater.inflate(R.layout.dialog_confirm_cancel_util, container, false);
		return myView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		//初始化控件以及设置初始数据和监听事件
		initView();
	}

	/**
	 * 设置宽度和高度值，以及打开的动画效果
	 */
	@Override
	public void onStart() {
		super.onStart();

		//设置对话框的宽高，必须在onStart中
//		Window window = this.getDialog().getWindow();
//		window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);//全屏（盖住状态栏）
//		window.setGravity(Gravity.BOTTOM);//设置在底部
		//打开的动画效果--缩放+渐隐

		//设置对话框的宽高，必须在onStart中
		DisplayMetrics metrics = new DisplayMetrics();
		this.getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
		Window window = this.getDialog().getWindow();
		window.setLayout(metrics.widthPixels, metrics.heightPixels - getStatusBarHeight(mContext));
		window.setGravity(Gravity.BOTTOM);//设置在底部
		//打开的动画效果--缩放+渐隐
	}

	/**获取状态栏的高度*/
	private int getStatusBarHeight(Context context) {
		int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
		return context.getResources().getDimensionPixelSize(resourceId);
	}

	/**实例化控件*/
	@SuppressWarnings("deprecation")
	private void initView() {

		TextView title = (TextView)myView.findViewById(R.id.title);//标题
		TextView message = (TextView)myView.findViewById(R.id.message);//内容
		Button leftBtn = (Button)myView.findViewById(R.id.cancel);//左侧按钮
		Button rightBtn = (Button)myView.findViewById(R.id.confirm);//右侧按钮

		//==========================初始展现==========================
		if(mDialogSetListener != null){
			mDialogSetListener.setDialog(title, message, leftBtn, rightBtn);
		}

		//如果左侧按钮不存在，则右侧按钮占满并且底色为蓝色、文字颜色为白色
		if(leftBtn.getVisibility() == View.GONE) {
			rightBtn.setBackgroundDrawable(ContextCompat.getDrawable(mContext,R.drawable.dialog_confirm_cancel_util_btnlayout_bg));
			rightBtn.setTextColor(mContext.getResources().getColor(R.color.confirm_cancel_util_dialog_btn_left_text_color));
		}
		//如果右侧按钮不存在，则左侧按钮占满并且底色为蓝色、文字颜色为白色
		if(rightBtn.getVisibility() == View.GONE) {
			leftBtn.setBackgroundDrawable(ContextCompat.getDrawable(mContext,R.drawable.dialog_confirm_cancel_util_btnlayout_bg));
			leftBtn.setTextColor(mContext.getResources().getColor(R.color.confirm_cancel_util_dialog_btn_left_text_color));
		}

		mTag = this.getTag();
		Log.e(TAG, "mTag====="+mTag);

		//==========================初始化监听事件==========================
		leftBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(mDialogClickListener != null){
					mDialogClickListener.leftClickListener();
				}
				dismiss();//关闭对话框，自动执行onDismiss中的方法
			}
		});

		rightBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(mDialogClickListener != null){
					mDialogClickListener.rightClickListener();
				}
				dismiss();//关闭对话框，自动执行onDismiss中的方法
			}
		});

	}

	/**设置对话框内容和样式的监听器（标题、内容、按钮样式，包括控制隐藏）*/
	public static abstract interface DialogSetListener
	{
		/**设置标题、内容、按钮的文本以及按钮的显示隐藏
		 * @param title - 标题控件【默认“提示”】
		 * @param message - 内容控件【默认空白】
		 * @param leftBtn - 左侧按钮控件【默认“取消”】
		 * @param rightBtn - 右侧按钮控件【默认“确定”】*/
		public abstract void setDialog(TextView title, TextView message, Button leftBtn, Button rightBtn);
	}

	public void setDialogSetListener(DialogSetListener dialogSetListener) {
		mDialogSetListener = dialogSetListener;
	}

	/**三个按钮的点击事件监听器*/
	public static abstract interface DialogClickListener
	{
		/**左侧按钮*/
		public abstract void leftClickListener();
		/**右侧按钮*/
		public abstract void rightClickListener();
	}

	public void setDialogClickListener(DialogClickListener dialogClickListener) {
		mDialogClickListener = dialogClickListener;
	}
}
