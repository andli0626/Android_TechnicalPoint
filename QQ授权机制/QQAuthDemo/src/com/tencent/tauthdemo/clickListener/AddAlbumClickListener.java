package com.tencent.tauthdemo.clickListener;

import java.util.Date;

import android.os.Bundle;
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
public class AddAlbumClickListener implements OnClickListener {
	private TAuthDemoActivity mActivity;

	public AddAlbumClickListener(TAuthDemoActivity activity) {
		mActivity = activity;
	}

	@Override
	public void onClick(View v) {
		if (!mActivity.satisfyConditions()) {
			TDebug.msg("请先获取access token和open id", mActivity);
			return;
		}
		mActivity.showDialog(TAuthDemoActivity.PROGRESS);
		Bundle bundle = null;
		bundle = new Bundle();
		bundle.putString("albumname", "QQ登陆SDK：Add_Album测试" + System.currentTimeMillis());//必须。相册名，不能超过30个字符。
		bundle.putString("albumdesc", "QQ登陆SDK：Add_Album测试" + new Date());//相册描述，不能超过200个字符。
		bundle.putString("priv", "5");//相册权限，其取值含义为： 1=公开；3=只主人可见； 4=QQ好友可见； 5=问答加密。不传则相册默认为公开权限。
		bundle.putString("question", "question");//如果priv取值为5，即相册是问答加密的，则必须包含问题和答案两个参数：
		bundle.putString("answer", "answer");//如果priv取值为5，即相册是问答加密的，则必须包含问题和答案两个参数：
		
		TencentOpenAPI.addAlbum(mActivity.mAccessToken, mActivity.mAppid, mActivity.mOpenId, bundle, new Callback() {
			
			@Override
			public void onSuccess(final Object obj) {
				mActivity.runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						mActivity.dismissDialog(TAuthDemoActivity.PROGRESS);
						mActivity.showMessage("用户创建相册返回数据", "share_id: " + obj.toString());
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
