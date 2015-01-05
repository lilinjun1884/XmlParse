package com.zjnms.xmlParse;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Attribute;

/**
 * 对xml元素进行分类的对象
 * 
 * @author llj
 * 
 */
public class Classification {
	private boolean onElmtNameCheck = false;
	private boolean onAttrNameCheck = false;
	// 表示以元素的name属性（如果存在）的值作为分类依据
	private boolean onNameAtrrValueCheck = false;
	private boolean onChildCheck = false;

	/**
	 * 对元素集依规则分类
	 * 
	 * @param elmts
	 * @return 同类元素的指向对象的集合
	 */
	public List<XmlPoint> classify(List<MyElement> elmts) {
		List<XmlPoint> classes = new ArrayList<XmlPoint>();
		while (elmts.size() != 0) {
			XmlPoint point = new XmlPoint();
			// 以第一个元素为代表找同类元素
			MyElement type = elmts.get(0);
			point.addCurrXmlElmt(type);
			for (int i = 1; i < elmts.size(); ++i) {
				MyElement iter = elmts.get(i);
				if (this.isSameType(type, iter)) {
					point.addCurrXmlElmt(iter);
					// 找到并附加后从查找集中剔除
					elmts.remove(i);
					// 剔除后保证下标的一致性
					--i;
				}
			}
			// 剔除代表元素
			elmts.remove(0);
			classes.add(point);
		}
		return classes;
	}

	/**
	 * 把元素名称作为分类的依据
	 */
	public void onElmtNameCheck() {
		onElmtNameCheck = true;
	}

	/**
	 * 把属性名是否相同作为分类的依据
	 */
	public void onAttrNameCheck() {
		onAttrNameCheck = true;
	}

	/**
	 * 把元素的name属性（如果存在）的值作为分类依据
	 */
	public void onNameAtrrValueCheck() {
		onNameAtrrValueCheck = true;
	}

	/**
	 * 把直接子元素作为分类的依据
	 */
	public void onChildCheck() {
		onChildCheck = true;
	}

	/**
	 * 判断两元素是否同类
	 * 
	 * @param lhd
	 * @param rhd
	 * @return
	 */
	private boolean isSameType(MyElement lhd, MyElement rhd) {
		boolean check1 = true, check2 = true, check3 = true, check4 = true;
		// 依据指定的分类条件分类
		if (onElmtNameCheck)
			check1 = elmtNameCheck(lhd, rhd);
		if (onAttrNameCheck)
			check2 = attrNameCheck(lhd, rhd);
		if (onNameAtrrValueCheck)
			check3 = nameAtrrValueCheck(lhd, rhd);
		if (onChildCheck)
			check4 = childCheck(lhd, rhd);
		if (check1 && check2 && check3 && check4)
			return true;
		else
			return false;
	}

	/**
	 * 通过元素名检查是否同类
	 * 
	 * @param lhd
	 * @param rhd
	 * @return
	 */
	private boolean elmtNameCheck(MyElement lhd, MyElement rhd) {
		return lhd.getName().equals(rhd.getName());
	}

	/**
	 * 通过属性是否相同来检查是否同类
	 * 
	 * @param lhd
	 * @param rhd
	 * @return
	 */
	private boolean attrNameCheck(MyElement lhd, MyElement rhd) {
		List<?> lhdAttrs = lhd.getAttributes();
		int lhdAttrSize = lhdAttrs.size();
		// 属性个数不等，不满足检查
		if (lhdAttrSize != rhd.getAttributes().size())
			return false;
		// lhd中的所有属性均在rhd中，满足检查
		for (int i = 0; i < lhdAttrSize; ++i) {
			String lhdAttrName = ((Attribute) lhdAttrs.get(i)).getName();
			if (rhd.getAttributeValue(lhdAttrName) == null)
				return false;
		}
		return true;
	}

	/**
	 * 通过name属性的值是否相同来检查是否同类
	 * 
	 * @param lhd
	 * @param rhd
	 * @return
	 */
	private boolean nameAtrrValueCheck(MyElement lhd, MyElement rhd) {
		String lhdValue = lhd.getAttributeValue("name");
		String rhdValue = rhd.getAttributeValue("name");
		if (lhdValue == null ^ rhdValue == null)
			return false;
		else if (lhdValue == null && rhdValue == null)
			return true;
		else
			return lhdValue.equals(rhdValue);
	}

	/**
	 * 通过直接子元素检查是否同类
	 * 
	 * @param lhd
	 * @param rhd
	 * @return
	 */
	private boolean childCheck(MyElement lhd, MyElement rhd) {
		List<MyElement> lhdElmts = lhd.getChildElements();
		List<MyElement> rhdElmts = rhd.getChildElements();
		// 创建分类对象，检查子元素是否同类
		Classification clsf = new Classification();
		clsf.onAttrNameCheck();
		clsf.onNameAtrrValueCheck();
		clsf.onElmtNameCheck();

		for (int i = 0; i < lhdElmts.size(); ++i) {
			MyElement lhdElmt = lhdElmts.get(i);
			for (int j = 0; j < rhdElmts.size(); ++j) {
				MyElement rhdElmt = rhdElmts.get(j);
				if (clsf.isSameType(lhdElmt, rhdElmt))
					break;
				else if (j == rhdElmts.size() - 1)
					return false;
			}
		}
		return true;
	}
}