package com.zjnms.xmlParse;

import java.util.ArrayList;
import java.util.List;

/**
 * 表示模板中的元素模板，在数据表Tbbase_Interdesc中，ename指定为node的模板为元素模板
 * 
 * @author llj
 * 
 */
public class ElmtModel extends EntityModel {
	private List<EntityModel> accompanies = new ArrayList<EntityModel>();;
	private List<ElmtModel> childElmtModels = new ArrayList<ElmtModel>();
	private boolean hasReadLower = false;

	private List<String> requesetBrotherFieldNames = new ArrayList<String>();
	private List<String> outputFieldNames = new ArrayList<String>();
	private boolean hasGetFieldNames = false;

	private boolean hasReadGroupRecord = false;
	private List<Record> groupRecord = null;

	private Model_Elmt_Binder bindinger;

	public ElmtModel(XmlModel xmlModel, int entityIndex,
			ElmtModel parentElmtModel) {
		super(xmlModel, entityIndex, parentElmtModel);
	}

	/**
	 * 判断元素模板是否是重复描述一个xml元素的重复模板
	 * <p>
	 * 暂取 tbbase_interdesc 的 placeholder 字段作为描述元素模板是否是重复模板的字段
	 * <p>
	 * 重复模板：如果一个 node 模板与其上一个模板描述了同一个 xml 元素，则该模板是重复模板
	 * <p>
	 * 在 ename = node 的模板的 placeholder 字段填入一个任意的值，表示该 node 是重复上一个node的元素
	 * <p>
	 * 
	 * @return
	 */
	public boolean isRepeat() {
		if (this.placeholder != null)
			return true;
		return false;
	}

	/**
	 * 判断是否存在从属模板
	 * 
	 * @return
	 */
	public boolean hasAccompanyModel() {
		return getAccompanyModels().size() != 0;
	}

	/**
	 * 获取子模板
	 * 
	 * @return
	 */
	public List<ElmtModel> getChildElmtModels() {
		buffering();
		return childElmtModels;
	}

	/**
	 * 获取从属模板
	 * 
	 * @return
	 */
	public List<EntityModel> getAccompanyModels() {
		buffering();
		return accompanies;
	}

	/**
	 * 是否有存储过程调用
	 * 
	 * @return
	 */
	public boolean hasProcedure() {
		// --------------------------------------------------------------------------------------
		// tbbase_interdesc 的 tablename 字段描述存储过程的调用字符串
		// 存储过程调用只在 node 模板上
		// ---------------------------------------------------------------------------------------
		if (this.tablename != null)
			return true;
		return false;
	}

	/**
	 * 是否请求了兄弟元素
	 * 
	 * @return
	 */
	public boolean hasRequestBrother() {
		if (!hasGetFieldNames)
			getFieldNames();
		return requesetBrotherFieldNames.size() != 0;
	}

	/**
	 * 模板下是否有输出到树记录的从属模板
	 * 
	 * @return
	 */
	public boolean hasOutputAccompanyModel() {
		if (!hasGetFieldNames)
			getFieldNames();
		return outputFieldNames.size() != 0;
	}

	/**
	 * 是否请求了树记录
	 * 
	 * @return
	 */
	public boolean hasRequestTreeRecord() {
		DataFromPrgmAttrModelIterator iter = new DataFromPrgmAttrModelIterator(
				this.getAccompanyModels());
		return iter.hasNext();
	}

	public boolean hasTotalAttrModel() {
		List<EntityModel> entities = this.getAccompanyModels();
		for (int i = 0; i < entities.size(); ++i) {
			EntityModel entity = entities.get(i);
			if (entity instanceof TotalAttrModel)
				return true;
		}
		return false;
	}

	public boolean hasDescribeAttrModel() {
		List<EntityModel> entities = this.getAccompanyModels();
		for (int i = 0; i < entities.size(); ++i) {
			EntityModel entity = entities.get(i);
			if (entity instanceof DescribeAttrModel)
				return true;
		}
		return false;
	}

	/**
	 * 获取向兄弟请求的数据的字段名
	 * 
	 * @return
	 */
	public List<String> getRequesetBrotherFieldNames() {
		if (!this.hasGetFieldNames)
			getFieldNames();
		return this.requesetBrotherFieldNames;
	}

	/**
	 * 获取输出到数据集合的键名
	 * 
	 * @return
	 */
	public List<String> getOutputKeyNames() {
		if (!this.hasGetFieldNames)
			getFieldNames();
		return this.outputFieldNames;
	}

	private void getFieldNames() {
		List<EntityModel> accompanies = this.getAccompanyModels();
		for (int i = 0; i < accompanies.size(); ++i) {
			EntityModel entity = accompanies.get(i);
			if (entity instanceof AttrModel) {
				AttrModel attr = (AttrModel) entity;
				if (attr.hasRequestBrother())
					requesetBrotherFieldNames.add(attr.getDataFromFieldName());
				if (attr.hasOutValue())
					try {
						this.outputFieldNames.add(attr.getOutputKey());
					} catch (Exception e) {
						e.printStackTrace();
					}
			}
		}
		this.hasGetFieldNames = true;
	}

	/**
	 * 将元素模板绑定到元素，返回创建的绑定对象
	 * 
	 * @param point
	 * @return
	 */
	public Model_Elmt_Binder bindingElmts(XmlPoint point) {
		return this.bindinger = point.binding2Model(this);
	}

	/**
	 * 获取绑定对象
	 * 
	 * @return
	 */
	public Model_Elmt_Binder getBindinger() {
		return bindinger;
	}

	/**
	 * 从一个源集合中，获取绑定源
	 * 
	 * @return
	 */
	public XmlPoint getBindingSource(List<XmlPoint> sources) {
		for (int i = 0; i < sources.size(); ++i) {
			XmlPoint source = sources.get(i);
			// 当一个源未被绑定到模板，且源的元素名与模板的描述相同时，该源是指定源
			if (!source.hasBinding2Model()
					&& source.getElmtName().equals(this.getXmlName()))
				return source;
		}
		return null;
	}

	// public boolean isModelof(MyElement elmt) {
	// if (elmt.getElmt().getName().equals(this.xmlname)) {
	// AttrModelIterator iter = new AttrModelIterator(this.accompanies);
	// if (!iter.hasNext()) {
	// return true;
	// } else {
	// for (; iter.hasNext();) {
	// AttrModel attr = (AttrModel) iter.next();
	// if (attr.realXmlAttr() && attr.hasDefaultValue()) {
	// String value = elmt.getElmt().attribute(attr.xmlname)
	// .getValue();
	// if (value != null
	// && value.equals(attr.getDefaultValue()))
	// return true;
	// }
	// }
	// }
	// }
	// return false;
	// }

	private void buffering() {
		if (!this.hasReadLower) {
			readLower();
			this.hasReadLower = true;
		}
	}

	// 读取下级实体模板
	private void readLower() {
		accompanies.clear();
		childElmtModels.clear();
		int pnodeid = this.nodeid;
		List<EntityModel> models = this.xmlModel.entities;
		for (int i = this.entityIndex + 1; i < models.size(); ++i) {
			EntityModel model = models.get(i);
			// 约定：当node节点模板需要写数据库时，如果一个属性需要输出到xml，则该属性必须是存库的属性
			if (model.pnodeid == pnodeid) {
				if (model instanceof ElmtModel)
					this.childElmtModels.add((ElmtModel) model);
				else
					this.accompanies.add(model);
			}
		}
	}

	public boolean hasReadGroupRecord() {
		return hasReadGroupRecord;
	}

	public List<Record> getGroupRecord() {
		if (this.groupRecord == null)
			throw new NullPointerException("未读取过该元素模板的组记录");
		return this.groupRecord;
	}

	public void setGroupRecord(List<Record> groupRecord) {
		this.groupRecord = groupRecord;
		this.hasReadGroupRecord = true;
	}
}
