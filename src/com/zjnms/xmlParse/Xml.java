package com.zjnms.xmlParse;

import java.sql.Connection; //import java.sql.ResultSet;
import java.sql.SQLException; //import java.util.ArrayList;
import java.util.ArrayList;
//import java.util.ArrayList;
import java.util.List;
import org.dom4j.*;

/**
 * 表示xml文件内容的对象
 * 
 * @author llj
 * 
 */
public class Xml {
	// 接口标识码所在的xml文件层
	private static int interfaceCodeLevel = 4;
	private Document doc;
	// xml文件读取对象
	// private XmlReader rder;
	// 数据库连接对象
	private Connection conn;

	public Xml(Document doc) {
		this.doc = doc;
		// rder = new XmlReader(doc.getRootElement());
	}

	/**
	 * 导入xml文件
	 * 
	 * @param xmlStr
	 *            xml文件内容字符串
	 * @return 失败时返回
	 */
	public static Xml importXml(String xmlStr) {
		Document doc = Xml.convertToDoc(xmlStr);
		if (doc == null)
			return null;

		return new Xml(doc);
	}

	private static Document convertToDoc(String xmlStr) {
		try {
			// 解析xmlStr内容并返回document对象
			return DocumentHelper.parseText(xmlStr);
		} catch (DocumentException e) {
			System.out.println("无法导入xml字符串，请确保xml格式无误");
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 创建一个空的xml文件对象
	 */
	public static Xml createEmptyXml() {
		return new Xml(DocumentHelper.createDocument());
	}

	/**
	 * 将xml文件对象转换成字符串形式
	 */
	public String toString() {
		// 调用dom4j
		return doc.asXML();
	}

	/**
	 * 根据模板处理xml对象,可能的操作为：
	 * <P>
	 * 写入数据库
	 * <P>
	 * 写入数据集合
	 * <P>
	 * 写入数据库并写入数据集合
	 * 
	 * @param model
	 *            描述xml的模版对象
	 */
	public TreeRecord handleWith(XmlModel model) {
		try {
			XmlPoint xmlPoint = new XmlPoint();
			// IPM_NodePoint nodePoint = new IPM_NodePoint(model);
			ElmtModel rootModel = (ElmtModel) model.getEntityModelByIndex(0);
			if (rootModel == null)
				throw new NullPointerException("找不到根节点");
			// 获取联合指向对象用于写入
			xmlPoint.initialize(this.getRoot());
			Model_Elmt_Binder point = new Model_Elmt_Binder(xmlPoint, rootModel);

			// 打开连接
			this.conn = new DataAccess().OpenConn();
			//revise4
			if(conn == null)
				return null;
			// 取消自动提交，xml文件在该连接上的写入是一个事务
			this.conn.setAutoCommit(false);
			// 递归写入
			TreeRecord tree = new TreeRecord();
			writeNode(point, tree);
			// 存储xml文件的所有数据后提交
			this.conn.commit();
			// 关闭连接
			this.conn.close();
			System.out.println("Done.");
			return tree;
			// revise3
		} catch (ExcuteProcedureException e) {
			System.out.println(e.getMessage());
		} catch (SQLException e) {
			try {
				// 提交时异常
				this.conn.rollback();
				this.conn.close();
			} catch (SQLException e1) {
				// 回滚时异常
				e1.printStackTrace();
			}
		} catch (Exception e) {
			// 
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return null;
	}

	/**
	 * 处理递归写入
	 * 
	 * @param point
	 *            节点模版指向对象
	 * @param model
	 *            描述xml的模板
	 * @return 1.存储模板指定的全部数据退出 2.存储过程中出错退出
	 * @exception ExcuteProcedureException
	 *                写xml文件失败时抛出
	 */
	private void writeNode(Model_Elmt_Binder point, TreeRecord tree)
			throws ExcuteProcedureException {
		try {
			// 写入节点
			// if(point.getElmtModel().xmlname.equals("nameValue"))
			// System.out.println("");
			write(point, tree);
		} catch (ExcuteProcedureException e) {
			// 写入系统日志
			throw e;
		}
		//revise1
		List<Model_Elmt_Binder> childs = new ArrayList<Model_Elmt_Binder>();
		try {
			childs = point.getChildPoints();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		boolean hasWrite = false;
		for (int i = 0; i < childs.size(); ++i) {
			writeNode(childs.get(i), tree);
			// 组织一次兄弟们的会议
			if (!hasWrite) {
				BrotherParty party = new BrotherParty(childs);
				// 如果安排了会议，召开会议，处理它们的数据交互
				if (party.tryOrder())
					hasWrite = party.start();
			}
		}
	}

	/**
	 * 根据模板将节点指定到存储过程
	 * 
	 * @param point
	 *            数据节点的指向对象
	 * @exception ExcuteProcedureException
	 *                写入node数据节点指向的xml元素失败时抛出
	 */
	private void write(Model_Elmt_Binder point, TreeRecord tree)
			throws ExcuteProcedureException {
		// 获取node节点模板对应的所有xml元素（即模板的多条记录）
		List<MyElement> elmts = point.getXmlPoint().getCurrXmlElmts();

		if (point.neededWriteDB()) {
			// 用point指向的node数据节点模板 写入该模板下的xml元素数据
			for (int j = 0; j < elmts.size(); ++j) {
				try {
					MyElement elmt = elmts.get(j);
					// 写到数据库,返回输出参数集合
					List<ProcParam> outParams = writeElmtData(elmt, point);
					if (outParams != null)
						// 将输出参数集合传递到元素
						elmt.addPrimaryKeys(outParams);
				} catch (ExcuteProcedureException e) {
					throw e; // 续抛该异常
				}
			}
		} else
			point.handleAccompanyModel();
		// 写树记录
		try {
			tree.writeRecords(point.getElmtModel(), elmts);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 写一个指定的元素数据到数据库
	 * 
	 * @param elmt
	 *            xml文件元素的包装对象
	 * @param point
	 *            node数据节点指向对象
	 * @exception ExcuteProcedureException
	 *                写入元素的数据失败时抛出
	 */
	private List<ProcParam> writeElmtData(MyElement elmt,
			Model_Elmt_Binder point) throws ExcuteProcedureException {
		// 获取节点模板的属性模板
		ElmtModel nodePoint = point.getElmtModel();
		List<EntityModel> attrs = nodePoint.getAccompanyModels();

		String procCallString = nodePoint.tablename;
		ProcedureProcess proc = new ProcedureProcess(procCallString);

		// -----------debug-------------
		// if(proc.procedureCallStr.equals("{call PRO_NODEMAIN_INSERT(?,?,?)}")){
		// System.out.println("");
		// }

		// 配置存储过程执行对象的参数并执行，配置的顺序与存储过程中参数的顺序必须是对应的，且参数的个数与配置的值的个数是相同的

		for (int i = 0; i < attrs.size(); ++i) {
			EntityModel attr = attrs.get(i);
			if (attr instanceof AttrModel) {
				//revise5
				boolean success = setProcProcessParam(proc, (AttrModel) attr, elmt);

				if (!success)
					return null;
			}
		}

		List<ProcParam> outParams = null;
		try {
			outParams = proc.insertWithReturnOutParam(this.conn);
		} catch (ExcuteProcedureException e) {
			throw e; // 续抛该异常
		} catch (NumberFormatException e) {
			System.out.println(nodePoint.toString() + ": 类型与实际数据不匹配");
			e.printStackTrace();
		}
		return outParams;

	}

	/**
	 * 设置存储过程执行对象的参数
	 * 
	 * @param procProcess
	 * @param attrModel
	 */
	private boolean setProcProcessParam(ProcedureProcess procProcess,
			AttrModel attrModel, MyElement elmt) {
		// 根据默认值获取值
		if (attrModel.defaultvalue != null)
			procProcess.addProcParamValue(attrModel.colname,
					attrModel.defaultvalue, attrModel.datatype, 1);
		else {
			// 根据外键传入值
			if (attrModel.datafrom != null) {
				// 传入的数据未指定colName，直接用datafrom的主键名
				try {
					procProcess.addProcParamValue(attrModel.colname, elmt
							.getDBKeyValue(attrModel.getDataFromFieldName()),
							attrModel.datatype, 1);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				// 获取xml属性值
				if (attrModel.xmlname != null && attrModel.colname != null) {
					String value = elmt.getElmt().attributeValue(
							attrModel.xmlname);
					if (value == null)
						return false;
					else
						procProcess.addProcParamValue(attrModel.colname, value,
								attrModel.datatype, 1);
				} else
				// 将xml文件中不存在的数据库生成数据作为输出参数输出
				if (attrModel.xmlname == null && attrModel.colname != null)
					procProcess.addProcParamValue(attrModel.colname, "1",
							attrModel.datatype, -1);
			}

		}
		return true;
	}

	/**
	 * 获取接口标识码
	 * 
	 * @return
	 */
	public String getInterfaceCode() {
		// 接口标识码是在xml文件的第4层指定的
		// 获取第4层的元素名称
		Element interfaceCode = new XmlReader(doc.getRootElement())
				.getNLevelElement(interfaceCodeLevel);
		// revise2
		String code = "";
		code = interfaceCode.getName();
		return code;
	}

	/**
	 * 获取xml的标识序列
	 * 
	 * @return
	 */
	public String getId() {
		Element root = doc.getRootElement();
		if (root == null) {
			System.out.println("xml不存在根元素");
			return null;
		}
		Attribute attr = root.attribute("sId");
		if (attr == null) {
			System.out.println("xml根元素不存在名为 sId 的属性");
			return null;
		}
		return attr.getValue();
	}

	/**
	 * 获取指定层的第一个元素的元素名
	 * 
	 * @param nLayer
	 *            根节点位于第一层，nLayer = 1,2,3...<maxLayer
	 * @return
	 */
	public String getFirstElmtNameAt(int nLayer) {
		Element elmt = doc.getRootElement();
		if (elmt == null) {
			System.out.println("xml不存在根元素");
			return null;
		}
		for (int i = 1; i < nLayer; i++) {
			List<?> childs = elmt.elements();
			if (childs.size() == 0) {
				System.out.println("xml的层次少于 " + nLayer + " 层");
				return null;
			}
			elmt = (Element) childs.get(0);
			if (elmt == null) {
				System.out.println(i + 1 + "层的第一个子节点不是元素");
				return null;
			}
		}
		return elmt.getName();
	}

	/**
	 * 获取xml文件根元素
	 * 
	 * @return
	 */
	public MyElement getRoot() {
		return new MyElement(doc.getRootElement(), null);
	}

	public Document getDoc() {
		return doc;
	}
}
