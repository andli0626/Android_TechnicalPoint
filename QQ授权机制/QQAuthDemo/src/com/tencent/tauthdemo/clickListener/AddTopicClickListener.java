package com.tencent.tauthdemo.clickListener;

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
public class AddTopicClickListener implements OnClickListener {
	private TAuthDemoActivity mActivity;

	public AddTopicClickListener(TAuthDemoActivity activity) {
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
		bundle.putString("richtype", "2");//发布心情时引用的信息的类型。1表示图片； 2表示网页； 3表示视频。 
		bundle.putString("richval", ("http://www.qq.com" + "#" + System.currentTimeMillis()));//发布心情时引用的信息的值。有richtype时必须有richval 
		bundle.putString("con","腾讯QQ登陆测试：心情不错！");//发布的心情的内容。
		bundle.putString("lbs_nm","广东省深圳市南山区高新科技园腾讯大厦");//地址文
		bundle.putString("lbs_x","0-360");//经度。请使用原始数据（纯经纬度，0-360）。
		bundle.putString("lbs_y","0-360");//纬度。请使用原始数据（纯经纬度，0-360）。
		bundle.putString("lbs_id","360");//地点ID。lbs_id与lbs_idnm通常一起使用，来明确标识一个地址。 
		bundle.putString("lbs_idnm","腾讯");//地点名称。lbs_id与lbs_idnm通常一起使用，来明确标识一个地址。
		
		TencentOpenAPI.addTopic(mActivity.mAccessToken, mActivity.mAppid, mActivity.mOpenId, bundle, new Callback() {
			
			@Override
			public void onSuccess(final Object obj) {
				mActivity.runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						mActivity.dismissDialog(TAuthDemoActivity.PROGRESS);
						mActivity.showMessage("发表说说返回数据", obj.toString());
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
