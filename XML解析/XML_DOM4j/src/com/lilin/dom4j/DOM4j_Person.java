package com.lilin.dom4j;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * 采用Dom4j解析xml
 * 
 * @author lilin
 * @date 2011-8-7 下午07:35:44
 * @ClassName: DOM4j_Person
 * @Description: TODO
 */
public class DOM4j_Person {
	@SuppressWarnings("unchecked")
	public static ArrayList<Person> AnalysisPersonXML(String xml) {
		Document document = null;
		try {
			document = DocumentHelper.parseText(xml);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		ArrayList person_List = new ArrayList();
		Element root = document.getRootElement();// 得到根元素
		List focs = root.elements();
		for (int i = 0; i < focs.size(); i++) {
			Person person = new Person();
			Element foc = (Element) focs.get(i);
			if (foc.element("id") != null)
				person.setId(Integer.valueOf(foc.elementText("id")));
			if (foc.element("name") != null)
				person.setName(foc.elementText("name"));
			if (foc.element("age") != null)
				person.setAge(Short.valueOf(foc.elementText("age")));
			person_List.add(person);
		}
		return person_List;
	}
}