package com.lilin.pullmethod;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;
import org.xmlpull.v1.XmlSerializer;
import com.lilin.model.PersonModel;
import android.util.Xml;
/**
 * 生成XML文件
 * 
 * @author lilin
 * @date 2011-9-26 上午08:36:08
 * @ClassName: CreateXML
 * @Description: TODO
 */
public class CreateXML {
	// 生成XML文件
	public static void createXML(File file, List<PersonModel> personModel)
			throws IllegalArgumentException, IllegalStateException, IOException {
		FileOutputStream outputStream = null;
		OutputStreamWriter writer = null;
		outputStream = new FileOutputStream(file);
		writer = new OutputStreamWriter(outputStream, "UTF-8");
		BufferedWriter writer2 = new BufferedWriter(writer);// 增加的缓存功能
		XmlSerializer serializer = Xml.newSerializer();
		serializer.setOutput(writer2);
		serializer.setOutput(outputStream, "utf-8");
		serializer.startDocument("utf-8", true);
		serializer.startTag(null, "persons");
		for (PersonModel persons : personModel) {
			serializer.startTag(null, "person");
			serializer.attribute(null, "id", persons.getId().toString());
			
			serializer.startTag(null, "name");
			serializer.text(persons.getName());
			serializer.endTag(null, "name");
			
			serializer.startTag(null, "age");
			serializer.text(persons.getAge().toString());
			serializer.endTag(null, "age");
			
			serializer.endTag(null, "person");
		}
		serializer.endTag(null, "persons");
		serializer.endDocument();
		outputStream.close();
	}
}
