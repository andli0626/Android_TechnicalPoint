package com.tencent.tauthdemo.clickListener;

import android.view.View;
import android.view.View.OnClickListener;

import com.tencent.tauth.TencentOpenAPI;
import com.tencent.tauth.http.Callback;
import com.tencent.tauth.http.TDebug;
import com.tencent.tauthdemo.TAuthDemoActivity;

/**
 * @author email:csshuai2009@gmail.com qq:65112183
 * @version 创建时间：2011-9-16 上午11:15:55
 * 类说明
 */
public class GetUserInfoClickListener implements OnClickListener {
	private TAuthDemoActivity mActivity;

	public GetUserInfoClickListener(TAuthDemoActivity activity) {
		mActivity = activity;
	}

	@Override
	public void onClick(View v) {
		if (!mActivity.satisfyConditions()) {
			TDebug.msg("请先获取access token和open id", mActivity);
			return;
		}
		mActivity.showDialog(TAuthDemoActivity.PROGRESS);
		TencentOpenAPI.userInfo(mActivity.mAccessToken, mActivity.mAppid, mActivity.mOpenId, new Callback() {
			
			@Override
			public void onSuccess(final Object obj) {
				mActivity.runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						mActivity.dismissDialog(TAuthDemoActivity.PROGRESS);
						mActivity.showMessage("用户信息", obj.toString());
					}
				});
			}
			
			@Override
			public void onFail(final int ret, final String msg) {
				mActivity.runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						mActivity.dismissDialog(TAuthDemoActivity.PROGRESS);
						TDebug.msg(ret + ": " + msg, mActivity);
					}
				});
			}

			@Override
			public void onCancel(int flag) {
				// TODO Auto-generated method stub
				
			}
		});
	}

}
