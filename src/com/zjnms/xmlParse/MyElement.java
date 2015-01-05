package com.zjnms.xmlParse;

import java.util.ArrayList;
import java.util.List;
import org.dom4j.Document;
import org.dom4j.Element;

/**
 * dom4j中Element对象的包装对象，在dom4j.Element的基础上，增加了一个保存传递数据的集合，
 * 每个MyElement对象都保持该集合，集合中的数据等于其 父元素的传递数据加上该元素本身的传递数据
 * 
 * @author llj
 * 
 */
public class MyElement {
	// 该元素数据的外键
	private List<DBKey> foreign = new ArrayList<DBKey>();
	// 该元素数据的主键，当元素对应数据库的多个实体表时，元素有多个主键
	private List<DBKey> primaries = new ArrayList<DBKey>();
	// 包装的xml元素
	private Element elmt;

	// 父元素
	private MyElement parentElmt;
	private List<MyElement> ChildElmts = new ArrayList<MyElement>();

	/**
	 * 构造包含传递数据（如外键id）的xml元素对象
	 * 
	 * @param elmt
	 *            xml元素对象
	 */
	// public MyElement(Element elmt) {
	// this.elmt = elmt;
	// }

	public MyElement(Element elmt, MyElement parentElmt) {
		this.elmt = elmt;
		this.parentElmt = parentElmt;
	}

	/**
	 * 获取外键集合
	 * 
	 * @return
	 */
	public List<DBKey> getForeignKeys() {
		return this.foreign;
	}

	/**
	 * 增加一个外键
	 * 
	 * @param pKey
	 */
	public void addForeignKey(DBKey pKey) {
		if (pKey == null)
			return;
		this.foreign.add(pKey);
	}

	/**
	 * 获取元素的文档对象
	 * 
	 * @return
	 */
	public Document getDoc() {
		return this.elmt.getDocument();
	}

	/**
	 * 获取主键
	 * 
	 * @return
	 */
	public List<DBKey> getPrimaryKeys() {
		// if (primary == null)
		// return null;
		return primaries;
	}

	/**
	 * 增加主键集合
	 * 
	 * @param keys
	 */
	// public void addPrimaryKeys(List<DBKey> keys){
	// for(int i=0;i<keys.size();++i)
	// this.primaries.add(keys.get(i));
	// }
	public void addPrimaryKeys(List<ProcParam> params) {
		for (int i = 0; i < params.size(); ++i) {
			DBKey key = new DBKey(params.get(i).getParamName(), params.get(i)
					.getParamValue());
			this.primaries.add(key);
		}
	}

	/**
	 * 以属性名获取属性值
	 * 
	 * @param attrName
	 * @return
	 */
	// public String getAttributeValue(String attrNameIgnoreSpell) {
	// // 属性名以不区分大小写的方式获取属性值
	// List<?> attrs = this.elmt.attributes();
	// for (int i = 0; i < attrs.size(); ++i) {
	// Attribute attr = (Attribute) attrs.get(i);
	// String attrLowerName = attr.getName().toLowerCase();
	// if (attrLowerName.equals(attrNameIgnoreSpell.toLowerCase()))
	// return attr.getValue();
	// }
	// return null;
	// }
	/**
	 * 以属性名获取属性值
	 * 
	 * @param attrName
	 * @return
	 */
	public String getAttributeValue(String attrName) {
		return this.elmt.attributeValue(attrName);
	}

	/**
	 * 获取元素的名称
	 * 
	 * @return
	 */
	public String getName() {
		return this.elmt.getName();
	}

	/**
	 * 以数据库列的键名获取键值
	 * 
	 * @param key
	 * @return
	 */
	public String getDBKeyValue(String key) {
		String value = getDBPrimaryKeyValue(key);
		if (value == null)
			value = getDBForeignKeyValue(key);
		return value;
	}

	public String getValue(String flag) {
		String value = this.getElmt().attributeValue(flag);
		if (value == null)
			value = this.getDBKeyValue(flag);

		return value;
	}

	/**
	 * 以数据库列的主键名获取主键值
	 * 
	 * @param key
	 * @return
	 */
	public String getDBPrimaryKeyValue(String key) {
		for (int i = this.primaries.size() - 1; i > -1; --i) {
			DBKey dbkey = primaries.get(i);
			if (dbkey.getColumnName().equals(key))
				return dbkey.getValue();
		}
		return null;
	}

	/**
	 * 以数据库列的外键名获取外键值
	 * 
	 * @param key
	 * @return
	 */
	public String getDBForeignKeyValue(String key) {
		// 返回最后插入的foreignColName外键
		for (int i = foreign.size() - 1; i > -1; --i) {
			DBKey dbkey = foreign.get(i);
			if (dbkey.getColumnName().equals(key))
				return dbkey.getValue();
		}
		return "";
	}

	/**
	 * 
	 * @param key
	 * @return
	 */
	public boolean contains(String key) {
		if (this.foreign.contains(key) || this.primaries.contains(key))
			return true;
		return false;
	}

	/**
	 * 获取xml实体元素
	 * 
	 * @return
	 */
	public Element getElmt() {
		return elmt;
	}

	/**
	 * 获取元素的所有属性
	 * 
	 * @return
	 */
	public List<?> getAttributes() {
		return this.elmt.attributes();
	}

	/**
	 * 判断两个元素描述的数据是否是同一组记录的数据， 当元素的所有外键都相同或是只有一个外键不相同时他们是同一组记录
	 * （每一条记录都会有一个line，这个不同 的外键即是line的id）
	 * 
	 * @param compared
	 * @return
	 */
	public boolean isSameGroup(MyElement compared) {
		List<DBKey> foreigns = compared.foreign;
		if (this.foreign.size() != foreigns.size())
			return false;
		int count = 0;
		for (int i = 0; i < foreigns.size(); ++i) {
			DBKey comparedKey = foreigns.get(i);
			if (comparedKey == null)
				continue;
			String name = comparedKey.getColumnName();
			String value = this.getDBForeignKeyValue(name);
			if (value == null || !value.equals(comparedKey.getValue()))
				++count;
		}
		if (count <= 1)
			return true;
		return false;
	}

	/**
	 * 判断是否有相同的父元素
	 * 
	 * @param elmt
	 * @return
	 */
	public static boolean equalParent(MyElement lhd, MyElement rhd) {
		return lhd.parentElmt.equals(rhd.parentElmt);
	}
	
	/**
	 * 判断祖父元素是否相同
	 * @param lhd
	 * @param rhd
	 * @return
	 */
	public static boolean equalGrandParent(MyElement lhd, MyElement rhd){
		MyElement lhdprt = lhd.parentElmt;
		MyElement rhdprt = rhd.parentElmt;
		
		MyElement lhdgrdprt=lhdprt.parentElmt;
		MyElement rhdgrdprt=rhdprt.parentElmt;
		return lhdgrdprt.equals(rhdgrdprt);
	}

	/**
	 * 获取元素的所有子元素
	 * 
	 * @return
	 */
	public List<MyElement> getChildElements() {
		this.ChildElmts.clear();
		List<?> elmts = this.elmt.elements();
		for (int i = 0; i < elmts.size(); ++i) {
			Element iter = (Element) elmts.get(i);
			if (iter == null)
				continue;
			// 设置元素数据的外键引用
			MyElement myElmt = new MyElement(iter, this);
			this.transferKeys2(myElmt);
			this.ChildElmts.add(myElmt);
		}
		return this.ChildElmts;
	}

	public MyElement createChildMyElement(String elmtName) {
		MyElement child = new MyElement(this.elmt.addElement(elmtName), this);
		this.transferKeys2(child);
		return child;
	}

	public void transferKeys2(MyElement elmt) {
		elmt.foreign.clear();
		// 父元素的外键集合设置为其外键集合
		for (int i = 0; i < this.foreign.size(); ++i)
			elmt.foreign.add(foreign.get(i));
		// 再增加父元素的主键为其外键
		for (int i = 0; i < this.primaries.size(); ++i)
			elmt.foreign.add(primaries.get(i));
	}

	/**
	 * 传递属性到其所有兄弟元素
	 * 
	 * @param name
	 */
	public void transferAttrToAllBrother(String name) {
		// String name = attrModel.xmlname;
		// this.toString();
		String value = this.getElmt().attributeValue(name);
		if (value == null) {
			// System.out.println("元素中不存在 " + name + " 属性");
			return;
		}
		DBKey key = new DBKey(name, value);
		if (parentElmt == null) {
			this.foreign.add(key);
			return;
		} else {
			this.parentElmt.foreign.add(key);
			// 传递给子元素
			List<MyElement> childs = this.parentElmt.ChildElmts;
			for (int i = 0; i < childs.size(); ++i)
				childs.get(i).foreign.add(key);
		}
	}
}

/**
 * 数据库的标识键
 * 
 * @author llj
 * 
 */
class DBKey {
	String columnName;
	int value = -1;
	String valueStr = "";

	/**
	 * 
	 * @param columnName
	 *            字段的列名
	 * @param value
	 *            字段的值（number类型）
	 */
	public DBKey(String columnName, int value) {
		this.columnName = columnName;
		this.value = value;
	}

	public DBKey(String columnName, String value) {
		this.columnName = columnName;
		this.valueStr = value;
	}

	/**
	 * 获取列名
	 * 
	 * @return
	 */
	public String getColumnName() {
		return columnName;
	}

	/**
	 * 设置列名
	 * 
	 * @param columnName
	 */
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	/**
	 * 获取值
	 * 
	 * @return
	 */
	public String getValue() {
		if (value != -1)
			return Integer.valueOf(value).toString();
		return valueStr;
	}

	/**
	 * 设置值
	 * 
	 * @param value
	 */
	public void setValue(int value) {
		this.value = value;
	}

	/**
	 * 设置值
	 * 
	 * @param value
	 */
	public void setValue(String value) {
		this.valueStr = value;
	}

	/**
	 * 判断键是否相等
	 * 
	 * @param key
	 * @return
	 */
	public boolean equalKey(DBKey key) {
		return this.getColumnName().equals(key.getColumnName())
				&& this.getValue().equals(key.getValue());
	}
}
