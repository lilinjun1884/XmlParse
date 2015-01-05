package com.zjnms.xmlParse;

//import java.util.Date;

/**
 * 测试网络管理设备中，从外部输入的接口协议（xml字符串）
 * 
 * @author llj
 */
public class Test_ReadIn {
	private static Xml2Str xml2Str = new Xml2Str();

	public static void main(String[] args) {
		// read_cfgNetStatPara();
		 read_setCommOrgParam();
		// read_setFreqInfo();
//		read_getAlarm();
		// read_getEqpInfo();
		// read_getFreqUseInfo();
//		read_getMoveNodeLinkState();
//		read_getPerfValue();
//		read_setReportAlarmLevel();
//		read_reportEquipStatus();
//		read_getEquipStatus();
//		read_reportMoveNodeLinkStatus();
//		read_setLgSubTrainCmd();
//		read_reportHeartbeat();
//		read_getChannelStatus();
//		read_reportChannelStatus();
//		read_reportNetStatus();
//		read_getObject();
//		read_getAllChildrenOfParent(); // 来自综合网管的输入
//		read_getAllChildrenOfParent_();
//		read_getAllChildrenOfParent2(); // 来自接入节点的输入
//		read_setReportAlarmlevel();
//		read_stopPerfWatch();
//		read_startPerfWatch();
//		read_reportChannelQuality();
//		read_getobject();
//		read_getobject2();
//		read_reportObjAdd();
//		read_reportPropChange();
//		read_getSiteNodeLinkStatus();
//		read_reportSetlinkStart();
//		read_reportSetlinkResult();
//		read_reportAlarm();
//		read_reportDatastatus();
//		read_getMoveNodeLinkStatus();
//		read_getComponentStatus();
//		read_reportComponentStatus();
//		read_reportManagedElementResult();
		// read_getLogicSubCfgParam();
	}

	public static String reportPropChange() {
		return xml2Str
				.run("C:\\Documents and Settings\\Administrator\\桌面\\mywork\\已调试的接口\\topo\\reportPropChange_子网_综合网管.xml");
	}

	public static void read_reportPropChange() {
		Xml examXml = Xml.importXml(Test_ReadIn.reportPropChange());
		if (examXml == null)
			return;
		XmlModelFactory fac = XmlModelFactory.getFactory();
		XmlModel inModel = fac.getInstance(examXml,
				TransferDirect.INPUT_FROM_JOINNODE);

		// TreeRecord tree= examXml.getRequestData(inModel);
		TreeRecord tree = examXml.handleWith(inModel);

		System.out.println(tree.getRecordFieldValue(2, 0, "objID"));
		System.out.println(tree.getRecordFieldValue(2, 0, "objType"));
	}

	public static String reportManagedElementResult() {
		return xml2Str
				.run("C:\\Documents and Settings\\Administrator\\桌面\\mywork\\已调试的接口\\内部\\reportManagedElementResult.xml");
	}

	public static void read_reportManagedElementResult() {
		Xml examXml = Xml.importXml(Test_ReadIn.reportManagedElementResult());
		if (examXml == null)
			return;
		XmlModelFactory fac = XmlModelFactory.getFactory();
		XmlModel inModel = fac.getInstance(examXml,
				TransferDirect.INPUT_FROM_JOINNODE);

		// TreeRecord tree= examXml.getRequestData(inModel);
		TreeRecord tree = examXml.handleWith(inModel);
	}

	/**
	 * 用作测试的xml字符串
	 * 
	 * @return
	 */
	public static String cfgNetStatParaXml() {
		return xml2Str
				.run("C:\\Documents and Settings\\Administrator\\桌面\\mywork\\已调试的接口\\strFormat\\8.1cgfNetStatPara1.xml");
	}

	public static String getLogicSubCfgParamXml() {
		return "<?xml version=\"1.0\" encoding=\"UTF-8\"?><session sId=\"12345\" username=\"\" password=\"\"><cmd>"
				+ "<config><getLogicSubCfgParam taskId=\"0255\" taskName=\"test\" logicSubnetID=\"13_01_1003\">"
				+ "</getLogicSubCfgParam></config></cmd></session>";
	}

	public static void read_getLogicSubCfgParam() {
		Xml examXml = Xml.importXml(Test_ReadIn.getLogicSubCfgParamXml());
		if (examXml == null)
			return;
		XmlModelFactory fac = XmlModelFactory.getFactory();
		XmlModel inModel = fac.getInstance(examXml,
				TransferDirect.INPUT_FROM_WEBMASTER);

		// TreeRecord tree= examXml.getRequestData(inModel);
		TreeRecord tree = examXml.handleWith(inModel);

		XmlModel outModel = fac.getInstance(inModel.code,
				TransferDirect.OUTPUT_TO_WEBMASTER);
		String outStr = outModel.generateXml(tree);
		System.out.println(outStr);
	}

	// public static String reportObjAdd() {
	// return
	// "<?xml version=\"1.0\" encoding=\"UTF-8\"?><session sId=\"12345\" username=\"admin\" password=\"admin\">"
	// +
	// "<cmd><topo><reportObjAdd><data><nameValue obj=\"02_02_55.66.12.33||0003\" objType=\"01\" name=\"青岛接入站\" lan=\"101.12\" lon=\"35.235\"/>"
	// + "</data></reportObjAdd></topo></cmd></session>";
	// }

	public static String getAlarm() {
		return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
				+ "<session sId=\"23954515188862\" username=\"admin\" password=\"admin\">"
				+ "<cmd>"
				+ "<alarm>"
				+ "<getAlarm sTime=\"20130701000000\" eTime=\"20130719000000\" count=\"500\" level=\"05\"/>"
				+ "</alarm>" + "</cmd>" + "</session>";
	}

	public static void read_getAlarm() {
		Xml examXml = Xml.importXml(Test_ReadIn.getAlarm());
		if (examXml == null)
			return;
		XmlModelFactory fac = XmlModelFactory.getFactory();
		XmlModel inModel = fac.getInstance(examXml,
				TransferDirect.INPUT_FROM_WEBMASTER);

		// TreeRecord tree= examXml.getRequestData(inModel);
		TreeRecord tree = examXml.handleWith(inModel);
		if (tree.isEmptyTree())
			return;
		/*
		 * XmlModel outModel = fac.getInstance(inModel.code, "输出"); String
		 * outStr = outModel.generateXml(tree); System.out.println(outStr);
		 */
	}

	public static String getAllChildrenNamesAndIDsOfParent() {
		return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
				+ "<session sId=\"16665035234598898\" username=\"admin\" password=\"admin\">"
				+ "<cmd>"
				+ "<topo>"
				+ "<getAllChildrenNamesAndIDsOfParent pID=\"01_22_01||001\" pType=\"06\" cType=\"08\" count=\"500\">"
				+ "</getAllChildrenNamesAndIDsOfParent>" + "</topo>" + "</cmd>"
				+ "</session>";
	}

	public static void read_getAllChildrenNamesAndIDsOfParent() {
		Xml examXml = Xml.importXml(Test_ReadIn
				.getAllChildrenNamesAndIDsOfParent());
		if (examXml == null)
			return;
		XmlModelFactory fac = XmlModelFactory.getFactory();
		XmlModel inModel = fac.getInstance(examXml,
				TransferDirect.INPUT_FROM_WEBMASTER);

		// TreeRecord tree= examXml.getRequestData(inModel);
		TreeRecord tree = examXml.handleWith(inModel);
		if (tree.isEmptyTree())
			return;
		/*
		 * XmlModel outModel = fac.getInstance(inModel.code, "输出"); String
		 * outStr = outModel.generateXml(tree); System.out.println(outStr);
		 */
	}

	public static String getEqpInfo() {
		return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
				+ "<session sId=\"36376875\" username=\"admin\" password=\"admin\">"
				+ " <cmd>"
				+ "<frequency>"
				+ "<getEqpInfo taskID=\"1|285278209|20130716151111\" logicSubnetID=\"01_22_01_1111\">"
				+ "</getEqpInfo>" + "</frequency>" + "</cmd>" + "</session>";
	}

	public static void read_getEqpInfo() {
		Xml examXml = Xml.importXml(Test_ReadIn.getEqpInfo());
		if (examXml == null)
			return;
		XmlModelFactory fac = XmlModelFactory.getFactory();
		XmlModel inModel = fac.getInstance(examXml,
				TransferDirect.INPUT_FROM_WEBMASTER);

		// TreeRecord tree= examXml.getRequestData(inModel);
		TreeRecord tree = examXml.handleWith(inModel);
		if (tree.isEmptyTree())
			return;
		/*
		 * XmlModel outModel = fac.getInstance(inModel.code, "输出"); String
		 * outStr = outModel.generateXml(tree); System.out.println(outStr);
		 */
	}

	public static String getFreqUseInfo() {
		return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
				+ "<session sId=\"44349785911841\" username=\"admin\" password=\"admin\">"
				+ "<cmd>"
				+ "<frequency>"
				+ "<getFreqUseInfo logicSubnetID=\"01_22_01_1111\" taskID=\"1|285278209|20130716151111\" statTimeSpan=\"60\"/>"
				+ "</frequency>" + "</cmd>" + "</session>";
	}

	public static void read_getFreqUseInfo() {
		Xml examXml = Xml.importXml(Test_ReadIn.getFreqUseInfo());
		if (examXml == null)
			return;
		XmlModelFactory fac = XmlModelFactory.getFactory();
		XmlModel inModel = fac.getInstance(examXml,
				TransferDirect.INPUT_FROM_WEBMASTER);

		// TreeRecord tree= examXml.getRequestData(inModel);
		TreeRecord tree = examXml.handleWith(inModel);
		if (tree.isEmptyTree())
			return;
		/*
		 * XmlModel outModel = fac.getInstance(inModel.code, "输出"); String
		 * outStr = outModel.generateXml(tree); System.out.println(outStr);
		 */
	}

	// public static String getMoveNodeLinkState() {
	// return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
	// +
	// "<session sId=\"24837826492650\" username=\"admin\" password=\"admin\">"
	// + "<cmd>"
	// + "<topo>"
	// +
	// "<getMoveNodeLinkState logicSubnetId=\"\" netId=\"22\" taskId=\"\" count=\"500\" nodeId=\"01_22_0001||004\"/>"
	// + "</topo>" + "</cmd>" + "</session>";
	// }
	//
	// public static void read_getMoveNodeLinkState() {
	// Xml examXml = Xml.importXml(Test_ReadIn.getMoveNodeLinkState());
	// if (examXml == null)
	// return;
	// XmlModelFactory fac = XmlModelFactory.getFactory();
	// XmlModel inModel =
	// fac.getInstance(examXml,TransferDirect.INPUT_FROM_WEBMASTER);
	//
	// // TreeRecord tree= examXml.getRequestData(inModel);
	// TreeRecord tree = examXml.handleWith(inModel);
	// //
	// System.out.println(tree.getChildTrees().get(0).getRoot().getRecordFieldValue("netId"));
	// if (tree.isEmptyTree())
	// return;
	// /*
	// * XmlModel outModel = fac.getInstance(inModel.code, "输出"); String
	// * outStr = outModel.generateXml(tree); System.out.println(outStr);
	// */
	// }

	// public static String getPerfValue() {
	// return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
	// + "<session sId=\"7542572489033\" username=\"admin\" password=\"admin\">"
	// + "<cmd>"
	// + "<performance>"
	// +
	// "<getPerfValue paramIds=\"-1\" sTime=\"20130412080100\" eTime=\"20130412180100\" objIds=\"-1\" objType=\"-1\" granularity=\"60\"/>"
	// + "</performance>" + "</cmd>" + "</session>";
	// }
	//
	// public static void read_getPerfValue() {
	// Xml examXml = Xml.importXml(Test_ReadIn.getPerfValue());
	// if (examXml == null)
	// return;
	// XmlModelFactory fac = XmlModelFactory.getFactory();
	// XmlModel inModel =
	// fac.getInstance(examXml,TransferDirect.INPUT_FROM_WEBMASTER);
	//
	// // TreeRecord tree= examXml.getRequestData(inModel);
	// TreeRecord tree = examXml.handleWith(inModel);
	// //
	// System.out.println(tree.getChildTrees().get(0).getRoot().getRecordFieldValue("netId"));
	// if (tree.isEmptyTree())
	// return;
	// /*
	// * XmlModel outModel = fac.getInstance(inModel.code, "输出"); String
	// * outStr = outModel.generateXml(tree); System.out.println(outStr);
	// */
	// }

	public static String setFreqInfo() {
		// return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
		// +
		// "<session sId=\"4357705394464\" username=\"admin\" password=\"admin\">"
		// + "<cmd>"
		// + "<frequency>"
		// + "<setFreqInfo taskID=\"1|285278209|20130716152111\">"
		// + "<data>"
		// + "<line name=\"子网参数\" total=\"1\" current=\"1\">"
		// + "<nameValue logicSubnetID=\"01_22_01_1001\"/>"
		// + "<line name=\"频率参数区\" total=\"1\" current=\"1\">"
		// +
		// "<nameValue freqUseEndTime=\"20130720000000\" freqEndTime=\"\" freqsSort=\"3\" freqUseStartTime=\"20130715000000\" freqsType=\"1\" freqStartTime=\"\" freqQuality=\"\" freqsFunctionType=\"1\"/>"
		// + "<line name=\"节点频率参数\" total=\"1\" current=\"1\">"
		// + "<nameValue meId=\"\"/>"
		// + "<line name=\"频率信息\" total=\"3\" current=\"1\">"
		// + "<nameValue frequency=\"9.0\"/>" + "</line>"
		// + "<line name=\"频率信息\" total=\"3\" current=\"2\">"
		// + "<nameValue frequency=\"9.05\"/>" + "</line>"
		// + "<line name=\"频率信息\" total=\"3\" current=\"3\">"
		// + "<nameValue frequency=\"9.025\"/>" + "</line>" + "</line>"
		// + "</line>" + "</line>" + "</data>" + "</setFreqInfo>"
		// + "</frequency>" + "</cmd>" + "</session>";
		return xml2Str
				.run("C:\\Documents and Settings\\Administrator\\桌面\\mywork\\已调试的接口\\6.9 频率规划\\setFreqInfo_ 综合网管-专业网管.xml");
	}

	public static void read_setFreqInfo() {
		Xml examXml = Xml.importXml(Test_ReadIn.setFreqInfo());
		if (examXml == null)
			return;
		XmlModelFactory fac = XmlModelFactory.getFactory();
		XmlModel inModel = fac.getInstance(examXml,
				TransferDirect.INPUT_FROM_WEBMASTER);

		// TreeRecord tree= examXml.getRequestData(inModel);
		TreeRecord tree = examXml.handleWith(inModel);
		// System.out.println(tree.getChildTrees().get(0).getRoot().getRecordFieldValue("netId"));
		if (tree.isEmptyTree())
			return;

		XmlModel outModel = fac.getInstance(inModel.code,
				TransferDirect.OUTPUT_TO_WEBMASTER);
		String outStr = outModel.generateXml(tree);
		System.out.println(outStr);

	}

	public static String setReportAlarmLevel() {
		// System.out.println(xml2Str.run("C:\\Documents and Settings\\Administrator\\桌面\\mywork\\已调试的接口\\6.5 告警具体数据\\setReportAlarmlevel.xml"));
		return xml2Str
				.run("C:\\Documents and Settings\\Administrator\\桌面\\mywork\\已调试的接口\\6.5 告警具体数据\\setReportAlarmlevel.xml");
	}

	public static void read_setReportAlarmLevel() {
		Xml examXml = Xml.importXml(Test_ReadIn.setReportAlarmLevel());
		if (examXml == null)
			return;
		XmlModelFactory fac = XmlModelFactory.getFactory();
		XmlModel inModel = fac.getInstance(examXml,
				TransferDirect.INPUT_FROM_WEBMASTER);

		// TreeRecord tree= examXml.getRequestData(inModel);
		TreeRecord tree = examXml.handleWith(inModel);
		// System.out.println(tree.getChildTrees().get(0).getRoot().getRecordFieldValue("netId"));
		if (tree.isEmptyTree())
			return;
		/*
		 * XmlModel outModel = fac.getInstance(inModel.code, "输出"); String
		 * outStr = outModel.generateXml(tree); System.out.println(outStr);
		 */
	}

	public static String startPerfWatch() {
		return xml2Str
				.run("C:\\Documents and Settings\\Administrator\\桌面\\mywork\\已调试的接口\\6.6 性能管理具体数据\\startPerfWatch_综合网管_子网.xml");
	}

	public static void read_startPerfWatch() {
		Xml examXml = Xml.importXml(Test_ReadIn.startPerfWatch());
		if (examXml == null)
			return;
		XmlModelFactory fac = XmlModelFactory.getFactory();
		XmlModel inModel = fac.getInstance(examXml,
				TransferDirect.INPUT_FROM_WEBMASTER);

		// TreeRecord tree= examXml.getRequestData(inModel);
		TreeRecord tree = examXml.handleWith(inModel);
		// System.out.println(tree.getChildTrees().get(0).getRoot().getRecordFieldValue("netId"));
		if (tree.isEmptyTree())
			return;
		XmlModel outModel = fac.getInstance(inModel.code,
				TransferDirect.OUTPUT_TO_WEBMASTER);
		String outStr = outModel.generateXml(tree);
		System.out.println(outStr);
	}

	public static String stopPerfWatch() {
		return xml2Str
				.run("C:\\Documents and Settings\\Administrator\\桌面\\mywork\\已调试的接口\\6.6 性能管理具体数据\\stopPerfWatch_综合网管_子网.xml");
	}

	public static void read_stopPerfWatch() {
		Xml examXml = Xml.importXml(Test_ReadIn.stopPerfWatch());
		if (examXml == null)
			return;
		XmlModelFactory fac = XmlModelFactory.getFactory();
		XmlModel inModel = fac.getInstance(examXml,
				TransferDirect.INPUT_FROM_WEBMASTER);

		// TreeRecord tree= examXml.getRequestData(inModel);
		TreeRecord tree = examXml.handleWith(inModel);
		// System.out.println(tree.getChildTrees().get(0).getRoot().getRecordFieldValue("netId"));
		if (tree.isEmptyTree())
			return;
		XmlModel outModel = fac.getInstance(inModel.code,
				TransferDirect.OUTPUT_TO_WEBMASTER);
		String outStr = outModel.generateXml(tree);
		System.out.println(outStr);
	}

	public static String test() {
		return "<?xml version=\"1.0\" encoding=\"UTF-8\"?><session sId=\"1234567890\" username=\"admin\" password=\"admin\">"
				+ "<cmd><topo><test><data><nameValue proid=\"10\" paramtype=\"test4\"/>"
				+ "</data></test></topo></cmd></session>";
	}

	public static void read_test() {
		Xml examXml = Xml.importXml(Test_ReadIn.test());
		if (examXml == null)
			return;
		XmlModelFactory fac = XmlModelFactory.getFactory();
		XmlModel inModel = fac.getInstance(examXml,
				TransferDirect.INPUT_FROM_WEBMASTER);

		// TreeRecord tree= examXml.getRequestData(inModel);
		TreeRecord tree = examXml.handleWith(inModel);
		if (tree.isEmptyTree())
			return;
		XmlModel outModel = fac.getInstance(inModel.code,
				TransferDirect.OUTPUT_TO_WEBMASTER);
		String outStr = outModel.generateXml(tree);
		System.out.println(outStr);
	}

	public static void read_cfgNetStatPara() {
		// Date time =new Date();
		// System.out.println(time.getSeconds());
		// 导入外部xmlString
		Xml examXml = Xml.importXml(Test_ReadIn.cfgNetStatParaXml());
		if (examXml == null)
			return;

		// 获取xml模版工厂对象
		XmlModelFactory fac = XmlModelFactory.getFactory();
		// 取得指定模板的副本
		XmlModel inModel = fac.getInstance(examXml,
				TransferDirect.INPUT_FROM_WEBMASTER);

		// 1.根据xml模版对象处理输入的xml文件
		TreeRecord tree = examXml.handleWith(inModel);
		System.out.println(tree.getRecordFieldValue(3, 0, "proid"));
		System.out.println(tree.getRecordFieldValue(3, 0, "netid"));
		System.out.println(tree.getRecordFieldValue(2, 0, "taskName"));
		System.out.println(tree.getRecordFieldValue(3, 0, "logicSubnetName"));
		Record record = tree.getChildTrees().get(0).getChildTrees().get(0)
				.getRoot();
		record.deleteRecordField("proid");
		record.deleteRecordField("netid");
		record.deleteRecordField("logicSubnetName");

		// 2.根据xml模板对象将xml中指定要获取的数据放入数据集合对象（标签数据）
		// TreeRecord tree = examXml.getRequestData(inModel);
		// 指定 标签数据 的输出xml模板
		XmlModel outModel = fac.getInstance(inModel.code,
				TransferDirect.OUTPUT_TO_WEBMASTER);
		String outStr = outModel.generateXml(tree);
		System.out.println(outStr);

	}

	public static String cfgNetStatParaXml2() {
		return xml2Str
				.run("C:\\Documents and Settings\\Administrator\\桌面\\mywork\\已调试的接口\\strFormat\\8.1cgfNetStatPara1.xml");
	}

	public static void read_cfgNetStatPara2() {

	}

	public static String cfgNetStatParaXml3() {
		return xml2Str
				.run("C:\\Documents and Settings\\Administrator\\桌面\\mywork\\已调试的接口\\strFormat\\8.1cgfNetStatPara1.xml");
	}

	public static void read_cfgNetStatPara3() {

	}

	public static void read_reportEquipStatus() {
		Xml examXml = Xml.importXml(Test_ReadIn.reportEquipStatus());
		if (examXml == null)
			return;
		XmlModelFactory fac = XmlModelFactory.getFactory();
		XmlModel inModel = fac.getInstance(examXml,
				TransferDirect.INPUT_FROM_JOINNODE);

		// TreeRecord tree= examXml.getRequestData(inModel);
		TreeRecord tree = examXml.handleWith(inModel);
		// Record root = tree.getRoot();
		// // String id = root.getRecordFieldValue("equipID");
		// String id = tree.getRecordFieldValue(3, 0, "equipID");
		// String service = root.getRecordFieldValue("serviceStatus");

		// System.out.println(id + " " + service);
		// System.out.println("is empty tree:" + tree.isEmptyTree());
	}

	public static String reportEquipStatus() {
		return xml2Str
				.run("C:\\Documents and Settings\\Administrator\\桌面\\mywork\\已调试的接口\\内部\\reportEquipStatus.xml");
	}

	public static String getEquipStatus() {
		return xml2Str
				.run("C:\\Documents and Settings\\Administrator\\桌面\\mywork\\已调试的接口\\内部\\getEquipStatus.xml");
	}

	public static void read_getEquipStatus() {
		Xml examXml = Xml.importXml(Test_ReadIn.getEquipStatus());
		if (examXml == null)
			return;
		XmlModelFactory fac = XmlModelFactory.getFactory();
		XmlModel inModel = fac.getInstance(examXml,
				TransferDirect.INPUT_FROM_JOINNODE);

		// TreeRecord tree= examXml.getRequestData(inModel);
		TreeRecord tree = examXml.handleWith(inModel);
		Record root = tree.getRoot();
		// String id = root.getRecordFieldValue("equipID");
		String id = tree.getRecordFieldValue(2, 0, "equipID");
		String service = tree.getRecordFieldValue(2, 0, "serviceStatus");

		System.out.println(id + " " + service);
	}

	public static String getComponentStatus() {
		return xml2Str
				.run("C:\\Documents and Settings\\Administrator\\桌面\\mywork\\已调试的接口\\内部\\getComponentStatus.xml");
	}

	public static void read_getComponentStatus() {
		Xml examXml = Xml.importXml(Test_ReadIn.getComponentStatus());
		if (examXml == null)
			return;
		XmlModelFactory fac = XmlModelFactory.getFactory();
		XmlModel inModel = fac.getInstance(examXml,
				TransferDirect.INPUT_FROM_JOINNODE);

		// TreeRecord tree= examXml.getRequestData(inModel);
		TreeRecord tree = examXml.handleWith(inModel);
		System.out.println(tree.getRecordFieldValue(2, 0, "serviceStatus"));
	}

	public static String reportComponentStatus() {
		return xml2Str
				.run("C:\\Documents and Settings\\Administrator\\桌面\\mywork\\已调试的接口\\内部\\reportComponentStatus.xml");
	}

	public static void read_reportComponentStatus() {
		Xml examXml = Xml.importXml(Test_ReadIn.reportComponentStatus());
		if (examXml == null)
			return;
		XmlModelFactory fac = XmlModelFactory.getFactory();
		XmlModel inModel = fac.getInstance(examXml,
				TransferDirect.INPUT_FROM_JOINNODE);

		// TreeRecord tree= examXml.getRequestData(inModel);
		TreeRecord tree = examXml.handleWith(inModel);
		System.out.println(tree.getRecordFieldValue(2, 0, "serviceStatus"));
	}

	public static String getMoveNodeLinkStatus() {
		return xml2Str
				.run("C:\\Documents and Settings\\Administrator\\桌面\\mywork\\已调试的接口\\内部\\getMoveNodeLinkStatus.xml");
	}

	public static void read_getMoveNodeLinkStatus() {
		Xml examXml = Xml.importXml(Test_ReadIn.getMoveNodeLinkStatus());
		if (examXml == null)
			return;
		XmlModelFactory fac = XmlModelFactory.getFactory();
		XmlModel inModel = fac.getInstance(examXml,
				TransferDirect.INPUT_FROM_JOINNODE);

		// TreeRecord tree= examXml.getRequestData(inModel);
		TreeRecord tree = examXml.handleWith(inModel);
		// System.out.println(tree.getRecordFieldValue(1, 0, "sId"));
	}

	// TODO
	public static void read_setCommOrgParam() {
		Xml examXml = Xml.importXml(Test_ReadIn.setCommOrgParam());
		if (examXml == null)
			return;
		XmlModelFactory fac = XmlModelFactory.getFactory();
		XmlModel inModel = fac.getInstance(examXml,
				TransferDirect.INPUT_FROM_WEBMASTER);

		TreeRecord tree = examXml.handleWith(inModel);

		System.out.println(tree.getRecordFieldValue(2, 0, "proid"));
		System.out.println(tree.getRecordFieldValue(2, 0, "taskName"));

		Record rcd = tree.getChildTrees().get(0).getRoot();
		// rcd.addRecordField("orgName",
		// tree.getRecordFieldValue(2, 0, "taskName"));
		rcd.deleteRecordField("proid");

		XmlModel outModel = fac.getInstance(inModel.code,
				TransferDirect.OUTPUT_TO_WEBMASTER);
		String outStr = outModel.generateXml(tree);
		System.out.println(outStr);
	}

	public static String setCommOrgParam() {
		return xml2Str
				.run("C:\\Documents and Settings\\Administrator\\桌面\\222.xml");

		// return xml2Str
		// .run("C:\\Documents and Settings\\Administrator\\桌面\\mywork\\已调试的接口\\strFormat\\网络组织规定.xml");
	}

	public static void read_setLgSubTrainCmd() {
		Xml examXml = Xml.importXml(Test_ReadIn.setLgSubTrainCmd());
		if (examXml == null)
			return;
		XmlModelFactory fac = XmlModelFactory.getFactory();
		XmlModel inModel = fac.getInstance(examXml,
				TransferDirect.INPUT_FROM_WEBMASTER);
		TreeRecord tree = examXml.handleWith(inModel);
		XmlModel outModel = fac.getInstance(inModel.code,
				TransferDirect.OUTPUT_TO_WEBMASTER);
		String outStr = outModel.generateXml(tree);
		System.out.println(outStr);
	}

	public static String setLgSubTrainCmd() {
		return xml2Str
				.run("C:\\Documents and Settings\\Administrator\\桌面\\mywork\\已调试的接口\\strFormat\\6.8具体数据\\训练开通\\8.7setLgSubTrainCmd_综合网管_子网.xml");
	}

	public static String reportMoveNodeLinkStatus() {
		return xml2Str
				.run("C:\\Documents and Settings\\Administrator\\桌面\\mywork\\已调试的接口\\1002 reportMoveNodeLinkStatus.xml");
	}

	public static void read_reportMoveNodeLinkStatus() {
		Xml examXml = Xml.importXml(Test_ReadIn.reportMoveNodeLinkStatus());
		if (examXml == null)
			return;
		XmlModelFactory fac = XmlModelFactory.getFactory();
		XmlModel inModel = fac.getInstance(examXml,
				TransferDirect.INPUT_FROM_JOINNODE);
		TreeRecord tree = examXml.handleWith(inModel);
		tree.getRecordFieldValue(3, 0, "sitePlatID");

	}

	public static String reportHeartbeat() {
		return xml2Str
				.run("C:\\Documents and Settings\\Administrator\\桌面\\mywork\\已调试的接口\\1001 reportHeartbeat.xml");
	}

	public static void read_reportHeartbeat() {
		Xml examXml = Xml.importXml(Test_ReadIn.reportHeartbeat());
		if (examXml == null)
			return;
		XmlModelFactory fac = XmlModelFactory.getFactory();
		XmlModel inModel = fac.getInstance(examXml,
				TransferDirect.INPUT_FROM_JOINNODE);
		examXml.handleWith(inModel);
		// XmlModel outModel = fac.getInstance(inModel.code, "输出");
		// String outStr = outModel.generateXml(tree);
		// System.out.println(outStr);

	}

	public static String reportChannelStatus() {
		return xml2Str
				.run("C:\\Documents and Settings\\Administrator\\桌面\\mywork\\已调试的接口\\内部\\reportChannelStatus.xml");
	}

	public static void read_reportChannelStatus() {
		Xml examXml = Xml.importXml(Test_ReadIn.reportChannelStatus());
		if (examXml == null)
			return;
		XmlModelFactory fac = XmlModelFactory.getFactory();
		XmlModel inModel = fac.getInstance(examXml,
				TransferDirect.INPUT_FROM_JOINNODE);
		examXml.handleWith(inModel);
		// XmlModel outModel = fac.getInstance(inModel.code, "输出");
		// String outStr = outModel.generateXml(tree);
		// System.out.println(outStr);

	}

	public static String getChannelStatus() {
		return xml2Str
				.run("C:\\Documents and Settings\\Administrator\\桌面\\mywork\\已调试的接口\\内部\\getChannelStatus.xml");
	}

	public static void read_getChannelStatus() {
		Xml examXml = Xml.importXml(Test_ReadIn.getChannelStatus());
		if (examXml == null)
			return;
		XmlModelFactory fac = XmlModelFactory.getFactory();
		XmlModel inModel = fac.getInstance(examXml,
				TransferDirect.INPUT_FROM_JOINNODE);
		TreeRecord tree = examXml.handleWith(inModel);
		System.out.println(tree.getRecordFieldValue(2, 0, "taskID"));
	}

	public static String reportNetStatus() {
		return xml2Str
				.run("C:\\Documents and Settings\\Administrator\\桌面\\mywork\\已调试的接口\\内部\\reportNetStatus.xml");
	}

	public static void read_reportNetStatus() {
		Xml examXml = Xml.importXml(Test_ReadIn.reportNetStatus());
		if (examXml == null)
			return;
		XmlModelFactory fac = XmlModelFactory.getFactory();
		XmlModel inModel = fac.getInstance(examXml,
				TransferDirect.INPUT_FROM_JOINNODE);
		examXml.handleWith(inModel);
	}

	public static String getObject() {
		return xml2Str
				.run("C:\\Documents and Settings\\Administrator\\桌面\\mywork\\已调试的接口\\综合网管下发数据\\getObject输入.xml");
		// return
		// xml2Str.run("C:\\Documents and Settings\\Administrator\\桌面\\mywork\\已调试的接口\\内部\\新建文本文档.xml");
	}

	public static void read_getObject() {
		Xml examXml = Xml.importXml(Test_ReadIn.getObject());
		if (examXml == null)
			return;
		XmlModelFactory fac = XmlModelFactory.getFactory();
		XmlModel inModel = fac.getInstance(examXml,
				TransferDirect.INPUT_FROM_WEBMASTER);
		TreeRecord tree = examXml.handleWith(inModel);
		Record child = new Record();
		// child.addRecordField("netId", "22");
		// child.addRecordField("chainId", "22");
		// child.addRecordField("zyCode", "");
		// child.addRecordField("nativeName", "短波自动接入网");
		// child.addRecordField("userLabel", "短波自动接入网");
		// child.addRecordField("owner", "01");
		// child.addRecordField("status", "1");
		// child.addRecordField("txEquipmentId", "txID");
		// child.addRecordField("termiSelfadaptAudioSte", "");
		// child.addRecordField("selfadaptSenderAudioSte", "");
		// child.addRecordField("additionalInfo", "");

		// child.addRecordField("netId", "22");
		child.addRecordField("meId", "meId");
		child.addRecordField("zyCode", "zyCode");
		child.addRecordField("lgSubnetName", "logicSubnetName");
		child.addRecordField("lgSubnetType", "logicSubnetType");
		child.addRecordField("owner", "01");
		child.addRecordField("lgSubnetState", "meId");
		child.addRecordField("ownerTaskIds", "");
		child.addRecordField("additionalInfo", "");

		tree.getLeafs().get(0).addLeafInRoot(child);

		XmlModel outModel = fac.getInstance(inModel.code,
				TransferDirect.OUTPUT_TO_WEBMASTER);
		// outModel.
		String outStr = outModel.generateXml(tree);
		System.out.println(outStr);
	}

	public static String getMoveNodeLinkState() {
		return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
				+ "<session sId=\"24837826492650\" username=\"admin\" password=\"admin\">"
				+ "<cmd>"
				+ "<topo>"
				+ "<getMoveNodeLinkState logicSubnetID=\"\" netId=\"22\" taskId=\"\" count=\"500\" nodeId=\"01_22_0001||004\"/>"
				+ "</topo>" + "</cmd>" + "</session>";
		// return new Xml2Str()
		// .run("C:\\Documents and Settings\\Administrator\\桌面\\mywork\\getMoveNodeLinkStatus.xml");
	}

	public static void read_getMoveNodeLinkState() {
		Xml examXml = Xml.importXml(Test_ReadIn.getMoveNodeLinkState());
		if (examXml == null)
			return;
		XmlModelFactory fac = XmlModelFactory.getFactory();
		XmlModel inModel = fac.getInstance(examXml,
				TransferDirect.INPUT_FROM_WEBMASTER);

		// TreeRecord tree= examXml.getRequestData(inModel);
		TreeRecord tree = examXml.handleWith(inModel);
		// System.out.println(tree.getChildTrees().get(0).getRoot().getRecordFieldValue("netId"));
		if (tree.isEmptyTree())
			return;
		Record child = new Record();
		// desObjType="06" desObjId="01_22_0001||003" freq=""
		// linkNodeId="01_22_0001||003" quality="1" direction="1"
		// logicSubnetId="" taskId="" additionalInfo="" />
		child.addRecordField("linkState", "1");
		child.addRecordField("srcObjType", "06");
		child.addRecordField("srcObjId", "13_03_1003");
		child.addRecordField("desObjType", "06");
		child.addRecordField("desObjId", "01_22_0001||003");
		child.addRecordField("freq", "");
		child.addRecordField("linkNodeId", "01_22_0001||003");
		child.addRecordField("quality", "3");
		child.addRecordField("direction", "1");
		child.addRecordField("logicSubnetID", "");
		child.addRecordField("additionalInfo", "");

		Record child2 = new Record();
		// desObjType="06" desObjId="01_22_0001||003" freq=""
		// linkNodeId="01_22_0001||003" quality="1" direction="1"
		// logicSubnetId="" taskId="" additionalInfo="" />
		child2.addRecordField("linkState", "1");
		child2.addRecordField("srcObjType", "06");
		child2.addRecordField("srcObjId", "13_02_1003");
		child2.addRecordField("desObjType", "06");
		child2.addRecordField("desObjId", "01_22_0001||003");
		child2.addRecordField("freq", "");
		child2.addRecordField("linkNodeId", "01_22_0001||003");
		child2.addRecordField("quality", "3");
		child2.addRecordField("direction", "1");
		child2.addRecordField("logicSubnetID", "");
		child2.addRecordField("additionalInfo", "");

		TreeRecord leaf = tree.getChildTrees().get(0);
		// leaf.addLeafInRoot(child);
		// leaf.addLeafInRoot(child2);
		XmlModel outModel = fac.getInstance(inModel.code,
				TransferDirect.OUTPUT_TO_WEBMASTER);
		String outStr = outModel.generateXml(tree);
		System.out.print(outStr);

	}

	public static String getAllChildrenOfParent() {
		return xml2Str
				.run("C:\\Documents and Settings\\Administrator\\桌面\\mywork\\已调试的接口\\综合网管下发数据\\getAllChildrenOfParent.xml");
	}

	public static void read_getAllChildrenOfParent() {
		Xml examXml = Xml.importXml(Test_ReadIn.getAllChildrenOfParent());
		if (examXml == null)
			return;
		XmlModelFactory fac = XmlModelFactory.getFactory();
		XmlModel inModel = fac.getInstance(examXml,
				TransferDirect.INPUT_FROM_WEBMASTER);
		TreeRecord tree = examXml.handleWith(inModel);

		Record child = new Record();
		child.addRecordField("additionalInfo", "22");
		child.addRecordField("version", "");
		child.addRecordField("owner", "01");
		child.addRecordField("nativeName", "短波自动接入网");
		child.addRecordField("userLabel", "短波自动接入网");
		child.addRecordField("zyCode", "1");
		child.addRecordField("systemId", "01_22_0001");

		tree.getLeafs().get(0).addLeafInRoot(child);

		XmlModel outModel = fac.getInstance(inModel.code,
				TransferDirect.OUTPUT_TO_WEBMASTER);
		String outStr = outModel.generateXml(tree);
		System.out.println(outStr);
	}

	public static String getAllChildrenOfParent_() {
		return xml2Str
				.run("C:\\Documents and Settings\\Administrator\\桌面\\mywork\\已调试的接口\\综合网管下发数据\\getAllChildrenOfParent_.xml");
	}

	public static void read_getAllChildrenOfParent_() {
		Xml examXml = Xml.importXml(Test_ReadIn.getAllChildrenOfParent_());
		if (examXml == null)
			return;
		XmlModelFactory fac = XmlModelFactory.getFactory();
		XmlModel inModel = fac.getInstance(examXml,
				TransferDirect.INPUT_FROM_WEBMASTER);
		TreeRecord tree = examXml.handleWith(inModel);

		Record child = new Record();
		child.addRecordField("additionalInfo", "224234");
		child.addRecordField("version", "");
		child.addRecordField("owner", "01");
		child.addRecordField("nativeName", "短波自动接入网2");
		child.addRecordField("userLabel", "短波自动接入网2");
		child.addRecordField("zyCode", "1");
		child.addRecordField("systemId", "01_22_0001");

		tree.getLeafs().get(0).addLeafInRoot(child);

		XmlModel outModel = fac.getInstance(inModel.code,
				TransferDirect.OUTPUT_TO_WEBMASTER);
		String outStr = outModel.generateXml(tree);
		System.out.println(outStr);
	}

	public static String getAllChildrenOfParent2() {
		return xml2Str
				.run("C:\\Documents and Settings\\Administrator\\桌面\\mywork\\已调试的接口\\内部\\getAllChildrenOfParent11.xml");
	}

	public static void read_getAllChildrenOfParent2() {
		Xml examXml = Xml.importXml(Test_ReadIn.getAllChildrenOfParent2());
		if (examXml == null)
			return;
		XmlModelFactory fac = XmlModelFactory.getFactory();
		XmlModel inModel = fac.getInstance(examXml,
				TransferDirect.INPUT_FROM_JOINNODE);
		TreeRecord tree = examXml.handleWith(inModel);
	}

	public static void read_setReportAlarmlevel() {
		Xml examXml = Xml.importXml(Test_ReadIn.setReportAlarmlevel());
		if (examXml == null)
			return;
		XmlModelFactory fac = XmlModelFactory.getFactory();
		XmlModel inModel = fac.getInstance(examXml,
				TransferDirect.INPUT_FROM_WEBMASTER);
		TreeRecord tree = examXml.handleWith(inModel);

		XmlModel outModel = fac.getInstance(inModel.code,
				TransferDirect.OUTPUT_TO_WEBMASTER);
		String outStr = outModel.generateXml(tree);
		System.out.println(outStr);
	}

	public static String setReportAlarmlevel() {
		return xml2Str
				.run("C:\\Documents and Settings\\Administrator\\桌面\\mywork\\已调试的接口\\6.5 告警具体数据\\setReportAlarmlevel.xml");
	}

	public static String getPerfValue() {
		return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
				+ "<session sId=\"7542572489033\" username=\"admin\" password=\"admin\">"
				+ "<cmd>"
				+ "<performance>"
				+ "<getPerfValue paramIds=\"-1\" sTime=\"20130412080100\" eTime=\"20130412180100\" objIds=\"-1\" objType=\"-1\" granularity=\"60\"/>"
				+ "</performance>" + "</cmd>" + "</session>";
	}

	public static void read_getPerfValue() {
		Xml examXml = Xml.importXml(Test_ReadIn.getPerfValue());
		if (examXml == null)
			return;
		XmlModelFactory fac = XmlModelFactory.getFactory();
		XmlModel inModel = fac.getInstance(examXml,
				TransferDirect.INPUT_FROM_WEBMASTER);

		// TreeRecord tree= examXml.getRequestData(inModel);
		TreeRecord tree = examXml.handleWith(inModel);
		// System.out.println(tree.getChildTrees().get(0).getRoot().getRecordFieldValue("netId"));
		if (tree.isEmptyTree())
			return;
		Record child2 = new Record();
		// desObjType="06" desObjId="01_22_0001||003" freq=""
		// linkNodeId="01_22_0001||003" quality="1" direction="1"
		// logicSubnetId="" taskId="" additionalInfo="" />
		child2.addRecordField("objId", "01_22_0001||001");
		child2.addRecordField("objType", "06");
		child2.addRecordField("paramId", "13_02_1003");
		child2.addRecordField("time", "20130715120000");
		child2.addRecordField("value", "22");
		child2.addRecordField("unit", "次");

		TreeRecord leaf = tree.getChildTrees().get(0);

		leaf.addLeafInRoot(child2);
		XmlModel outModel = fac.getInstance(inModel.code,
				TransferDirect.OUTPUT_TO_WEBMASTER);
		String outStr = outModel.generateXml(tree);
		System.out.print(outStr);
		/*
		 * XmlModel outModel = fac.getInstance(inModel.code, "输出"); String
		 * outStr = outModel.generateXml(tree); System.out.println(outStr);
		 */
	}

	public static String reportChannelQuality() {
		return xml2Str
				.run("C:\\Documents and Settings\\Administrator\\桌面\\mywork\\已调试的接口\\内部\\reportChannelQuality.xml");
	}

	public static void read_reportChannelQuality() {
		Xml examXml = Xml.importXml(Test_ReadIn.reportChannelQuality());
		if (examXml == null)
			return;
		XmlModelFactory fac = XmlModelFactory.getFactory();
		XmlModel inModel = fac.getInstance(examXml,
				TransferDirect.INPUT_FROM_JOINNODE);
		TreeRecord tree = examXml.handleWith(inModel);

		// XmlModel outModel = fac.getInstance(inModel.code, "输出");
		// String outStr = outModel.generateXml(tree);
		// System.out.println(outStr);
	}

	public static String getobject() {
		return xml2Str
				.run("C:\\Documents and Settings\\Administrator\\桌面\\mywork\\已调试的接口\\内部\\getObject_08_02.xml");
	}

	public static void read_getobject() {
		Xml examXml = Xml.importXml(Test_ReadIn.getobject());
		if (examXml == null)
			return;
		XmlModelFactory fac = XmlModelFactory.getFactory();
		XmlModel inModel = fac.getInstance(examXml,
				TransferDirect.INPUT_FROM_JOINNODE);
		examXml.handleWith(inModel);

		// XmlModel outModel = fac.getInstance(inModel.code, "输出");
		// String outStr = outModel.generateXml(tree);
		// System.out.println(outStr);

	}

	public static String getobject2() {
		return xml2Str
				.run("C:\\Documents and Settings\\Administrator\\桌面\\mywork\\已调试的接口\\综合网管下发数据\\getObject - 副本.xml");
	}

	public static void read_getobject2() {
		Xml examXml = Xml.importXml(Test_ReadIn.getobject2());
		if (examXml == null)
			return;
		XmlModelFactory fac = XmlModelFactory.getFactory();
		XmlModel inModel = fac.getInstance(examXml,
				TransferDirect.INPUT_FROM_WEBMASTER);
		examXml.handleWith(inModel);

		// XmlModel outModel = fac.getInstance(inModel.code, "输出");
		// String outStr = outModel.generateXml(tree);
		// System.out.println(outStr);

	}

	public static String reportObjAdd() {
		return xml2Str
				.run("C:\\Documents and Settings\\Administrator\\桌面\\mywork\\已调试的接口\\topo\\reportObjAdd.xml");
	}

	public static void read_reportObjAdd() {

		Xml examXml = Xml.importXml(Test_ReadIn.reportObjAdd());
		if (examXml == null)
			return;
		XmlModelFactory fac = XmlModelFactory.getFactory();
		XmlModel inModel = fac.getInstance(examXml,
				TransferDirect.INPUT_FROM_JOINNODE);
		TreeRecord tree = examXml.handleWith(inModel);

		// XmlModel outModel = fac.getInstance(inModel.code, "输出");
		// String outStr = outModel.generateXml(tree);
		System.out.println(tree.getRecordFieldValue(1, 0, "objID"));

	}

	public static String getSiteNodeLinkStatus() {
		return xml2Str
				.run("C:\\Documents and Settings\\Administrator\\桌面\\mywork\\已调试的接口\\内部\\getSiteNodeLinkStatus(2).xml");
	}

	public static void read_getSiteNodeLinkStatus() {
		Xml examXml = Xml.importXml(Test_ReadIn.getSiteNodeLinkStatus());
		if (examXml == null)
			return;
		XmlModelFactory fac = XmlModelFactory.getFactory();
		XmlModel inModel = fac.getInstance(examXml,
				TransferDirect.INPUT_FROM_JOINNODE);
		TreeRecord tree = examXml.handleWith(inModel);
		System.out.println(tree.getRecordFieldValue(2, 0, "localSiteID"));
	}

	public static String reportSetlinkResult() {
		return xml2Str
				.run("C:\\Documents and Settings\\Administrator\\桌面\\mywork\\已调试的接口\\内部\\reportSetlinkResult.xml");
	}

	public static void read_reportSetlinkResult() {
		Xml examXml = Xml.importXml(Test_ReadIn.reportSetlinkResult());
		if (examXml == null)
			return;
		XmlModelFactory fac = XmlModelFactory.getFactory();
		XmlModel inModel = fac.getInstance(examXml,
				TransferDirect.INPUT_FROM_JOINNODE);
		TreeRecord tree = examXml.handleWith(inModel);
	}

	public static String reportSetlinkStart() {
		return xml2Str
				.run("C:\\Documents and Settings\\Administrator\\桌面\\mywork\\已调试的接口\\内部\\reportSetlinkStart.xml");
	}

	public static void read_reportSetlinkStart() {
		Xml examXml = Xml.importXml(Test_ReadIn.reportSetlinkStart());
		if (examXml == null)
			return;
		XmlModelFactory fac = XmlModelFactory.getFactory();
		XmlModel inModel = fac.getInstance(examXml,
				TransferDirect.INPUT_FROM_JOINNODE);
		TreeRecord tree = examXml.handleWith(inModel);
	}

	public static String reportAlarm() {
		return xml2Str
				.run("C:\\Documents and Settings\\Administrator\\桌面\\mywork\\已调试的接口\\6.5 告警具体数据\\reportAlarm_ 专业网管-综合网管.xml");
	}

	public static void read_reportAlarm() {

		Xml examXml = Xml.importXml(Test_ReadIn.reportAlarm());
		if (examXml == null)
			return;
		XmlModelFactory fac = XmlModelFactory.getFactory();
		XmlModel inModel = fac.getInstance(examXml,
				TransferDirect.INPUT_FROM_JOINNODE);
		examXml.handleWith(inModel);

		// XmlModel outModel = fac.getInstance(inModel.code, "输出");
		// String outStr = outModel.generateXml(tree);
		// System.out.println(outStr);

	}

	public static String reportDatastatus() {
		return xml2Str
				.run("C:\\Documents and Settings\\Administrator\\桌面\\mywork\\已调试的接口\\内部\\reportDatastatus.xml");
	}

	public static void read_reportDatastatus() {
		Xml examXml = Xml.importXml(Test_ReadIn.reportDatastatus());
		if (examXml == null)
			return;
		XmlModelFactory fac = XmlModelFactory.getFactory();
		XmlModel inModel = fac.getInstance(examXml,
				TransferDirect.INPUT_FROM_JOINNODE);
		examXml.handleWith(inModel);

	}

}
