package com.lilin.dom;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * 采用DOM解析XML内容
 */
public class DOM_Person {

	public static List<Person> getPersons(InputStream inStream)
			throws Throwable {
		List<Person> persons = new ArrayList<Person>();
		/* 实例化一个文档构建器工厂 */
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		/* 通过文档构建器工厂获取一个文档构建器 */
		DocumentBuilder builder = factory.newDocumentBuilder();
		/* 通过文档构建器构建一个文档实例 */
		Document documnet = builder.parse(inStream);// 调用parse()完成解析xml文件，形成文档树

		Element root = documnet.getDocumentElement();// 取得根元素

		NodeList personNodes = root.getElementsByTagName("person");

		for (int i = 0; i < personNodes.getLength(); i++) {
			Person person = new Person();
			Element personElement = (Element) personNodes.item(i);
			person.setId(new Integer(personElement.getAttribute("id")));
			NodeList personChilds = personElement.getChildNodes();// 取得Person的子节点
			for (int y = 0; y < personChilds.getLength(); y++) {
				if (personChilds.item(y).getNodeType() == Node.ELEMENT_NODE) {// 判断当前节点是否是元素类型节点
					Element childElement = (Element) personChilds.item(y);
					if ("name".equals(childElement.getNodeName())) {
						person.setName(childElement.getFirstChild()
								.getNodeValue());// 取得第一个子节点的文本值
					} else if ("age".equals(childElement.getNodeName())) {
						person.setAge(new Short(childElement.getFirstChild()
								.getNodeValue()));
					}
				}
			}
			persons.add(person);
		}
		return persons;
	}
}
