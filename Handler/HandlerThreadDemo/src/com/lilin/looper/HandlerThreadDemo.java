package com.lilin.looper;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;

/**
 * 使用Looper来处理消息队列
 * 
 * @author lilin
 * @date 2011-8-10 上午09:03:32
 * @ClassName: HandlerTest2
 * @Description: TODO
 */
public class HandlerThreadDemo extends Activity {
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		System.out.println("当前Activity的ID-->" + Thread.currentThread().getId());
		
		HandlerThread handlerThread = new HandlerThread("handler_thread"); // 生成一个HandlerThread对象，实现了使用Looper来处理消息队列的功能，这个类由Android应用程序框架提供
		handlerThread.start();// 在使用HandlerThread的getLooper()方法之前，必须先调用该类的start();
		
		MyHandler myHandler = new MyHandler(handlerThread.getLooper());
		Message msg = myHandler.obtainMessage();

		Bundle b = new Bundle();
		b.putInt("age", 20);
		b.putString("name", "Jhon");

		msg.setData(b);// 将数据存放到Message中
		msg.sendToTarget();// 将msg发送到目标对象，所谓的目标对象，就是生成该msg对象的handler对象
	}

	class MyHandler extends Handler {
		public MyHandler() {

		}

		public MyHandler(Looper looper) {
			super(looper);
		}

		@Override
		public void handleMessage(Message msg) {
			Bundle b = msg.getData();// 从Message中获得数据
			int age = b.getInt("age");
			String name = b.getString("name");
			System.out.println("age is " + age + ", name is" + name);
			System.out.println("Handler的ID-->" + Thread.currentThread().getId());

		}
	}
}
