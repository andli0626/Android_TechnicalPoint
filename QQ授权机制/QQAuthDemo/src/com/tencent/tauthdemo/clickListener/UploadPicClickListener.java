package com.tencent.tauthdemo.clickListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import android.content.Intent;
import android.net.Uri;
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
public class UploadPicClickListener implements OnClickListener {
	private TAuthDemoActivity mActivity;
	
	public UploadPicClickListener(TAuthDemoActivity activity) {
		mActivity = activity;
	}

	@Override
	public void onClick(View v) {
		if (!mActivity.satisfyConditions()) {
			TDebug.msg("请先获取access token和open id", mActivity);
			return;
		}
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.addCategory(Intent.CATEGORY_OPENABLE);
		intent.setType("image/*");
		mActivity.startActivityForResult(intent, TAuthDemoActivity.REQUEST_PICK_PICTURE);
		
	}
	
	public static void uploadPic(final TAuthDemoActivity activity, Uri uri) {
		activity.showDialog(TAuthDemoActivity.PROGRESS);
		Bundle bundle = null;
		bundle = new Bundle();
		
		byte[] buff = null;
		try {
			InputStream is = activity.getContentResolver().openInputStream(uri);
			ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len = 0;
			while( (len = is.read(buffer)) !=-1 ){
				outSteam.write(buffer, 0, len);
			}
			outSteam.close();
			is.close();
			buff = outSteam.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		bundle.putByteArray("picture", buff);//必须.上传照片的文件名以及图片的内容（在发送请求时，图片内容以二进制数据流的形式发送，见下面的请求示例），注意照片名称不能超过30个字符。
		bundle.putString("photodesc", "QQ登陆SDK：UploadPic测试" + new Date());//照片描述，注意照片描述不能超过200个字符。 
		bundle.putString("title", "QQ登陆SDK：UploadPic测试" + System.currentTimeMillis() + ".png");//照片的命名，必须以.jpg, .gif, .png, .jpeg, .bmp此类后缀结尾。
//		bundle.putString("albumid", "564546-asdfs-feawfe5545-45454");//相册id，不填则传到默认相册
		bundle.putString("x", "0-360");//照片拍摄时的地理位置的经度。请使用原始数据（纯经纬度，0-360）。
		bundle.putString("y", "0-360");//照片拍摄时的地理位置的纬度。请使用原始数据（纯经纬度，0-360）。
		
		TencentOpenAPI.uploadPic(activity.mAccessToken, activity.mAppid, activity.mOpenId, bundle, new Callback() {
			
			@Override
			public void onSuccess(final Object obj) {
				activity.runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						activity.dismissDialog(TAuthDemoActivity.PROGRESS);
						activity.showMessage("用户上传照片返回数据", obj.toString());
					}
				});
			}
			
			@Override
			public void onFail(final int ret, final String msg) {
				activity.runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						activity.dismissDialog(TAuthDemoActivity.PROGRESS);
						TDebug.msg(ret + ": " + msg, activity);
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
