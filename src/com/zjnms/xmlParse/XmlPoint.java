package com.zjnms.xmlParse;

import java.util.ArrayList;
import java.util.List;

/**
 * Xml文件中元素的指向对象
 * 
 * @author llj
 * 
 */
public class XmlPoint {
	// 对应的xml文件
	// private Xml xml;
	// 当前node节点模板对应的xml元素集合
	private List<MyElement> currXmlElmts;
	private boolean hasBinding = false;

	/**
	 * 构造 Xml文件中元素的指向对象
	 * 
	 * @param xml
	 */
	public XmlPoint() {
		// this.xml = xml;
		currXmlElmts = new ArrayList<MyElement>();
	}

	/**
	 * 判断该对象是否已绑定到模板
	 * 
	 * @return
	 */
	public boolean hasBinding2Model() {
		return this.hasBinding;
	}

	/**
	 * 将类元素绑定到元素模板
	 * 
	 * @param elmt
	 * @return
	 */
	public Model_Elmt_Binder binding2Model(ElmtModel elmt) {
		this.hasBinding = true;
		return new Model_Elmt_Binder(this, elmt);
	}

	/**
	 * 获取类元素的元素名
	 * 
	 * @return
	 */
	public String getElmtName() {
		if (currXmlElmts.size() == 0)
			return "";
		// 类元素中，所有元素具有相同的元素名
		return this.currXmlElmts.get(0).getName();
	}
	/**
	 * 获取类元素的数据描述名，数据描述名表示该类元素描述了哪一类数据
	 * @return
	 */
	public String getElmtDataName(){
		if (currXmlElmts.size() == 0)
			return "";
		
		return this.currXmlElmts.get(0).getAttributeValue("name");
	}

	/**
	 * 增加一个被指向的元素
	 * 
	 * @param e
	 */
	public void addCurrXmlElmt(MyElement e) {
		currXmlElmts.add(e);
	}

	/**
	 * 初始化指向到xml文件对象的根节点
	 */
	public void initialize(MyElement root) {
		// throw new NullPointerException("xml文件根节点有误");
		if (root != null)
			currXmlElmts.add(root);
	}

	/**
	 * 获取当前指向的xml元素集合
	 * 
	 * @return
	 */
	public List<MyElement> getCurrXmlElmts() {
		return this.currXmlElmts;
	}

	/**
	 * 以自根节点向下的方式，指向下一种xml元素
	 * 
	 * @return 不存在下一种xml元素，返回false
	 */
	public List<XmlPoint> getChildPoints() {
		// 对该对象的所有子元素分类，返回同类子元素的XmlPoint的集合
		Classification clsf = new Classification();
		clsf.onAttrNameCheck();
		clsf.onNameAtrrValueCheck();
		clsf.onElmtNameCheck();
		clsf.onChildCheck();
		return clsf.classify(this.getChildElmts());
	}

	/**
	 * 获取该对象下的所有子元素
	 * 
	 * @return
	 */
	public List<MyElement> getChildElmts() {
		List<MyElement> childElmts = new ArrayList<MyElement>();
		for (int i = 0; i < this.currXmlElmts.size(); ++i) {
			MyElement pElmt = currXmlElmts.get(i);
			List<MyElement> iter = pElmt.getChildElements();
			// 把每一个元素的所有子元素存放在同一集合中
			for (int j = 0; j < iter.size(); ++j)
				childElmts.add(iter.get(j));
		}
		return childElmts;
	}
	public String toString(){
		if (currXmlElmts.size() == 0)
			return "";
		return this.currXmlElmts.get(0).toString();
	}
}
