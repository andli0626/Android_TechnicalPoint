package com.lilin.ui;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Gallery;

import com.test.R;

/**
 * 主界面
 * 
 * @author lilin
 * @date 2011-12-22 上午08:21:16
 * @ClassName: MainView
 */
public class MainView extends Activity {
	private Gallery advGallery = null;
	private AdvGalleryAdapter myAdapter = null;
	public List<String> imgURL = new ArrayList<String>();

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		advGallery = (Gallery) findViewById(R.id.home_advs_gallery);
		imgURL.add("http://www.huangpuqu.sh.cn/shhpadmin/UploadFile/nanjdljd/image/image005(1).jpg");
		imgURL.add("http://www.huangpuqu.sh.cn/shhpadmin/UploadFile/nanjdljd/image/image005(2).jpg");
		imgURL.add("http://www.huangpuqu.sh.cn/shhpadmin/UploadFile/nanjdljd/image/image005(3).jpg");
		

		myAdapter = new AdvGalleryAdapter(MainView.this, imgURL, advGallery);
		advGallery.setAdapter(myAdapter);
		advGallery
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						
					}
					public void onNothingSelected(AdapterView<?> arg0) {
					}
				});

	}

}