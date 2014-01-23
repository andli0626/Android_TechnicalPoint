package com.lilin.dom;

import java.io.InputStream;
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
		InputStream inStream = getClass().getClassLoader().getResourceAsStream(
				"itcast.xml");

		List<Person> persons = null;
		/* DOM解析XML文件 */

		try {
			persons = DOM_Person.getPersons(inStream);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("/* DOM解析XML文件 */");
		for (Person person : persons) {
			System.out.println(person.toString());
		}
	}
}