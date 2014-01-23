package com.lilin.pullxml;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.lilin.model.PersonModel;
import com.lilin.pullmethod.CreateXML;
import com.lilin.pullmethod.PullXML;

/**
 * Pull生成并解析XML文件
 * 
 * @author lilin
 * @date 2011-9-26 上午08:24:50
 * @ClassName: Main
 * @Description: TODO
 */
public class Main extends Activity {
	ListView listView;
	Button pull_btn;
	List<PersonModel> personModel;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		listView = (ListView) findViewById(R.id.list);
		pull_btn = (Button) findViewById(R.id.pull_btn);

		System.out.println("文件的路径-->" + getFilePath());
		File file = new File(getFilePath() + "/test.xml");
		try {
			if (!file.exists()) {// 如果文件不存在，就创建新文件
				System.out.println("文件不存在，马上创建！");
				List<PersonModel> personModel = new ArrayList<PersonModel>();
				personModel.add(new PersonModel(1001, "张三", (short) 30));
				personModel.add(new PersonModel(1002, "李四", (short) 18));
				personModel.add(new PersonModel(1003, "王二", (short) 21));
				CreateXML.createXML(file, personModel);
			}
			FileInputStream fileInputStream = new FileInputStream(file);
			personModel = PullXML.pullXML(fileInputStream);

		} catch (Throwable e) {
			e.printStackTrace();
		}

		pull_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				Map<String, Object> map = new HashMap<String, Object>();
				for (int i = 0; i < personModel.size(); i++) {
					map = new HashMap<String, Object>();
					map.put("id", personModel.get(i).getId());
					map.put("name", personModel.get(i).getName());
					map.put("age", personModel.get(i).getAge());
					list.add(map);
				}
				SimpleAdapter simpleAdapter = new SimpleAdapter(
						Main.this,//
						list, //
						R.layout.list,//
						new String[] { "id", "name", "age" },//
						new int[] { R.id.id_text, R.id.name_text, R.id.age_text });//
				listView.setAdapter(simpleAdapter);
				listView.setOnItemClickListener(new OnItemClickListener() {
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						String s = listView.getItemAtPosition(arg2).toString();
						Toast.makeText(Main.this, s, 2000).show();
					}
				});
				System.out.println("/* Pull解析XML文件 */");
				for (PersonModel person : personModel) {
					System.out.println(person.toString());
				}
			}
		});
	}

	// 获取保存路径
	public File getFilePath() {
		File filePath = null;
		// 判断SD卡存在与否
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			filePath = new File(Environment.getExternalStorageDirectory()
					.getAbsolutePath()
					+ "/A_Test/test/");
			if (!filePath.isDirectory()) {// 如果不存在就创建
				filePath.mkdirs();
			}
		} else {
			Toast.makeText(Main.this, "存储卡不存在，请插入卡!", 3000).show();
		}
		return filePath;
	}

}