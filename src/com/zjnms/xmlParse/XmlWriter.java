package com.zjnms.xmlParse;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException; //import java.text.DecimalFormat;
import java.util.ArrayList; //import java.util.Iterator;
import java.util.List;
import java.util.Date;
import org.dom4j.Document;
import org.dom4j.Element;

public class XmlWriter {
	// private Xml xml;
	private List<MyElement> location;
	private int currAttrValue = 0;
	private List<Record> records = null;

	// private List<EntityModel> sqlparam_in = new ArrayList<EntityModel>();

	public XmlWriter() {
		// this.xml = xml;
		location = new ArrayList<MyElement>();
	}

	/**
	 * 获取当前写入点的位置（在哪种元素下写入，同种元素可能有多个元素）
	 * 
	 * @return
	 */
	public List<MyElement> getLocation() {
		return location;
	}

	/**
	 * 把xml元素指向对象指向的位置作为写入位置
	 * 
	 * @param point
	 *            xml元素指向对象
	 */
	public void setLocation(XmlPoint point) {
		this.location = point.getCurrXmlElmts();
	}

	/**
	 * 写根元素
	 * 
	 * @return 操作结果
	 */
	public MyElement writeRoot(Document doc, ElmtModel rootModel,
			TreeRecord records) {
		// if (location.size() != 0)
		// return false;
		// 获得根元素节点模板
		// EntityModel node = rootPoint.currNodeModel();
		// 根元素节点的父节点id为0
		// if (rootPoint == null || node == null || node.pnodeid != 0) {
		// System.out.println("请确保模板中根元素的pnodeid=0");
		// return false;
		// }
		// 调用dom4j，为指定元素增加一个子元素，返回新增的子元素
		Element root = doc.addElement(rootModel.xmlname);
		List<EntityModel> accompanies = rootModel.getAccompanyModels();
		for (int i = 0; i < accompanies.size(); ++i) {
			EntityModel accompany = accompanies.get(i);
			// 写根节点的默认属性
			if (accompany instanceof AttrModel) {
				AttrModel attr = (AttrModel) accompany;
				if (attr.xmlname != null) {
					if (attr.hasDefaultValue())
						root.addAttribute(accompany.xmlname,
								accompany.defaultvalue);
					else {
						if (attr.dataFromIdentify())
							root.addAttribute(accompany.xmlname, this
									.createIdentify());
						if (attr.dataFromPrgm()) {
							attr.requestGroupRecords(records);
							String sId = rootModel.getGroupRecord().get(0)// ((ElmtModel)attr.getParent()) revise
									.getRecordFieldValue(
											attr.getDataFromFieldName());
							if (sId == null)
								new Exception("数据集合中关于根节点的记录应该有且只有一条").printStackTrace();
							root.addAttribute(attr.xmlname, sId);
						}
					}
				}
			}
		}
		// 设置写入点为根节点
		MyElement myRoot = new MyElement(root, null);
		location.add(myRoot);
		return myRoot;
	}

	/**
	 * 在 当前写入点 下写入nodePoint所代表的元素
	 * 
	 * @param elmtModel
	 * 
	 * @return 返回写出的xml元素的指向对象
	 */
	public XmlPoint writeElmts(ElmtModel elmtModel, TreeRecord tree) {
		XmlPoint xmlPoint = new XmlPoint();
		for (int i = 0; i < location.size(); ++i) {
			MyElement elmt = location.get(i);
			// 增加子元素
			List<MyElement> elmts =  new ArrayList<MyElement>();//revise
			elmts = addElements(elmt, elmtModel, tree, i);
			// 将所有同类子元素增加到指向对象
			for (int j = 0; j < elmts.size(); ++j) {
				xmlPoint.addCurrXmlElmt(elmts.get(j));
			}
		}
		return xmlPoint;
	}

	/**
	 * 读取存储过程参数的名称和类型
	 * <p>
	 * 存储过程输入参数位于模板的invalue字段，存储过程输出参数位于模板的outvalue字段，所有参数的类型位于模板的datatype字段，以
	 * ","分隔，描述的顺序是从输入参数到输出参数
	 * <p>
	 * example: invalue:netid,nodeid outvalue:proname
	 * datatype:number,number,varchar2
	 * 
	 * @param node
	 *            node数据节点
	 */

	private List<MyElement> addElements(MyElement parent, ElmtModel elmtModel,
			TreeRecord tree, int index) {
		List<MyElement> elmts = new ArrayList<MyElement>();//revise
		// 以存储过程写入

		// -------------Debug--------------------
		// if (elmtModel.xmlname.equals("line"))
		// System.out.println("");
		if (elmtModel.hasProcedure()) {
			elmts = addElmtByProc(parent, elmtModel, tree);

			// 最后写total属性的值，这个值由子元素的数量确定
			if (elmtModel.hasTotalAttrModel())
			// writeTotalAttrValue(parent.getElmt());
			{
				for (int i = 0; i < elmts.size(); ++i)
					elmts.get(i).getElmt().addAttribute("total",
							Integer.valueOf(elmts.size()).toString());
			}
		} else {
			//elmts = new ArrayList<MyElement>();

			DataFromPrgmAttrModelIterator attrIter = new DataFromPrgmAttrModelIterator(
					elmtModel.getAccompanyModels());

			// 以外部数据集合写入
			if (attrIter.hasNext()) {
				((AttrModel) attrIter.next()).requestGroupRecords(tree);
				// if (index == 0) {
				List<Record> rcds = elmtModel.getGroupRecord();
				// }
				// 数据集合中不包含模板中描述的记录
				if (rcds == null)
					return null;
				this.records = rcds;
				if (records.size() <= index)
					new Exception(
							"树记录小于模板描述中需要的记录，请确保树记录填写无误，且模板的结构无误（尤其是父子关系）")
							.printStackTrace();
				elmts.add(addElmtByExternData(parent, elmtModel, records
						.get(index)));
			} else {
				if (elmtModel.hasTotalAttrModel())
					elmts = writeDescribeNode(parent, elmtModel, tree);
				else {
					elmts.add(addSingleChildElmt(parent, elmtModel));
				}
			}
		}
		return elmts;
	}

	private MyElement addSingleChildElmt(MyElement parent, ElmtModel elmtModel) {
		// Element elmt = parent.getElmt().addElement(elmtModel.xmlname);
		MyElement elmt = parent.createChildMyElement(elmtModel.xmlname);

		for (AttrModelIterator attrIter = new AttrModelIterator(elmtModel
				.getAccompanyModels()); attrIter.hasNext();) {
			AttrModel attr = (AttrModel) attrIter.next();
			if (attr.defaultvalue != null)
				// new Exception(attr.toString()
				// + " 的属性值不来源于数据集合和存储过程时，只能来源于defaultvalue")
				// .printStackTrace();
				elmt.getElmt().addAttribute(attr.xmlname, attr.defaultvalue);
			else if (attr.hasDataFrom()) {
				elmt.getElmt().addAttribute(attr.xmlname,
						elmt.getValue(attr.getDataFromFieldName()));
			}
		}
		// return new MyElement(elmt, parent);
		return elmt;
	}

	private List<MyElement> writeDescribeNode(MyElement parent,
			ElmtModel elmtModel, TreeRecord tree) {
		List<MyElement> elmts = new ArrayList<MyElement>();
		GroupRecordIterator iter = tree.getGroupRecordIterator();
		//revise
		if(iter == null)
			return elmts;
		int count = iter.getCurrRecords().size() / this.location.size();
		for (int i = 0; i < count; ++i) {
			Element elmt = parent.getElmt().addElement(elmtModel.xmlname);
			for (AttrModelIterator attrIter = new AttrModelIterator(elmtModel
					.getAccompanyModels()); attrIter.hasNext();) {

				AttrModel attr = (AttrModel) attrIter.next();
				if (attr.defaultvalue != null)
					elmt.addAttribute(attr.xmlname, attr.defaultvalue);
				if (attr.datafrom != null) {
					if (attr.datafrom.equals("data_curr_row"))
						elmt.addAttribute(attr.xmlname, Integer.valueOf(i + 1)
								.toString());
					if (attr.datafrom.equals("data_total_row"))
						elmt.addAttribute(attr.xmlname, Integer.valueOf(count)
								.toString());
				}
			}
			elmts.add(new MyElement(elmt, parent));
		}
		return elmts;
	}

	/**
	 * 通过执行存储过程写xml元素
	 * 
	 * @param parent
	 * @param elmtModel
	 * @param tag
	 * @return
	 */
	private List<MyElement> addElmtByProc(MyElement parent,
			ElmtModel elmtModel, TreeRecord tree) {
		// 获取存储过程执行对象
		DataAccess da = new DataAccess();
		Connection conn = da.OpenConn();
		if(conn == null)
			return null;//revise

		// 创建存储过程执行对象
		ProcedureProcess proc = null;
		try {
			proc = this.createProcedureProcess(parent, elmtModel, tree);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		//revise
		if(proc == null)
			return null;
		// 执行存储过程，返回结果集
		ResultSet rs = proc.selectInRS(conn);
		if(rs == null)//revise
			return null;
		// 存放新增元素的集合
		List<MyElement> elmts = new ArrayList<MyElement>();
		// List<TreeRecord> leafs = tree.getLeafs();
		// if (leafs.size() != 1)
		// System.out.println("树记录中只应该有单一查询条件记录");
		// TreeRecord param = leafs.get(0);
		this.currAttrValue = 0;
		try {
			// 查询结果增加到子元素属性（子元素的数量由取出的数据条数确定）
			while (rs.next()) {
				++this.currAttrValue;
				// 新增一个子元素
				MyElement elmt = parent.createChildMyElement(elmtModel.xmlname);

				for (SqlOutparamModelIterator paramIter = new SqlOutparamModelIterator(
						elmtModel.getAccompanyModels()); paramIter.hasNext();) {
					SqlOutparamModel outparam = (SqlOutparamModel) paramIter
							.next();
					String value = this.getOutValueFromRS(outparam, rs);
					if (value == null)
						new Exception("查询结果集中未找到模板中指定的输出到数据集合的属性")
								.printStackTrace();
					else
						elmt.addForeignKey(new DBKey(outparam.getOutKey(),
								value));
				}

				for (AttrModelIterator attrIter = new AttrModelIterator(
						elmtModel.getAccompanyModels()); attrIter.hasNext();) {
					addElmtAttr(elmt, (AttrModel) attrIter.next());
				}
				elmts.add(elmt);
			}
			da.CloseConn();
			return elmts;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 通过读取标签元素写xml元素
	 * 
	 * @param parent
	 * @param elmtModel
	 * @param tag
	 * @return
	 */
	private MyElement addElmtByExternData(MyElement parent,
			ElmtModel elmtModel, Record record) {
		Element elmt = parent.getElmt().addElement(elmtModel.xmlname);
		// 为elmt填入属性
		for (AttrModelIterator attrIter = new AttrModelIterator(elmtModel
				.getAccompanyModels()); attrIter.hasNext();) {
			AttrModel attr = (AttrModel) attrIter.next();
			if (attr.xmlname == null)
				continue;
			// 从标签数据写入
			if (attr.datafrom != null) {
				// 实际数据不包含模板描述的属性，不需要写属性
				if (attr.dataFromPrgm()) {
					// if (attr.colname == null) {
					// System.out.println("模板中datafrom = prgm时，colname必须被指定");
					// }
					// 当数据集合中存的是一个从xml中读出的属性时，关键字xmlname;当存的是从数据库中读出的记录时，关键字是colname
					//revise
					String value = record.getRecordFieldValue(attr
							.getDataFromFieldName());
					if (value != null)
						elmt.addAttribute(attr.xmlname, value);
				}
				if (attr.dataFrom_dataCurrRow())
					elmt.addAttribute(attr.xmlname, Integer.valueOf(1)
							.toString());
				// 总数据条数，先留空
				if (attr.dataFrom_dataTotalRow())
					elmt.addAttribute(attr.xmlname, Integer.valueOf(1)
							.toString());
			} else if (attr.hasDefaultValue())
				elmt.addAttribute(attr.xmlname, attr.getDefaultValue());
		}
		return new MyElement(elmt, parent);
	}

	private List<SqlInparamModel> readSqlInParamModel(ElmtModel elmtModel) {
		List<SqlInparamModel> params = new ArrayList<SqlInparamModel>();
		List<EntityModel> entities = elmtModel.getAccompanyModels();
		for (int i = 0; i < entities.size(); ++i) {
			EntityModel entity = entities.get(i);
			if (entity instanceof SqlInparamModel)
				params.add((SqlInparamModel) entity);
		}
		return params;
	}

	private ProcedureProcess createProcedureProcess(MyElement parent,
			ElmtModel elmtModel, TreeRecord records) {
		// 配置存储过程
		ProcedureProcess proc = new ProcedureProcess(elmtModel.tablename);
		List<SqlInparamModel> sqlparam_in = this.readSqlInParamModel(elmtModel);

		// SqlInparamModelIterator attrIter = new AttrModelIterator(nodePoint
		// .getAccompanyModels());
		List<Record> rcds = null;
		if (sqlparam_in.size() != 0) {
			boolean hasReadRecord = false;
			// 增加存储过程的输入参数
			for (int i = 0; i < sqlparam_in.size(); ++i) {
				String value = null;
				SqlInparamModel inparam = sqlparam_in.get(i);
				String key = inparam.getDataFromFieldName();
				if (inparam.dataFromMyElement())
					value = parent.getDBKeyValue(key);
				if (inparam.dataFromPrgm()) {
					if (!hasReadRecord) {
						inparam.requestGroupRecords(records);

						rcds = elmtModel.getGroupRecord();
						if (rcds.size() != 1) {
							new Exception(inparam.toString()
									+ ": 找不到对应的记录值").printStackTrace();
						}
						hasReadRecord = true;
					}
					// 该输入参数为数据集合传入的值
					value = rcds.get(0).getRecordFieldValue(key);
				}
				if (value == null)
					new Exception(elmtModel.toString() + ": 找不到存储过程对应的参数")
							.printStackTrace();

				// 设置存储过程的参数
				proc.addProcParamValue(value, inparam.datatype, 1);
			}
		}
		// 最后增加一个游标参数
		// 约定：输出的存储过程最后一个参数都是一个游标输出参数
		proc.addProcParamValue("", "cursor", -1);
		return proc;
	}

	/**
	 * 从 存储过程读取的数据集 和 标签数据 写入元素的属性
	 * 
	 * @param elmt
	 * @param attr
	 * @param rs
	 */
	private void addElmtAttr(MyElement myelmt, AttrModel attr) {
		try {
			Element elmt = myelmt.getElmt();
			// 属性有默认值
			if (attr.defaultvalue != null) {
				elmt.addAttribute(attr.xmlname, attr.defaultvalue);
				return;
			}
			// 属性有数据来源
			String datafrom = attr.datafrom;//revise
			if (datafrom != null) {
				if (datafrom.equals("data_curr_row"))
					elmt.addAttribute(attr.xmlname, Integer.valueOf(
							this.currAttrValue).toString());
				// 总数据条数，先留空
				if (datafrom.equals("data_total_row"))
					elmt.addAttribute(attr.xmlname, "");
				// if (attr.datafrom.equals("prgm"))
				// addElmtAttrFromTagData(elmt, attr, recordIter);
				return;
			}
			// 属性指定到列名（通过存储过程返回的结果集查得）
			if (attr.colname != null) {
				elmt.addAttribute(attr.xmlname, myelmt.getDBKeyValue(
						(attr).getDataFromFieldName()));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	private String getOutValueFromRS(EntityModel attr, ResultSet rs) {
		String value = null;
		try {
			if (attr.outvalue != null && attr.colname != null) {
				Object obj = rs.getObject(attr.colname);
				value = obj != null ? obj.toString() : "";
			} else
				System.out.println("模板有输出到数据集合的属性时，outvalue 和 colname不能为空");
		} catch (SQLException e) {
			System.out.println(attr.toString() + " 出错");
			e.printStackTrace();
		}
		return value;
	}

	/**
	 * 创建一个唯一标识序列
	 * 
	 * @return
	 */
	private String createIdentify() {
		Date date = new Date();
		String id = date.toString().replace(' ', '-');//revise
		id = id.replace(':', '-');
		// DecimalFormat df = (DecimalFormat)DecimalFormat.getInstance();
		// return df.format(1);
		return id;
	}

	// public static void main(String[] args) {
	// Xml xml = Xml.createEmptyXml();
	// XmlWriter writer = new XmlWriter();
	// String id = writer.createIdentify();
	// System.out.println(id);
	// }

}
