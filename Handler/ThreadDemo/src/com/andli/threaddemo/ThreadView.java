package com.andli.threaddemo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.TextView;

public class ThreadView extends Activity {

	private String tag = "andli";

	private TextView gpsTextView;
	private int SHOWINFOMSG = 1000;
	private boolean isStart = true;

	int i = 0;

	Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == SHOWINFOMSG) {
				gpsTextView.setText(i + "");
			}
		};
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		isStart = true;
		gpsTextView = (TextView) findViewById(R.id.showtext);

		// 非主线程
		new Thread() {
			public void run() {
				while (true) {

					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					if (isStart) {
						Log.i(tag, ">>>>>>>开始计数");
						i++;

						// 第一种方法
						// mHandler.sendEmptyMessage(SHOWINFOMSG);

						// 第二种方法
						// mHandler.post(new Runnable() {
						// public void run() {
						// gpsTextView.setText(i + "");
						// }
						// });

						// 第三种方法----
						Message msg = new Message();
						msg.what = SHOWINFOMSG;
						mHandler.sendMessage(msg);
						// mHandler.handleMessage(msg);//非主线程不能调用

					} else {
						Log.i(tag, ">>>>>>>停止计数");
					}
				}
			};
		}.start();

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			i = 0;
			isStart = false;
			finish();
			break;
		}
		return false;
	}

}