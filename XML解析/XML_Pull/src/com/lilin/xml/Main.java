package com.lilin.xml;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * pull解析xml
 * 
 * @author lilin
 * @date 2011-8-3 下午08:22:56
 * @ClassName: Main
 * @Description: TODO
 */
public class Main extends Activity {
	ListView listView;
	Button pull_btn;
	List<Person> persons = null;
	/* 获取XML文件流 */
	InputStream inStream = getClass().getClassLoader().getResourceAsStream("itcast.xml");

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		listView = (ListView) findViewById(R.id.list);
		pull_btn = (Button) findViewById(R.id.pull_btn);
		pull_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					persons = Pull_Person.getPersons(inStream);
				} catch (Throwable e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				Map<String, Object> map = new HashMap<String, Object>();
				for (int i = 0; i < persons.size(); i++) {
					map = new HashMap<String, Object>();
					map.put("id", persons.get(i).getId());
					map.put("name", persons.get(i).getName());
					map.put("age", persons.get(i).getAge());

					list.add(map);
				}
				SimpleAdapter simpleAdapter = new SimpleAdapter(Main.this,
						list, R.layout.list,
						new String[] { "id", "name", "age" }, new int[] {
								R.id.id_text, R.id.name_text, R.id.age_text });
				listView.setAdapter(simpleAdapter);
				listView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						String s = listView.getItemAtPosition(arg2).toString();
						Toast.makeText(Main.this, s, 2000).show();
					}
				});
				System.out.println("/* Pull解析XML文件 */");
				for (Person person : persons) {
					System.out.println(person.toString());
				}
			}
		});

		/* 再将解析出的信息重新生成一个XML文件 ，并保存到项目所在的文件夹 */
		FileOutputStream outputStream = null;
		OutputStreamWriter writer = null;
		try {
			outputStream = this.openFileOutput("person.xml",
					Context.MODE_PRIVATE);
			writer = new OutputStreamWriter(outputStream, "UTF-8");
			BufferedWriter writer2 = new BufferedWriter(writer);// 增加的缓存功能
			Pull_Person.create_XML(persons, writer2);// 调用生成方法

			/* 以字符串的形式得到XML文件 */
			StringWriter stringWriter = new StringWriter();
			Pull_Person.create_XML(persons, stringWriter);// 调用生成方法
			String contentString = stringWriter.toString();
			System.out.println(contentString);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}