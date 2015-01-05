package com.zjnms.xmlParse;

import java.util.ArrayList;
import java.util.List;

/**
 * 将元素模板与xml文件元素绑定的对象
 * 
 * @author llj
 * 
 */
public class Model_Elmt_Binder {
	// XmlModel中node数据节点的指向对象
	private ElmtModel elmtModel;
	// Xml文件中元素的指向对象
	private XmlPoint xmlPoint;
	private boolean neededWriteDB = false;

	/**
	 * 构造绑定了xml文件 和 node节点模板 的 联合指向对象
	 * 
	 * @param xmlPoint
	 *            xml文件对象
	 * @param nodePoint
	 *            xml模板
	 */
	public Model_Elmt_Binder(XmlPoint xmlPoint, ElmtModel nodePoint) {
		this.xmlPoint = xmlPoint;
		this.elmtModel = nodePoint;
		neededWriteDB = nodePoint.hasProcedure();
	}

	/**
	 * 获取XmlModel中node数据节点的指向对象
	 * 
	 * @return
	 */
	public ElmtModel getElmtModel() {
		return elmtModel;
	}

	/**
	 * 获取Xml文件中元素的指向对象
	 * 
	 * @return
	 */
	public XmlPoint getXmlPoint() {
		return xmlPoint;
	}

	/**
	 * 下移指向
	 * 
	 * @return 已经指向终端，返回false
	 * @throws Exception
	 */
	public List<Model_Elmt_Binder> getChildPoints() {
		// ------------------debug---------------------------
		// if(this.elmtModel.nodeid == 228)
		// System.out.println("");
		List<ElmtModel> nodePoints = this.elmtModel.getChildElmtModels();

		List<XmlPoint> xmlPoints = this.xmlPoint.getChildPoints();
		List<Model_Elmt_Binder> childUnionPoints = new ArrayList<Model_Elmt_Binder>();
		if (xmlPoints.size() == 0 ^ nodePoints.size() == 0) {
			new Exception(this.elmtModel.toString()
					+ " 的子模板与xml字符串描述的不一致，请检查是否正确填写了pnodeid和nodeid").printStackTrace();
			// return childUnionPoints;
		}
		Model_Elmt_Binder unionPoint = new Model_Elmt_Binder(null,null);
		for(int i =0;i<xmlPoints.size();++i){
			XmlPoint xmlPoint = xmlPoints.get(i);
			boolean find = false;
			for(int j=0;j<nodePoints.size();++j){
				ElmtModel model = nodePoints.get(j);
				if(model.isRepeat())
					continue;
				if(xmlPoint.getElmtName().equals(model.xmlname)){
					 if(model.defaultvalue != null && !xmlPoint.getElmtDataName().equals(model.defaultvalue))
						 continue;
					 unionPoint = new Model_Elmt_Binder(xmlPoint, model);
					 childUnionPoints.add(unionPoint);
					 find = true;
					 nodePoints.remove(j);
					 break;
				}
			}
			if(!find)
				new Exception("未找在模板中找到元素: " + xmlPoint.toString()
						+ " ，请检查模板和xml字符串").printStackTrace();
		}
		
		// 对重复描述模板进行配对
		for(int i=0;i<nodePoints.size();++i){
			ElmtModel model = nodePoints.get(i);
			if(!model.isRepeat())
				continue;
			for(int j=0;j<xmlPoints.size();++j){
				XmlPoint xmlPoint = xmlPoints.get(j);
				if(xmlPoint.getElmtName().equals(model.xmlname)){
					 if(model.defaultvalue != null && !xmlPoint.getElmtDataName().equals(model.defaultvalue))
						 continue;
					 unionPoint = new Model_Elmt_Binder(xmlPoint, model);
					 childUnionPoints.add(unionPoint);
					 break;
				}
			}
		}
		return childUnionPoints;
	}

	public List<Model_Elmt_Binder> getChildPoints2() throws XmlAndModelNotMatching, NotFindElmtDescribeByModel {
		List<ElmtModel> models = this.elmtModel.getChildElmtModels();
		List<XmlPoint> xmlPoints = this.xmlPoint.getChildPoints();
		validateXmlAndModel(models, xmlPoints);

		List<Model_Elmt_Binder> childBinders = new ArrayList<Model_Elmt_Binder>();
		for (int i = 0; i < models.size(); ++i) {
			ElmtModel nodePoint = models.get(i);
			Model_Elmt_Binder binder = new Model_Elmt_Binder(null,null);
			
			if (nodePoint.isRepeat() && i != 0) {
				ElmtModel prev = models.get(i - 1);
				XmlPoint prevBindingSource = prev.getBindinger().getXmlPoint();
				binder = new Model_Elmt_Binder(prevBindingSource, nodePoint);
			} else {
				XmlPoint source = nodePoint.getBindingSource(xmlPoints);
				if (source == null)
					throw new NotFindElmtDescribeByModel(nodePoint);
				binder = nodePoint.bindingElmts(source);
			}
			childBinders.add(binder);
		}
		return childBinders;
	}
	/**
	 * 
	 * @param nodePoints
	 * @param xmlPoints
	 * @throws XmlAndModelNotMatching
	 */
	private void validateXmlAndModel(List<ElmtModel> nodePoints,
			List<XmlPoint> xmlPoints) throws XmlAndModelNotMatching {
		if (xmlPoints.size() == 0 ^ nodePoints.size() == 0)
			throw new XmlAndModelNotMatching(this.elmtModel);
	}

	// private void binding(ElmtModel model){
	// if (model.isRepeat() && i != 0) {
	// ElmtModel prev = nodePoints.get(i - 1);
	// XmlPoint prevBindingSource = prev.getBindinger().getXmlPoint();
	// Model_Elmt_Binder binder = new Model_Elmt_Binder(
	// prevBindingSource, model);
	// childBinders.add(binder);
	// } else {
	// XmlPoint source = model.getBindingSource(xmlPoints);
	// if (source == null)
	// throw new NotFindElmtDescribeByModel(model);
	// Model_Elmt_Binder binder = model.bindingElmts(source);
	// childBinders.add(binder);
	// }
	// }

	/**
	 * 帮助它的兄弟（为兄弟IPM_UnionPoint对象传递需要的数据）
	 * 
	 * @return
	 */
	public boolean helpBrother(Model_Elmt_Binder requester) {
		if (requester.elmtModel.getRequesetBrotherFieldNames().size() == 0)
			return false;
		return requester.requestDataFromBrother(this);
	}

	private boolean requestDataFromBrother(Model_Elmt_Binder response) {
		List<String> names = this.elmtModel.getRequesetBrotherFieldNames();
		List<MyElement> req_elmts = this.xmlPoint.getCurrXmlElmts();
		List<MyElement> resp_elmts = response.xmlPoint.getCurrXmlElmts();

		for (int z = 0; z < req_elmts.size(); ++z) {
			MyElement req_elmt = req_elmts.get(z);
			boolean find = false;
			for (int i = 0; i < resp_elmts.size(); ++i) {
				MyElement resp_elmt = resp_elmts.get(i);

				if (MyElement.equalParent(req_elmt, resp_elmt)) {
					for (int j = 0; j < names.size(); ++j) {
						String value = resp_elmt.getDBKeyValue(names.get(j));
						if (value == null) {
							// System.out.println("未从兄弟元素中找到指定的字段");
							find = false;
							break;
						}
						DBKey key = new DBKey(names.get(j), value);
						req_elmt.addForeignKey(key);
						if (j == names.size() - 1)
							find = true;
					}

				} else
					find = false;
				if(find)
					break;
			}
			if (!find)
				return false;
		}
		return true;
	}

	public boolean hasSameParentElmt(Model_Elmt_Binder compare) {
		List<MyElement> req_elmts = this.getXmlPoint().getCurrXmlElmts();
		List<MyElement> resp_elmts = compare.getXmlPoint().getCurrXmlElmts();
		if (resp_elmts.size() != 0 && req_elmts.size() != 0)
			return MyElement.equalParent(req_elmts.get(0), resp_elmts.get(0));
		return false;
	}

	public boolean neededWriteDB() {
		return this.neededWriteDB;
	}

	public void handleAccompanyModel() {
		if (neededWriteDB)
			return;
		if (!this.elmtModel.hasAccompanyModel())
			return;
		List<EntityModel> entities = elmtModel.getAccompanyModels();
		List<MyElement> elmts = this.xmlPoint.getCurrXmlElmts();
		for (int i = 0; i < entities.size(); ++i) {
			EntityModel entity = entities.get(i);
			if (entity instanceof AttrModel) {
				for (int j = 0; j < elmts.size(); ++j) {
					MyElement elmt = elmts.get(j);
					elmt.transferAttrToAllBrother(entity.xmlname);
				}
			}
		}
	}

}
