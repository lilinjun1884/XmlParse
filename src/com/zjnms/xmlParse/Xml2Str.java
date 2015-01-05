package com.zjnms.xmlParse;

import java.io.File;

import org.dom4j.*;
import org.dom4j.io.SAXReader;

/**
 * 将xml文件读取为xml字符串的对象
 * 
 * @author llj
 * 
 */
public class Xml2Str {
	private Document doc;

	/**
	 * 读取xml字符串
	 * 
	 * @param filePath
	 * @return
	 */
	public String run(String filePath) {
		try {
			File f = new File(filePath);
			SAXReader reader = new SAXReader();
			doc = reader.read(f);
			return doc.asXML();
		} catch (DocumentException e) {
			e.printStackTrace();
			return null;
		}
	}
	// // test
	// public static void main(String[] args) {
	// String basePath = "d:\\Users\\llj\\Desktop\\xml输入输出\\xml\\xml文件\\";
	// //String filePath = basePath;
	// Xml2Str proc = new Xml2Str();
	// String res = proc.run(basePath+"getAlarm_ 其他网管-综合网管.xml");
	// System.out.println(res);
	// }

}
