package com.zjnms.xmlParse;

//import java.util.ArrayList;
//import java.util.List;
import org.dom4j.*;

/**
 * 以XmlModel的规则，实现xml文件读取的对象
 * 
 * @author llj
 * 
 */
public class XmlReader {
	private Element root;

	public XmlReader(Element root) {
		this.root = root;
		// nextElmtBuff = new ArrayList<Element>();
	}

	/**
	 * 获取N级的第一个元素
	 * 
	 * @param level
	 *            指定的级数
	 * @return
	 */
	public Element getNLevelElement(int level) {
		Element iter = root;
		for (int i = 1; i < level; ++i) {
			// 调用dom4j，获取第一个子元素
			iter = (Element) iter.elements().get(0);
			if (iter == null)
				return null;
		}
		return iter;
	}

}
