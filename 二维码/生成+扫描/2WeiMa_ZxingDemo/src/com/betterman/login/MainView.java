package com.betterman.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.google.zxing.client.android.CaptureActivity;
import com.google.zxing.client.android.R;

public class MainView extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent();
		switch (v.getId()) {
		case R.id.scanning_analytic:// 扫描二维码
			intent.setClass(MainView.this, CaptureActivity.class);
			MainView.this.startActivity(intent);
			break;
		case R.id.image_analytic:// 生成二维码
			intent.setClass(MainView.this, ImageActivity.class);
			MainView.this.startActivity(intent);
			break;
		default:
			break;
		}
	}

}
