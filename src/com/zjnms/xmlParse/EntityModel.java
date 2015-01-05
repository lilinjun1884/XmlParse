package com.zjnms.xmlParse;

/**
 * 表示描述xml的一个模板，对应数据表Tbbase_Interdesc的一条记录
 * 
 * @author llj
 * 
 */
public class EntityModel implements Cloneable {
	/**
	 * xml中的属性名
	 */
	public String xmlname;
	/**
	 * 对应于数据库中的字段名
	 */
	public String colname;
	/**
	 * 对应数据节点在数据库中的指定的存储过程呼叫字符串
	 */
	public String tablename;
	/**
	 * 节点数据id
	 */
	public int nodeid = -1;
	/**
	 * 模板id
	 */
	public int tempid;
	/**
	 * 父节点数据id，程序中未使用该字段，且数据库不再保证该字段的正确性
	 */
	public int pnodeid = -1;
	/**
	 * 节点名称
	 */
	public String ename;
	/**
	 * 输出值
	 */
	public String outvalue;
	/**
	 * 输入值
	 */
	public String invalue;
	/**
	 * 默认值
	 */
	public String defaultvalue;
	/**
	 * 表示模板值的来源
	 * <p>
	 * 取值：
	 * <p>
	 * 1.父元素的值的键名 keyname（如<code>nodeid</code>, <code>netid</code>,
	 * <code>objType</code>..)
	 * <p>
	 * 2.<code>prgm</code>（表示值来源于树记录）
	 * <p>
	 * 3.<code>data_curr_row</code>（指定attr.curr模板的值来源）
	 * <p>
	 * 4.<code>data_total_row</code>（指定attr.total模板的值来源）
	 * <p>
	 * 5.兄弟元素的值的键名<code>brother.keyname</code>如（<code>brother.nodeid</code>..)
	 * <p>
	 */
	public String datafrom;
	/**
	 * 数据类型取值：
	 * <p>
	 * <code>varchar2</code>
	 * <p>
	 * <code>number</code>
	 */
	public String datatype;
	/**
	 * 暂用来描述是否是重复的模板
	 * 
	 * @see com.zjnms.xmlParse.ElmtModel#isRepeat()
	 */
	public String placeholder;

	protected XmlModel xmlModel;
	protected int entityIndex = -1;
	protected ElmtModel parentElmtModel;

	private boolean hasXmlName = false;
	private boolean hasColName = false;
	private boolean hasTableName = false;
	private boolean hasDataFrom = false;
	private boolean hasDefaultValue = false;
	private boolean hasOutValue = false;
	private boolean hasInValue = false;

	public EntityModel(XmlModel xmlModel, int entityIndex,
			ElmtModel parentElmtModel) {
		this.xmlModel = xmlModel;
		this.entityIndex = entityIndex;
		this.parentElmtModel = parentElmtModel;
	}

	/**
	 * 判定模板具有的属性
	 * 
	 */
	public void judge() {
		if (this.xmlname != null)
			this.hasXmlName = true;
		if (this.colname != null)
			this.hasColName = true;
		if (this.tablename != null)
			this.hasTableName = true;
		if (this.datafrom != null)
			this.hasDataFrom = true;
		if (this.defaultvalue != null)
			this.hasDefaultValue = true;
		if (this.outvalue != null)
			this.hasOutValue = true;
		if (this.invalue != null)
			this.hasInValue = true;
	}

	/**
	 * 判断模板是否有xmlname字段
	 * 
	 * @return
	 */
	public boolean hasXmlName() {
		return this.hasXmlName;
	}

	/**
	 * 判断模板是否有colname字段
	 * 
	 * @return
	 */
	public boolean hasColName() {
		return this.hasColName;
	}

	/**
	 * 判断模板是否有tablename字段
	 * 
	 * @return
	 */
	public boolean hasTableName() {
		return this.hasTableName;
	}

	/**
	 * 判断模板是否有datafrom字段
	 * 
	 * @return
	 */
	public boolean hasDataFrom() {
		return this.hasDataFrom;
	}

	/**
	 * 判断模板是否有outvalue字段
	 * 
	 * @return
	 */
	public boolean hasOutValue() {
		return this.hasOutValue;
	}

	/**
	 * 判断模板是否有defaultvalue字段
	 * 
	 * @return
	 */
	public boolean hasDefaultValue() {
		return hasDefaultValue;
	}

	/**
	 * 判断模板是否有invalue字段
	 * 
	 * @return
	 */
	public boolean hasInValue() {
		return this.hasInValue;
	}

	/**
	 * 获取模板的xmlname字段
	 * 
	 * @return
	 */
	public String getXmlName() {
		return this.xmlname;
	}

	/**
	 * 获取模板的colname字段
	 * 
	 * @return
	 */
	public String getColName() {
		return this.colname;
	}

	/**
	 * 获取模板的tablename字段
	 * 
	 * @return
	 */
	public String getTableName() {
		return this.tablename;
	}

	/**
	 * 获取模板的outvalue字段
	 * 
	 * @return
	 */
	public String getOutValue() {
		return this.outvalue;
	}

	/**
	 * 获取模板的invalue字段
	 * 
	 * @return
	 */
	public String getInValue() {
		return this.invalue;
	}

	/**
	 * 获取模板的defaultvalue字段
	 * 
	 * @return
	 */
	public String getDefaultValue() {
		return this.defaultvalue;
	}

	/**
	 * 模板的字符串描述
	 */
	public String toString() {
		return this.xmlModel.toString() + " : " + this.nodeid + "."
				+ this.ename;
	}

	/**
	 * 实现Cloneable接口的clone方法
	 */
	public Object clone() {
		EntityModel o = null;
		try {
			o = (EntityModel) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return o;
	}

	/**
	 * 获取该模板的父模板对象
	 * 
	 * @return
	 */
	public EntityModel getParent() {
		int nodeid = this.pnodeid;
		//ElmtModel parent = null;
		try {
			ElmtModel parent = (ElmtModel) this.xmlModel.getEntityModelById(nodeid);
			return parent;
		} catch (ClassCastException e) {
			System.out.println(this.toString() + " 的pnode不是 一个可作为pnode的模板");
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取模板次序中位于该模板的前一个位置的模板对象
	 * 
	 * @return
	 */
	public EntityModel getPrevModel() {
		int prevEntityIndex = --this.entityIndex;
		return this.xmlModel.getEntityModelByIndex(prevEntityIndex);
	}

	/**
	 * 将模板的信息拷贝到指定的模板对象
	 * 
	 * @param model
	 */
	public void copyInfo2(EntityModel model) {
		model.colname = this.colname;
		model.datafrom = this.datafrom;
		model.datatype = this.datatype;
		model.defaultvalue = this.defaultvalue;
		model.ename = this.ename;
		model.invalue = this.invalue;
		model.nodeid = this.nodeid;
		model.outvalue = this.outvalue;
		model.placeholder = this.placeholder;
		model.pnodeid = this.pnodeid;
		model.tablename = this.tablename;
		model.tempid = this.tempid;
		model.xmlname = this.xmlname;
	}

	/**
	 * 识别实体模板确切的模板类型，返回确切对象的副本
	 * 
	 * @return
	 * @throws Exception
	 */
	public EntityModel recognize() {
		EntityModel model = null;

		if (ename.equals("node")) {
			model = new ElmtModel(this.xmlModel, this.entityIndex,
					this.parentElmtModel);
			this.copyInfo2(model);
		}
		if (ename.equals("attr")) {
			model = new AttrModel(this.xmlModel, this.entityIndex,
					this.parentElmtModel);
			this.copyInfo2(model);
			model = model.recognize();
		}
		if (ename.equals("sqlparam")) {
			model = new SqlparamModel(this.xmlModel, this.entityIndex,
					this.parentElmtModel);
			this.copyInfo2(model);
			model = model.recognize();
		}
		if (model == null)
			new Exception("ename: " + ename + " 是一个无效的关键字").printStackTrace();
		return model;
	}
}
