package com.lilin.xml;

import java.io.InputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlSerializer;

import android.util.Xml;

/**
 * 采用Pull解析XML内容
 */
public class Pull_Person {

	/**
	 * 以pull方式生成XML文件
	 * 
	 * @author lilin
	 * @date 2011-8-3 下午08:43:12
	 * @Title: save
	 * @Description: TODO
	 * @param @param persons
	 * @param @param writer
	 * @param @throws Throwable
	 * @return void
	 * @throws
	 */
	public static void create_XML(List<Person> persons, Writer writer// 写入器，更灵活
	) throws Throwable {

		XmlSerializer serializer = Xml.newSerializer();// 获得序列化对象
		serializer.setOutput(writer);

		serializer.startDocument("UTF-8", true);// 生成开始文档
		serializer.startTag(null, "persons");// 生成开始标签
		// 以迭代方式生成标签
		for (Person person : persons) {
			serializer.startTag(null, "person");
			serializer.attribute(null, "id", person.getId().toString());

			serializer.startTag(null, "name");
			serializer.text(person.getName());
			serializer.endTag(null, "name");

			serializer.startTag(null, "age");
			serializer.text(person.getAge().toString());
			serializer.endTag(null, "age");

			serializer.endTag(null, "person");
		}
		// 结束标签
		serializer.endTag(null, "persons");
		// 结束文档
		serializer.endDocument();
		writer.flush();
		writer.close();
	}

	public static List<Person> getPersons(InputStream inStream)
			throws Throwable {
		List<Person> persons = null;
		Person person = null;
		/* 声明XML的pull解析器 */
		XmlPullParser parser = Xml.newPullParser();
		/* 声明编码方式 */
		parser.setInput(inStream, "UTF-8");
		int eventType = parser.getEventType();// 产生第一个事件
		while (eventType != XmlPullParser.END_DOCUMENT) {// 只要不是文档结束事件
			switch (eventType) {
			case XmlPullParser.START_DOCUMENT:// 0 文档开始事件
				persons = new ArrayList<Person>();
				break;

			case XmlPullParser.START_TAG:// 2 开始元素
				String name = parser.getName();// 获取解析器当前指向的元素的名称
				if ("person".equals(name)) {
					person = new Person();
					person.setId(new Integer(parser.getAttributeValue(0)));
				}
				if (person != null) {
					if ("name".equals(name)) {
						person.setName(parser.nextText());// 获取解析器当前指向元素的下一个文本节点的值
					}
					if ("age".equals(name)) {
						person.setAge(new Short(parser.nextText()));
					}
				}
				break;

			case XmlPullParser.END_TAG:
				if ("person".equals(parser.getName())) {
					persons.add(person);
					person = null;
				}
				break;
			}
			eventType = parser.next();
		}
		return persons;
	}
}
