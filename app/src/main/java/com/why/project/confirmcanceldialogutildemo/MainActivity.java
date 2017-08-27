package com.why.project.confirmcanceldialogutildemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.why.project.confirmcanceldialogutildemo.dialog.ConfirmCancelUtilDialog;

public class MainActivity extends AppCompatActivity {

	private Button btn_confirm;
	private Button btn_confirmcancel;
	private Button btn_notitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initViews();
		initEvents();
	}

	private void initViews() {
		btn_confirm = (Button) findViewById(R.id.btn_confirm);
		btn_confirmcancel = (Button) findViewById(R.id.btn_confirmcancel);
		btn_notitle = (Button) findViewById(R.id.btn_notitle);
	}

	private void initEvents() {

		btn_confirm.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				ConfirmCancelUtilDialog dialog = ConfirmCancelUtilDialog.getInstance(MainActivity.this,
						new ConfirmCancelUtilDialog.DialogSetListener() {
							@Override
							public void setDialog(TextView title, TextView message, Button leftBtn, Button rightBtn) {
								title.setText("提示");
								message.setText("请检查用户名");
								message.setGravity(Gravity.CENTER);
								leftBtn.setText("确定");
								rightBtn.setVisibility(View.GONE);
							}
						});
				dialog.setDialogClickListener(new ConfirmCancelUtilDialog.DialogClickListener() {
					@Override
					public void leftClickListener() {
						Toast.makeText(MainActivity.this, "确定", Toast.LENGTH_SHORT).show();
					}

					@Override
					public void rightClickListener() {
					}
				});
				dialog.show(getSupportFragmentManager(), "confirmCancelDialog");
			}
		});

		btn_confirmcancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				ConfirmCancelUtilDialog dialog = ConfirmCancelUtilDialog.getInstance(MainActivity.this,
						new ConfirmCancelUtilDialog.DialogSetListener() {
							@Override
							public void setDialog(TextView title, TextView message, Button leftBtn, Button rightBtn) {
								title.setText("保存到");
								message.setText("您可以'立即发送'给收件人，也可以保存到'草稿箱'");
								leftBtn.setText("立即发送");
								rightBtn.setText("草稿箱");
							}
						});
				dialog.setDialogClickListener(new ConfirmCancelUtilDialog.DialogClickListener() {
					@Override
					public void leftClickListener() {
						Toast.makeText(MainActivity.this, "立即发送", Toast.LENGTH_SHORT).show();
					}

					@Override
					public void rightClickListener() {
						Toast.makeText(MainActivity.this, "草稿箱", Toast.LENGTH_SHORT).show();
					}
				});
				dialog.show(getSupportFragmentManager(), "confirmCancelDialog");
			}
		});

		btn_notitle.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ConfirmCancelUtilDialog dialog = ConfirmCancelUtilDialog.getInstance(MainActivity.this,
						new ConfirmCancelUtilDialog.DialogSetListener() {
							@Override
							public void setDialog(TextView title, TextView message, Button leftBtn, Button rightBtn) {
								title.setVisibility(View.GONE);
								message.setText("发现新版本，是否升级？");
								message.setGravity(Gravity.CENTER);
								leftBtn.setText("确定");
								rightBtn.setText("取消");
							}
						});
				dialog.setDialogClickListener(new ConfirmCancelUtilDialog.DialogClickListener() {
					@Override
					public void leftClickListener() {
						Toast.makeText(MainActivity.this, "确定", Toast.LENGTH_SHORT).show();
					}

					@Override
					public void rightClickListener() {
						Toast.makeText(MainActivity.this, "取消", Toast.LENGTH_SHORT).show();
					}
				});
				dialog.show(getSupportFragmentManager(), "confirmCancelDialog");
			}
		});
	}

}
