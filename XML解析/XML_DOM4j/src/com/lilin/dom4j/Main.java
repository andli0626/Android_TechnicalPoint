package com.lilin.dom4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;

public class Main extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		/* 获取XML文件流 */
		InputStream XML_Stream = getClass().getClassLoader()
				.getResourceAsStream("itcast.xml");

		BufferedReader in = new BufferedReader(
				new InputStreamReader(XML_Stream));
		StringBuffer buffer = new StringBuffer();
		String line = "";
		try {
			while ((line = in.readLine()) != null) {
				buffer.append(line);
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		List<Person> person_List = null;

		try {
			System.out.println(buffer.toString());
			person_List = DOM4j_Person.AnalysisPersonXML(buffer.toString());

		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (person_List != null) {
			for (int i = 0; i < person_List.size(); i++) {
				System.out.println("id=" + person_List.get(i).getId() + "name="
						+ person_List.get(i).getName() + "age="
						+ person_List.get(i).getAge());
			}
		} else {
			System.out.println("is null");
		}
	}
}