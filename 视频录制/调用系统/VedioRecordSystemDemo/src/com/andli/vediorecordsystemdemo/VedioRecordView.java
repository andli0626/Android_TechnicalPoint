package com.andli.vediorecordsystemdemo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

import com.lilin.util.DateHelp;

public class VedioRecordView extends Activity {
	private static final int CAMERA_ACTIVITY = 1;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		Intent mIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
		mIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0.5);// 画质0.5
		mIntent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 70000);// 70s
		startActivityForResult(mIntent, CAMERA_ACTIVITY);// CAMERA_ACTIVITY = 1

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		if (resultCode != RESULT_OK)
			return;

		try {
			AssetFileDescriptor videoAsset = getContentResolver()
					.openAssetFileDescriptor(intent.getData(), "r");
			FileInputStream fis = videoAsset.createInputStream();
			File tmpFile = new File(Environment.getExternalStorageDirectory(),
					DateHelp.getFormateDate(3) + ".3gp");
			FileOutputStream fos = new FileOutputStream(tmpFile);
			byte[] buf = new byte[1024];
			int len;
			while ((len = fis.read(buf)) > 0) {
				fos.write(buf, 0, len);
			}
			fis.close();
			fos.close();
		} catch (IOException io_e) {
			Toast.makeText(this, io_e.toString(), 3000).show();
		}

		super.onActivityResult(requestCode, resultCode, intent);
	}
}