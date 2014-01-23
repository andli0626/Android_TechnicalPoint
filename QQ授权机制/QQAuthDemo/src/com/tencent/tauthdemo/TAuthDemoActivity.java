package com.tencent.tauthdemo;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.tencent.tauth.TencentOpenAPI;
import com.tencent.tauth.TencentOpenAPI2;
import com.tencent.tauth.TencentOpenHost;
import com.tencent.tauth.TencentOpenRes;
import com.tencent.tauth.bean.OpenId;
import com.tencent.tauth.http.Callback;
import com.tencent.tauth.http.TDebug;
import com.tencent.tauthdemo.clickListener.AddAlbumClickListener;
import com.tencent.tauthdemo.clickListener.AddShareClickListener;
import com.tencent.tauthdemo.clickListener.AddTopicClickListener;
import com.tencent.tauthdemo.clickListener.GetUserInfoClickListener;
import com.tencent.tauthdemo.clickListener.GetUserProfileClickListener;
import com.tencent.tauthdemo.clickListener.ListAlbumClickListener;
import com.tencent.tauthdemo.clickListener.UploadPicClickListener;

public class TAuthDemoActivity extends Activity {

	private static final String TAG = "andli";
	public static final int REQUEST_PICK_PICTURE = 1001;
	/**
	 * <p>
	 * 不能包含特殊字符“#”</br> 不能是浏览器能识别的协议，比如：http://auth.qq.com</br> 不设置时默认使用：
	 * auth://tauth.qq.com/</br></br> 不区分大小写。在Manifest设置的scheme必须是小写</br>
	 * 在Manifest中设置intent-filter：data android:scheme="auth"
	 * </p>
	 * 例如：</br> &ltintent-filter> </br> &ltaction
	 * android:name="android.intent.action.VIEW" /> </br> &ltcategory
	 * android:name="android.intent.category.DEFAULT" /> </br> &ltcategory
	 * android:name="android.intent.category.BROWSABLE" /> </br> &ltdata
	 * android:scheme="auth"/> </br> &lt/intent-filter> </br>
	 * 
	 */
	private static final String CALLBACK = "auth://tauth.qq.com/";

	public String mAppid = "222222";// 申请时分配的appid
	private String scope = "get_user_info,get_user_profile,add_share,add_topic,list_album,upload_pic,add_album";// 授权范围
	private AuthReceiver receiver;

	public String mAccessToken, mOpenId;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		WebView.enablePlatformNotifications();

		ImageView loginBtn = (ImageView) findViewById(R.id.login);
		loginBtn.setImageDrawable(TencentOpenRes.getBigLoginBtn(getAssets()));

		loginBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				TencentOpenAPI2.logIn(getApplicationContext(), mOpenId, scope,
						mAppid, "_self", CALLBACK, null, null);
			}
		});

		ImageView loginBBtn = (ImageView) findViewById(R.id.login_browser);
		loginBBtn.setImageDrawable(TencentOpenRes.getLoginBtn(getAssets()));
		loginBBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				TencentOpenAPI2.logIn(getApplicationContext(), mOpenId, scope,
						mAppid, "_blank", CALLBACK, null, null);
			}
		});

		registerIntentReceivers();

		findViewById(R.id.get_user_info).setOnClickListener(
				new GetUserInfoClickListener(this));
		findViewById(R.id.get_user_profile).setOnClickListener(
				new GetUserProfileClickListener(this));
		findViewById(R.id.add_share).setOnClickListener(
				new AddShareClickListener(this));
		findViewById(R.id.add_topic).setOnClickListener(
				new AddTopicClickListener(this));
		findViewById(R.id.list_album).setOnClickListener(
				new ListAlbumClickListener(this));
		findViewById(R.id.upload_pic).setOnClickListener(
				new UploadPicClickListener(this));
		findViewById(R.id.add_album).setOnClickListener(
				new AddAlbumClickListener(this));
	}

	/**
	 * 打开登录认证与授权页面
	 * 
	 * @param String
	 *            clientId 申请时分配的appid
	 * @param String
	 *            target 打开登录页面的方式：“_slef”以webview方式打开; "_blank"以内置安装的浏览器方式打开
	 * @author John.Meng<arzen1013@gmail> QQ:3440895
	 * @date 2011-9-5
	 */
	private void auth(String clientId, String target) {
		Intent intent = new Intent(TAuthDemoActivity.this,
				com.tencent.tauth.TAuthView.class);

		intent.putExtra(TencentOpenHost.CLIENT_ID, clientId);
		intent.putExtra(TencentOpenHost.SCOPE, scope);
		intent.putExtra(TencentOpenHost.TARGET, target);
		intent.putExtra(TencentOpenHost.CALLBACK, CALLBACK);

		startActivity(intent);

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (receiver != null) {
			unregisterIntentReceivers();
		}
	}

	private void registerIntentReceivers() {
		receiver = new AuthReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(TencentOpenHost.AUTH_BROADCAST);
		registerReceiver(receiver, filter);
	}

	private void unregisterIntentReceivers() {
		unregisterReceiver(receiver);
	}

	public void setOpenIdText(String txt) {
		((TextView) findViewById(R.id.openid)).setText(txt);
		mOpenId = txt;
	}

	/**
	 * 广播的侦听，授权完成后的回调是以广播的形式将结果返回
	 * 
	 * @author John.Meng<arzen1013@gmail> QQ:3440895
	 * @date 2011-9-5
	 */
	public class AuthReceiver extends BroadcastReceiver {

		private static final String TAG = "AuthReceiver";

		@Override
		public void onReceive(Context context, Intent intent) {
			Bundle exts = intent.getExtras();
			String raw = exts.getString("raw");
			String access_token = exts.getString(TencentOpenHost.ACCESS_TOKEN);
			String expires_in = exts.getString(TencentOpenHost.EXPIRES_IN);
			String error_ret = exts.getString(TencentOpenHost.ERROR_RET);
			String error_des = exts.getString(TencentOpenHost.ERROR_DES);
			Log.i(TAG, String.format("raw: %s, access_token:%s, expires_in:%s",
					raw, access_token, expires_in));

			if (access_token != null) {
				mAccessToken = access_token;
				((TextView) findViewById(R.id.access_token))
						.setText(access_token);
				// TDebug.msg("正在获取OpenID...", getApplicationContext());
				if (!isFinishing()) {
					showDialog(PROGRESS);
				}

				// 用access token 来获取open id
				TencentOpenAPI.openid(access_token, new Callback() {

					public void onCancel(int flag) {

					}

					@Override
					public void onSuccess(final Object obj) {
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								dismissDialog(PROGRESS);
								setOpenIdText(((OpenId) obj).getOpenId());
							}
						});
					}

					@Override
					public void onFail(int ret, final String msg) {
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								dismissDialog(PROGRESS);
								TDebug.msg(msg, getApplicationContext());
							}
						});
					}
				});
			}
			if (error_ret != null) {
				((TextView) findViewById(R.id.access_token))
						.setText("获取access token失败" + "\n错误码: " + error_ret
								+ "\n错误信息: " + error_des);
			}
		}

	}

	public boolean satisfyConditions() {
		return mAccessToken != null && mAppid != null && mOpenId != null
				&& !mAccessToken.equals("") && !mAppid.equals("")
				&& !mOpenId.equals("");
	}

	public static final int PROGRESS = 0;

	@Override
	protected Dialog onCreateDialog(int id) {
		Dialog dialog = null;
		switch (id) {
		case PROGRESS:
			dialog = new ProgressDialog(this);
			((ProgressDialog) dialog).setMessage("请求中,请稍等...");
			break;
		}

		return dialog;
	}

	public void showMessage(String title, String msg) {
		Dialog dialog = new Dialog(TAuthDemoActivity.this);
		ScrollView rootView = new ScrollView(TAuthDemoActivity.this);
		TextView view = new TextView(TAuthDemoActivity.this);
		view.setText(msg);
		rootView.addView(view);
		dialog.setContentView(rootView);
		dialog.setTitle(title);
		if (!isFinishing() && !dialog.isShowing()) {
			try {
				dialog.show();
			} catch (Exception e) {
				Log.d("tauthdemo", "activity is finished.");
			}
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case REQUEST_PICK_PICTURE: {
			if (data != null) {
				Uri bitmapUri = data.getData();
				if (bitmapUri != null) {
					UploadPicClickListener.uploadPic(this, bitmapUri);
				}
			}
			break;
		}
		}
	}

}