package com.lilin.pullmethod;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;
import android.util.Xml;
import com.lilin.model.PersonModel;

/**
 * 解析XML
 * 
 * @author lilin
 * @date 2011-9-26 上午09:23:37
 * @ClassName: PullXML
 * @Description: TODO
 */
public class PullXML {
	public static List<PersonModel> pullXML(InputStream inStream)
			throws Throwable {
		List<PersonModel> persons = null;
		PersonModel person = null;
		/* 声明XML的pull解析器 */
		XmlPullParser xmlpullparser = Xml.newPullParser();
		/* 声明编码方式 */
		xmlpullparser.setInput(inStream, "UTF-8");
		int eventType = xmlpullparser.getEventType();// 产生第一个事件
		while (eventType != XmlPullParser.END_DOCUMENT) {// 只要不是文档结束事件
			switch (eventType) {
			case XmlPullParser.START_DOCUMENT:// 0 文档开始事件
				persons = new ArrayList<PersonModel>();
				break;

			case XmlPullParser.START_TAG:// 2 开始元素
				String name = xmlpullparser.getName();// 获取解析器当前指向的元素的名称
				if ("person".equals(name)) {
					person = new PersonModel();
					person
							.setId(new Integer(xmlpullparser
									.getAttributeValue(0)));
				}
				if (person != null) {
					if ("name".equals(name)) {
						person.setName(xmlpullparser.nextText());// 获取解析器当前指向元素的下一个文本节点的值
					}
					if ("age".equals(name)) {
						person.setAge(new Short(xmlpullparser.nextText()));
					}
				}
				break;

			case XmlPullParser.END_TAG:
				if ("person".equals(xmlpullparser.getName())) {
					persons.add(person);
					person = null;
				}
				break;
			}
			eventType = xmlpullparser.next();
		}
		return persons;
	}
}
