package com.zjnms.xmlParse;

//import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//import oracle.jdbc.OracleTypes;

/**
 * 获取xml模板的工厂
 * 
 * @author llj
 * 
 */
public class XmlModelFactory {
	//revise11
	private List<XmlModel> models;
	// 对模板工厂使用单一实例避免构造多个模板工厂保存多个xml接口模板库的实例
	private static XmlModelFactory factory = null;
	private DataAccess da = new DataAccess();

	/**
	 * 获取模板工厂对象
	 * 
	 * @return
	 */
	public static XmlModelFactory getFactory() {
		if (factory == null)
			factory = new XmlModelFactory();
		return factory;
	}

	private XmlModelFactory() {
	}

	/**
	 * 从模板库中获取一个指定模板的拷贝
	 * 
	 * @param xml
	 *            xml文件对象
	 * @return
	 */
	public XmlModel getInstance(Xml xml, TransferDirect direct) {
		// 第一次获取时从数据库读取所有模板,加锁
		synchronized(models){
		if (models == null)
			readAllModels();
		}
		// 根据接口标识码获取指定xml模板复件
		return getCopy(xml.getInterfaceCode(), direct);
	}

	/**
	 * 从模板库中获取一个指定模板的拷贝
	 * 
	 * @param interfaceCode
	 *            接口标识码
	 * @return
	 */
	public XmlModel getInstance(String interfaceCode, TransferDirect direct) {
		// 第一次获取从数据库读取所有xml接口模板
		synchronized(models){
		if (models == null)
			readAllModels();
		}
		// 根据接口标识码获取指定xml模板复件
		return getCopy(interfaceCode, direct);
	}

	private void readAllModels() {
		try {
			// DataAccess da = new DataAccess();
			// String sql = "select * from Tbbase_Interface order by tempid";
			Connection conn = da.OpenConn();
			// 执行存储过程
			ProcedureProcess proc = new ProcedureProcess(
					"{call proc_select_tbbaseinterface(?)}");
			// 增加一个游标参数
			proc.addProcParamValue("", "cursor", -1);
			// 执行并返回结果集
			ResultSet res = proc.selectInRS(conn);
			//revise9
			if(res == null)
				return;
			this.models = new ArrayList<XmlModel>();
			while (res.next()) {
				XmlModel base = new XmlModel();
				base.setCode(res.getString("code"));
				base.setDecodes(res.getString("decodes"));
				base.setDirect(res.getString("direct"));
				base.setINmae(res.getString("iname"));
				base.setIsfile(res.getString("isfile"));
				base.setTempid(res.getInt("tempid"));
				readNodeModels(base, base.tempid);
				this.models.add(base);
			}
			res.close();
//			da.CloseConn();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			da.CloseConn();
		}
	}

	/**
	 * 获取指定的xml模板
	 * 
	 * @param interfaceCode
	 *            接口标识码
	 * @param direct
	 *            模板的输入输出方向
	 * @return 不存在指定模板则返回null
	 */
	private XmlModel getCopy(String interfaceCode, TransferDirect direct) {
		// 解析xml文件的数据方向是 输入
		// String direct = "输入";
		for (int i = 0; i < models.size(); ++i) {
			XmlModel model = models.get(i);
			if (model.code.equals(interfaceCode) && model.getDirect() == direct)
				return (XmlModel) model.clone();
		}
		new Exception("不存在接口标识码为 " + interfaceCode+"，方向为 "+direct.toString() + " 的接口模板").printStackTrace();
		return null;
	}

	private void readNodeModels(XmlModel base, int tempid) {//revise12
		try {
			// String sql = "select * from Tbbase_Interdesc where tempid ="
			// + base.tempid + " order by nodeid";

			ProcedureProcess proc = new ProcedureProcess(
					"{call proc_select_tbbaseinterdesc(?,?)}");
			// 设置存储过程的输入参数
			proc.addProcParamValue(Integer.valueOf(tempid).toString(),
					"number", 1);
			// 设置游标输出参数
			proc.addProcParamValue("", "cursor", -1);
			Connection conn = da.getConn();
			if(conn == null)
				return;
			// 执行并返回结果集
			ResultSet res = proc.selectInRS(conn);
			//revise10
			if(res == null)
				return;
			int entityIndex = 0;
			ElmtModel currElmtModel = null;
			while (res.next()) {
				// String ename = res.getString("ename");
				// if (ename == null)
				// throw new Exception("ename不能为空");
				// revise8
				EntityModel model = new EntityModel(base, entityIndex++, currElmtModel);
				model.colname = res.getString("colname");
				model.datafrom = res.getString("datafrom");
				model.datatype = res.getString("datatype");
				model.defaultvalue = res.getString("defaultvalue");
				model.ename = res.getString("ename");
				model.invalue = res.getString("invalue");
				model.nodeid = res.getInt("nodeid");
				model.outvalue = res.getString("outvalue");
				model.placeholder = res.getString("placeholder");
				model.pnodeid = res.getInt("pnodeid");
				model.tablename = res.getString("tablename");
				model.tempid = res.getInt("tempid");
				model.xmlname = res.getString("xmlname");

				EntityModel real = model.recognize();
				real.judge();
				if (real instanceof ElmtModel)
					currElmtModel = (ElmtModel) real;
				base.addEntityModel(real);
			}
			res.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
