package com.lilin.sax;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * 采用SAX解析XML内容
 */
public class SAX_Task {

	/* 以输入流的形式传入XML文件 */
	public List<Task> getTasks(InputStream inStream) throws Throwable {
		/* SAX解析工厂 */
		SAXParserFactory factory = SAXParserFactory.newInstance();
		/* SAX解析器 */
		SAXParser parser = factory.newSAXParser();

		TaskParser TaskParser = new TaskParser();
		/* 调用parse方法 */
		parser.parse(inStream// 输入流
				, TaskParser// 调用回调方法
				);
		inStream.close();
		return TaskParser.getTasks();
	}

	/**
	 * 回调方法
	 * 
	 * @author lilin
	 * @date 2011-8-2 下午10:21:05
	 * @ClassName: TaskParser
	 * @Description: TODO
	 */
	// 继承DefaultHandler帮助类，该类实现了ContentHandler接口
	private final class TaskParser extends DefaultHandler

	{
		private List<Task> Task_List = null;// Task集合
		private String tagName = null;// 记录当前解析到的元素节点名称
		private Task Task = null;

		public List<Task> getTasks() {
			return Task_List;
		}

		/* 开始文档方法 ：当解析到 <Task id="23"> */
		@Override
		public void startDocument() throws SAXException {
			Task_List = new ArrayList<Task>();
		}

		/* 开始元素方法 */
		@Override
		public void startElement(String uri, // 命名空间
				String localName, // 不带命名空间前缀的标签名
				String qName,// 带命名空间前缀的标签名。
				Attributes attributes// 得到所有的属性名和相应的值
		) throws SAXException {

			if ("archives".equals(localName)) {
				Task = new Task();
				// Task.setId(new Integer(attributes.getValue(0)));//
				// 取得标签Task的属性id的值，并传给Task的id
			}
			tagName = localName;// 记录下当前的标签名
		}

		/*
		 * 开始文本方法 ：用来处理在XML文件中读到的内容
		 */
		@Override
		public void characters(char[] ch,// 文件的字符串内容
				int start, // 字符串在这个数组中的起始位置
				int length// 字符串在这个数组中的长度
		) throws SAXException {
			if (tagName != null) {
				String data = new String(ch, start, length);// 获取文本节点的数据
				if ("title".equals(tagName)) {
					Task.setTitle(data);
				} else if ("sender".equals(tagName)) {
					Task.setSender(data);
				} else if ("time".equals(tagName)) {
					Task.setTime(data);
				} else if ("content".equals(tagName)) {
					Task.setContent(data);
				}
			}
		}

		/* 结束文档方法 */
		@Override
		public void endElement(String uri, String localName, String qName)
				throws SAXException {
			if ("archives".equals(localName)) {
				Task_List.add(Task);
				Task = null;
			}
			tagName = null;
		}
	}
}
