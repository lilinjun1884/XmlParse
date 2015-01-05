package com.zjnms.xmlParse;

//import java.io.Serializable;
import java.util.*;

/**
 * 统一描述xml文件的模版对象
 * 
 * @author llj
 * 
 */
public class XmlModel implements Cloneable {
	/**
	 * 指定模板的根节点的父节点id
	 */
	private static int rootModelPNodeId = 0;
	/**
	 * 模板ID
	 */
	public int tempid;
	/**
	 * 接口标识码
	 */
	public String code;
	/**
	 * 接口中文名称
	 */
	public String iname;
	/**
	 * 数据方向
	 */
	private String direct;
	/**
	 * 编码格式
	 */
	public String decodes;
	/**
	 * 输出格式
	 */
	public String isfile;
	public List<EntityModel> entities;
	private XmlWriter writer;

	public XmlModel() {

		entities = new ArrayList<EntityModel>();
	}

	public void addEntityModel(EntityModel model) {
		entities.add(model);
	}

	public String toString() {
		return this.tempid + "." + this.code + "." + this.direct;
	}

	// public void deleteNodeModel(int nodename)
	// {
	// nodeModels.remove(nodename);
	// }
	/**
	 * 获取第一个节点数据模板
	 * 
	 * @return 没有则返回null
	 */
	public ElmtModel getRootEntity() {

		for (int i = 0; i < this.entities.size(); ++i) {
			EntityModel entity = entities.get(i);
			if (entity.pnodeid == XmlModel.rootModelPNodeId
					&& entity instanceof ElmtModel)
				return (ElmtModel) entity;
		}
		new Exception("未在模板中找到 " + this.toString()
				+ " 的根节点模板，请确保唯一指定了一个根节点模板，其 pnodeid = 0").printStackTrace();
		return null;
	}

	/**
	 * 通过模板id获取模板实例
	 * 
	 * @param nodeid
	 * @return
	 */
	public EntityModel getEntityModelById(int nodeid) {
		for (int i = 0; i < this.entities.size(); ++i) {
			EntityModel entity = entities.get(i);
			if (entity.nodeid == nodeid)
				return entity;
		}
		return null;
	}

	/**
	 * 通过模板在集合中的下标获取模板实例
	 * 
	 * @param entityIndex
	 * @return
	 */
	public EntityModel getEntityModelByIndex(int entityIndex) {
		if (entityIndex < 0 || entityIndex >= this.entities.size())
			return null;
		return this.entities.get(entityIndex);
	}

	/**
	 * 实现Cloneable接口的clone方法（深克隆）
	 */
	public Object clone() {
		XmlModel o = null;
		try {
			// 克隆XmlModel对象的直接成员
			o = (XmlModel) super.clone();
			List<EntityModel> entities = new ArrayList<EntityModel>();
			o.entities = entities;
			// 克隆XmlModel对象引用的EntityModel实例集合
			for (int i = 0; i < this.entities.size(); ++i) {
				EntityModel iter = (EntityModel) this.entities.get(i).clone();
				//revise16
				if(iter == null)
					return null;
				// 重新定位EntityModel实例的引用
				iter.xmlModel = o;
				// 重新定位EntityModel实例的父元素模板
				iter.parentElmtModel = (ElmtModel) iter.getParent();
				entities.add(iter);
			}
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return o;
	}

	public String getIName() {
		return this.iname;
	}

	public void setINmae(String iname) {
		this.iname = iname;
	}

	public void setIsfile(String isfile) {
		this.isfile = isfile;
	}

	public String getIsfile() {
		return this.isfile;
	}

	public void setTempid(int tempid) {
		this.tempid = tempid;
	}

	public int getTempid() {
		return this.tempid;
	}

	public void setDecodes(String decodes) {
		this.decodes = decodes;
	}

	public String getDecodes() {
		return this.decodes;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCode() {
		return this.code;
	}

	public void setDirect(String direct) {
		if (direct == null)
			new NullPointerException("模板的输入输出方向不能为空").printStackTrace();
		if (!direct.equals("来自接入节点的输入") && !direct.equals("来自台站的输入")
				&& !direct.equals("来自综合网管的输入") && !direct.equals("来自网络管理设备的输入")
				&& !direct.equals("来自接入控制器的输入") && !direct.equals("来自下级网管的输入")
				&& !direct.equals("到接入节点的输出") && !direct.equals("到台站的输出")
				&& !direct.equals("到综合网管的输出") && !direct.equals("到网络管理设备的输出")
				&& !direct.equals("到接入控制器的输出") && !direct.equals("到下级网管的输出")
				&& !direct.equals("到上级网管的输出"))
			new Exception("数据错误，不存在为 " + direct + "的输入输出方向").printStackTrace();
		this.direct = direct;
	}

	/**
	 * 获取模板的输入输出方向
	 * 
	 * @return
	 */
	public TransferDirect getDirect() {
		if (this.direct.equals("来自接入节点的输入"))
			return TransferDirect.INPUT_FROM_JOINNODE;
		if (this.direct.equals("来自台站的输入"))
			return TransferDirect.INPUT_FROM_STATION;
		if (this.direct.equals("来自综合网管的输入"))
			return TransferDirect.INPUT_FROM_WEBMASTER;
		if (this.direct.equals("来自网络管理设备的输入"))
			return TransferDirect.INPUT_FROM_NETMANAGE;
		if (this.direct.equals("来自接入控制器的输入"))
			return TransferDirect.INPUT_FROM_JOINMANAGE;
		if (this.direct.equals("来自下级网管的输入"))
			return TransferDirect.INPUT_FROM_LOWERJOINMANAGE;

		if (this.direct.equals("到接入节点的输出"))
			return TransferDirect.OUTPUT_TO_JOINNODE;
		if (this.direct.equals("到台站的输出"))
			return TransferDirect.OUTPUT_TO_STATION;
		if (this.direct.equals("到综合网管的输出"))
			return TransferDirect.OUTPUT_TO_WEBMASTER;
		if (this.direct.equals("到网络管理设备的输出"))
			return TransferDirect.OUTPUT_TO_NETMANAGE;
		if (this.direct.equals("到接入控制器的输出"))
			return TransferDirect.OUTPUT_TO_JOINMANAGE;
		if (this.direct.equals("到下级网管的输出"))
			return TransferDirect.OUTPUT_TO_LOWERJOINMANAGE;
		if (this.direct.equals("到上级网管的输出"))
			return TransferDirect.OUTPUT_TO_UPERJOINMANAGE;
		return null;
	}

	/**
	 * 通过标签数据生成xml输出文件字符串
	 * 
	 * @param tag
	 *            标签数据
	 * @return xml文件字符串
	 */
	public String generateXml(TreeRecord records) {
		// 创建空xml文件对象
		Xml xml = Xml.createEmptyXml();
		// 设置该文件的数据起始id，该起始id能以链路的形式查询出xml文件对象的所有数据

		// 用模板写入根元素到该xml文件
		MyElement root = writeRoot(xml, records);
		// 构造指向对象：
		// xml文件元素指向对象
		XmlPoint xmlPoint = new XmlPoint();
		// 模板node数据节点指向对象：
		// OPM_NodePoint nodePoint = new OPM_NodePoint(this);

		// ElmtModel elmtModel = new ElmtModel(this, 0, null);
		xmlPoint.initialize(root);
		// nodePoint.initialize();
		// 组记录迭代器
		// GroupRecordIterator recordIter = new GroupRecordIterator(records);
		// 递归写
		ElmtModel rootModel =this.getRootEntity();
		if(rootModel == null)
			return "";
		writeXml(rootModel, xmlPoint, records);
		// 将指向的xml文件以内容字符串返回
		return xml.toString();
	}

	/**
	 * 写该xml文件的根元素
	 * 
	 * @param xml
	 *            空xml文件
	 * @return 操作结果
	 */
	private MyElement writeRoot(Xml xml, TreeRecord records) {
		//revise13
		ElmtModel root = this.getRootEntity();
		if (root == null)//revise14
		   return null;
		// 初始化xml写入对象
		this.writer = new XmlWriter();
		// 写根元素
		try {
			return writer.writeRoot(xml.getDoc(), root, records);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 处理递归写xml文件的方法
	 * 
	 * @param elmtModel
	 * @param xmlPoint
	 */
	private void writeXml(ElmtModel elmtModel, XmlPoint xmlPoint,
			TreeRecord records) {
		//revise
		List<ElmtModel> elmtModel_childs = elmtModel.getChildElmtModels();
		XmlWriter writer = new XmlWriter();
		// 获取所有子元素模板，不存在则退出递归
		if (elmtModel_childs.size() != 0) {
			// 设置写入点为xmlPoint指向的位置
			writer.setLocation(xmlPoint);
			for (int i = 0; i < elmtModel_childs.size(); ++i) {
				// System.out.println(xmlPoint.getXml().toString());
				// 返回本次写的元素指向对象
				XmlPoint newElmt = writer.writeElmts(elmtModel_childs.get(i),
						records);
				// 对每一种元素指向对象递归写
				writeXml(elmtModel_childs.get(i), newElmt, records);
			}
		}
	}
}
