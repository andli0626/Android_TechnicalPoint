package com.lilin.sax;

import java.io.InputStream;
import java.net.Socket;
import java.util.List;
import android.app.Activity;
import android.os.Bundle;

/**
 * SAX解析XML
 * 
 * @author lilin
 * @date 2011-8-3 下午08:23:13
 * @ClassName: Main
 * @Description: TODO
 */
public class Main extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		/* 获取XML文件流 */
		InputStream XML_Stream = getClass().getClassLoader()
				.getResourceAsStream("person.xml");

		List<Person> person_List = null;
		/* SAX解析XML文件 */
		SAX_Person service = new SAX_Person();
		try {
			person_List = service.getPersons(XML_Stream);

		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (person_List != null) {
			for (int i = 0; i < person_List.size(); i++) {
				System.out.println("id=" + person_List.get(i).id + "name="
						+ person_List.get(i).name + "age="
						+ person_List.get(i).age);
			}
		} else {
			System.out.println("is null");
		}

		/* SAX解析XML文件 */
		XML_Stream = getClass().getClassLoader()
				.getResourceAsStream("task.xml");
		List<Task> task_List = null;

		SAX_Task saxTask = new SAX_Task();
		try {
			task_List = saxTask.getTasks(XML_Stream);

		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (task_List != null) {
			for (int i = 0; i < task_List.size(); i++) {
				String title = task_List.get(i).title;
				String sender = task_List.get(i).sender;
				String time = task_List.get(i).time;
				String content = task_List.get(i).content;
				System.out.println("title=" + title + "sender=" + sender
						+ "time=" + time + "content=" + content);
			}
		} else {
			System.out.println("is null");
		}
	}
}