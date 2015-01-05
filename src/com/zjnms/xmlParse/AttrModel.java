package com.zjnms.xmlParse;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * 表示模板中的属性模板，在数据表Tbbase_Interdesc中，ename指定为attr的模板为属性模板
 * 
 * @author llj
 * 
 */
public class AttrModel extends EntityModel implements TreeRecordRequester {
	private boolean hasDataFromPrgm = false;
	private boolean hasDataFrom_dataCurrRow = false;
	private boolean hasDataFrom_dataTotalRow = false;
	private boolean hasDataFrom_Identify = false;
	private boolean hasRequestBrother = false;

	public AttrModel(XmlModel xmlModel, int entityIndex,
			ElmtModel parentElmtModel) {
		super(xmlModel, entityIndex, parentElmtModel);
	}

	/**
	 * 获取从数据来源中查找值的键名
	 * 
	 * @return
	 */
	public String getDataFromFieldName() {
		// -------------------------------------------------------------------------------
		// 
		// -------------------------------------------------------------------------------
		if (!this.hasDataFrom())
			new ModelException(this, "datafrom为空").printStackTrace();

		if (hasRequestBrother)
			return getDataFromBrotherFieldName();
		else if (hasDataFromPrgm)
			return getKeyNameWhenDataFromPrgm();
		else if (!hasDataFrom_dataCurrRow && !hasDataFrom_dataTotalRow)
			return datafrom;
		else
			new ModelException(this, this.toString() + ": dataform指定的值没有对应的字段名")
					.printStackTrace();

		return "";
	}

	/**
	 * 数据来源于兄弟元素时（datafrom=brother.*），获取从元素对象（MyElement）中检索值的键名
	 * 
	 * @return
	 */
	private String getDataFromBrotherFieldName() {
		// --------------------------------------------------------------------------------
		// 如果 datafrom 字段下指定的是一个键名，如 nodeid ，那么模板会以 nodeid 为键到其
		// 直接父元素（MyElement对象）下去检索最新的 nodeid ，但有时子元素模板需要其兄弟元素模板而
		// 非其父元素模板向其传递数据，此时指定 datafrom 的格式是 brother.nodeid ，模板请求
		// 数据的对象也将是一个已处理的兄弟元素
		// --------------------------------------------------------------------------------
		return datafrom.substring(datafrom.indexOf(".") + 1);
	}

	/**
	 * 数据来源于prgm时（datafrom=prgm），获取从树记录中检索值的键名
	 * 
	 * @return
	 */
	private String getKeyNameWhenDataFromPrgm() {
		// ----------------------------------------------------------------------------------
		// 数据来源于prgm（树记录 TreeRecord 对象）时，模板会先试取其 colname 作为检索键，
		// 若 colname == null ，模板再以 xmlname 作为检索键，当 colname 和 xmlname 都为空时，
		// 模板没有可作为检索键的字段，程序打印一个异常消息
		// ----------------------------------------------------------------------------------
		if (this.colname != null)
			return this.colname;
		else if (this.xmlname != null)
			return this.xmlname;

		new Exception(this.toString() + ": colname和xmlname都为空，无法获取键名")
				.printStackTrace();
		return null;
	}

	/**
	 * 判断模板的数据是否来源于树记录
	 * 
	 * @return
	 */
	public boolean dataFromPrgm() {
		return this.hasDataFromPrgm;
	}

	/**
	 * 判断模板的数据值是否来源于描述的数据的当前的行数
	 * 
	 * @return
	 */
	public boolean dataFrom_dataCurrRow() {
		return this.hasDataFrom_dataCurrRow;
	}

	/**
	 * 判断模板的数据值是否来源于描述的数据总行数
	 * 
	 * @return
	 */
	public boolean dataFrom_dataTotalRow() {
		return this.hasDataFrom_dataTotalRow;
	}

	/**
	 * 判断模板的数据值是否来源于一个唯一标识
	 * 
	 * @return
	 */
	public boolean dataFromIdentify() {
		return this.hasDataFrom_Identify;
	}

	/**
	 * 判断模板是否是代表了xml元素中的一个真实的属性
	 * 
	 * @return
	 */
	public boolean realXmlAttr() {
		return this.xmlname != null;
	}

	/**
	 * 获取该属性模板输出到树记录时的键名
	 * 
	 * @return
	 */
	public String getOutputKey() {
		// ---------------------------------------------------------------------------------------------------
		// 有时属性模板（AttrModel）可能需要输出模板描述的元素属性的值到树记录（TreeRecord)对象，这是为了使
		// 客户程序可以不用在解析接口协议后再次读取数据库获取接口发送过来的对象值。此时，为属性模板的 outvalue
		// 字段给一个任意的值来说明该模板描述的属性值是需要输出的，输出值的键优先取模板的 xmlname，若xmlname为空
		// 则取 colname 作为值的键。当 colname 和 xmlname 都为空时，模板没有可作为检索键的字段，程序打印一个异常消息
		// ---------------------------------------------------------------------------------------------------
		if (!this.hasOutValue())
			new Exception(this.toString() + ": 属性模板不输出").printStackTrace();
		else if (this.xmlname != null)
			return this.xmlname;
		else if (this.colname != null)
			return this.colname;
		else
			new ModelException(
					this,
					this.toString()
							+ ": 输出属性时，必须以xmlname或colname为键（xmlname优先级高于colname），请保证其一不为空")
					.printStackTrace();
		return null;
	}

	/**
	 * 是否向兄弟元素请求传值
	 * <p>
	 * tbbase_interdesc 的 dataform 字段描述了 attrmodel 是否向兄弟元素（同级元素）请求字段值
	 * <P>
	 * 
	 * @see com.zjnms.xmlParse.EntityModel
	 * @return
	 */
	public boolean hasRequestBrother() {
		return hasRequestBrother;
	}

	public void judge() {
		super.judge();
		if (this.hasDataFrom()) {
			if (this.datafrom.indexOf("brother.") != -1)
				hasRequestBrother = true;
			if (this.datafrom.equals("prgm"))
				this.hasDataFromPrgm = true;
			if (this.datafrom.equals("data_curr_row"))
				this.hasDataFrom_dataCurrRow = true;
			if (this.datafrom.equals("data_total_row"))
				this.hasDataFrom_dataTotalRow = true;
			if (this.datafrom.equals("identify"))
				this.hasDataFrom_Identify = true;
		}
	}

	/**
	 * 读取模板在树记录中对应的组记录
	 */
	public void requestGroupRecords(TreeRecord tree) {
		if (!hasDataFromPrgm)
			new ModelException(this, "datafrom != prgm，不能从外部获取记录")
					.printStackTrace();

		// 从树记录中获取存储过程参数组记录
		if (!this.parentElmtModel.hasReadGroupRecord()) {
			List<Record> rcds = null;
			GroupRecordIterator iter = tree.getGroupRecordIterator();
			//revise
			if(iter == null)
				return;
			while (true) {
				rcds = iter.getCurrRecords();
				if (rcds.size() == 0)
					// new Exception("迭代对象没有当前指向的对象").printStackTrace();
					return;
				else {
					if (!isMatching(rcds.get(0))) {
						boolean success = iter.nextGroup();
						if (!success)
							new ModelException(this, "模板中不存在树记录中的 "+notFindKey+" 字段")
									.printStackTrace();
					} else
						break;
				}
			}
			iter.nextGroup();
			this.parentElmtModel.setGroupRecord(rcds);
		}
	}
	private String notFindKey=null;
	private boolean isMatching(Record record) {
		List<EntityModel> models = this.parentElmtModel.getAccompanyModels();
		Set<String> keys = record.getKeys();
		for (Iterator<String> keyIter = keys.iterator(); keyIter.hasNext();) {
			String key = keyIter.next();
			boolean findKey = false;
			for (DataFromPrgmAttrModelIterator attrIter = new DataFromPrgmAttrModelIterator(
					models); attrIter.hasNext();) {
				AttrModel attr = (AttrModel) attrIter.next();
				// String name =attr.getDataFromFieldName();
				if (key.equals(attr.getDataFromFieldName())) {
					findKey = true;
					break;
				}
			}
			if (!findKey){
				notFindKey= key;
				return false;
			}
			// throw new Exception("集合中指定的数据: "+key+" 在模板中不存在");
		}
		return true;
	}

	public EntityModel recognize() {
		//revise
		if (xmlname != null && xmlname.equals("total")) {
			EntityModel model = new TotalAttrModel(this.xmlModel, this.entityIndex,
					this.parentElmtModel);
			this.copyInfo2(model);
			return model;
		} else
			return this;
	}

}
