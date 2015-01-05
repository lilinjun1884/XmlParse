package com.zjnms.xmlParse;

/**
 * 测试接入节点中，从外部输入的接口协议（xml字符串）
 * 
 * @author llj
 * 
 */
public class Test_JoinNode_ReadIn {
	private static Xml2Str xml2Str = new Xml2Str();

	public static void main(String[] args) {
//		read_reportEquipStatus();
//		read_reportHeartbeat();
//		read_reportChannelStatus();
//		read_reportChannelQuality();
//		read_reportNetStatus();
//		read_reportSetlinkResult();
//		read_reportRemovelinkResult();
//		read_reportRemovelinkStart();
//		write_reportAlarm();
//		write_getSiteNodeLinkStatus();
//		write_getNetStatus();
//		write_getEquipStatus();
//		write_getChannelStatus();
//		read_reportMoveNodeLinkStatus();
		read_setLgSubParamCmd();
//		read_setLgSubParamCmd2();//常设模式
//		read_setLgSubParamCmd3();//广播模式
//		read_reportchannelDetectResult();
//		read_setManagedElement();
//		read_setLgSubWorkCmd();
	}
	
	public static String reportRemovelinkStart() {
		return xml2Str
				.run("C:\\Documents and Settings\\Administrator\\桌面\\mywork\\已调试的接口\\内部\\reportRemovelinkStart.xml");
	}

	public static void read_reportRemovelinkStart() {
		Xml examXml = Xml.importXml(Test_JoinNode_ReadIn.reportRemovelinkStart());
		if (examXml == null)
			return;
		XmlModelFactory fac = XmlModelFactory.getFactory();
		XmlModel inModel = fac.getInstance(examXml,
				TransferDirect.INPUT_FROM_JOINMANAGE);
		TreeRecord tree = examXml.handleWith(inModel);
	}
	
	public static String reportRemovelinkResult() {
		return xml2Str
				.run("C:\\Documents and Settings\\Administrator\\桌面\\mywork\\已调试的接口\\内部\\reportRemovelinkResult.xml");
	}

	public static void read_reportRemovelinkResult() {
		Xml examXml = Xml.importXml(Test_JoinNode_ReadIn.reportRemovelinkResult());
		if (examXml == null)
			return;
		XmlModelFactory fac = XmlModelFactory.getFactory();
		XmlModel inModel = fac.getInstance(examXml,
				TransferDirect.INPUT_FROM_JOINMANAGE);
		TreeRecord tree = examXml.handleWith(inModel);
	}
	
	public static String setLgSubWorkCmd(){
		return xml2Str
				.run("C:\\Documents and Settings\\Administrator\\桌面\\mywork\\已调试的接口\\内部\\setLgSubWorkCmd.xml");
	
	}
	public static void read_setLgSubWorkCmd(){
		Xml examXml = Xml.importXml(Test_JoinNode_ReadIn.setLgSubWorkCmd());
		if (examXml == null)
			return;
		XmlModelFactory fac = XmlModelFactory.getFactory();
		XmlModel inModel = fac.getInstance(examXml,
				TransferDirect.INPUT_FROM_NETMANAGE);
		TreeRecord tree = examXml.handleWith(inModel);
	}
	
	public static void read_setManagedElement(){
		Xml examXml = Xml.importXml(Test_JoinNode_ReadIn.setManagedElement());
		if (examXml == null)
			return;
		XmlModelFactory fac = XmlModelFactory.getFactory();// date_v
		XmlModel inModel = fac.getInstance(examXml,
				TransferDirect.INPUT_FROM_NETMANAGE);
		TreeRecord tree = examXml.handleWith(inModel);
	}
	public static String setManagedElement(){
		return xml2Str
				.run("C:\\Documents and Settings\\Administrator\\桌面\\mywork\\已调试的接口\\内部\\setManagedElement.xml");
	
	}
	public static String reportchannelDetectResult(){
		return xml2Str
				.run("C:\\Documents and Settings\\Administrator\\桌面\\mywork\\已调试的接口\\内部\\reportchannelDetectResult.xml");
	}
	public static void read_reportchannelDetectResult(){
		Xml examXml = Xml.importXml(Test_JoinNode_ReadIn.reportchannelDetectResult());
		if (examXml == null)
			return;
		XmlModelFactory fac = XmlModelFactory.getFactory();
		XmlModel inModel = fac.getInstance(examXml,
				TransferDirect.INPUT_FROM_JOINMANAGE);
		TreeRecord tree = examXml.handleWith(inModel);
		System.out.println(tree.getRecordFieldValue(1, 0, "result"));
	}
	
	
	public static String setLgSubParamCmd(){
		return xml2Str
				.run("C:\\Documents and Settings\\Administrator\\桌面\\mywork\\已调试的接口\\子网规划参数下发_任务模式.xml");
//		return xml2Str
//				.run("C:\\Documents and Settings\\Administrator\\桌面\\新建 文本文档 (5).xml");
	}
	public static void read_setLgSubParamCmd(){
		Xml examXml = Xml.importXml(Test_JoinNode_ReadIn.setLgSubParamCmd());
		if (examXml == null)
			return;
		XmlModelFactory fac = XmlModelFactory.getFactory();
		XmlModel inModel = fac.getInstance("setLgSubParamCmd1",
				TransferDirect.INPUT_FROM_LOWERJOINMANAGE);
		TreeRecord tree = examXml.handleWith(inModel);
	}
	public static String reportMoveNodeLinkStatus() {
		return xml2Str
				.run("C:\\Documents and Settings\\Administrator\\桌面\\mywork\\已调试的接口\\1002 reportMoveNodeLinkStatus.xml");
	}
	
	public static String setLgSubParamCmd2(){
		return xml2Str
				.run("C:\\Documents and Settings\\Administrator\\桌面\\mywork\\已调试的接口\\子网规划参数下发_常设模式.xml");
	}
	public static void read_setLgSubParamCmd2(){
		Xml examXml = Xml.importXml(Test_JoinNode_ReadIn.setLgSubParamCmd2());
		if (examXml == null)
			return;
		XmlModelFactory fac = XmlModelFactory.getFactory();
		XmlModel inModel = fac.getInstance("setLgSubParamCmd2",
				TransferDirect.INPUT_FROM_NETMANAGE);
		TreeRecord tree = examXml.handleWith(inModel);
	}
	
	public static String setLgSubParamCmd3(){
		return xml2Str
				.run("C:\\Documents and Settings\\Administrator\\桌面\\mywork\\已调试的接口\\子网规划参数下发_广播模式.xml");
	}
	public static void read_setLgSubParamCmd3(){
		Xml examXml = Xml.importXml(Test_JoinNode_ReadIn.setLgSubParamCmd3());
		if (examXml == null)
			return;
		XmlModelFactory fac = XmlModelFactory.getFactory();
		XmlModel inModel = fac.getInstance("setLgSubParamCmd3",
				TransferDirect.INPUT_FROM_NETMANAGE);
		TreeRecord tree = examXml.handleWith(inModel);
	}
	
	

	public static void read_reportMoveNodeLinkStatus() {
		Xml examXml = Xml.importXml(Test_JoinNode_ReadIn.reportMoveNodeLinkStatus());
		if (examXml == null)
			return;
		XmlModelFactory fac = XmlModelFactory.getFactory();
		XmlModel inModel = fac.getInstance(examXml,
				TransferDirect.INPUT_FROM_JOINMANAGE);
		// TODO 組記錄未正確分組
		TreeRecord tree = examXml.handleWith(inModel);
		System.out.println(tree.getRecordFieldValue(3, 0, "movePlatId"));

	}
	
	public static void read_reportEquipStatus() {
		Xml examXml = Xml.importXml(Test_JoinNode_ReadIn.reportEquipStatus());
		if (examXml == null)
			return;
		XmlModelFactory fac = XmlModelFactory.getFactory();
		XmlModel inModel = fac.getInstance(examXml,
				TransferDirect.INPUT_FROM_JOINMANAGE);

		// TreeRecord tree= examXml.getRequestData(inModel);
		TreeRecord tree = examXml.handleWith(inModel);
		Record root = tree.getRoot();
		// String id = root.getRecordFieldValue("equipID");
		tree.getRecordFieldValue(2, 0, "equipID");
		//String service = root.getRecordFieldValue("serviceStatus");

		//System.out.println(id + " " + service);
		// System.out.println("is empty tree:" + tree.isEmptyTree());
	}

	public static String reportEquipStatus() {
		return xml2Str
				.run("C:\\Documents and Settings\\Administrator\\桌面\\mywork\\已调试的接口\\内部\\reportEquipStatus.xml");
	}

	public static String reportHeartbeat() {
		return xml2Str
				.run("C:\\Documents and Settings\\Administrator\\桌面\\mywork\\已调试的接口\\内部\\1000 reportHeartbeat.xml");
	}

	public static void read_reportHeartbeat() {
		Xml examXml = Xml.importXml(Test_JoinNode_ReadIn.reportHeartbeat());
		if (examXml == null)
			return;
		XmlModelFactory fac = XmlModelFactory.getFactory();
		XmlModel inModel = fac.getInstance(examXml,
				TransferDirect.INPUT_FROM_JOINMANAGE);
		TreeRecord tree = examXml.handleWith(inModel);
		// XmlModel outModel = fac.getInstance(inModel.code, "输出");
		// String outStr = outModel.generateXml(tree);
		// System.out.println(outStr);

	}

	public static String reportChannelStatus() {
		return xml2Str
				.run("C:\\Documents and Settings\\Administrator\\桌面\\mywork\\已调试的接口\\内部\\reportChannelStatus.xml");
	}

	public static void read_reportChannelStatus() {
		Xml examXml = Xml.importXml(Test_JoinNode_ReadIn.reportChannelStatus());
		if (examXml == null)
			return;
		XmlModelFactory fac = XmlModelFactory.getFactory();
		XmlModel inModel = fac.getInstance(examXml,
				TransferDirect.INPUT_FROM_JOINMANAGE);
		TreeRecord tree = examXml.handleWith(inModel);
		// XmlModel outModel = fac.getInstance(inModel.code, "输出");
		// String outStr = outModel.generateXml(tree);
		// System.out.println(outStr);

	}

	public static String reportChannelQuality() {
		return xml2Str
				.run("C:\\Documents and Settings\\Administrator\\桌面\\mywork\\已调试的接口\\内部\\reportChannelQuality.xml");
	}

	public static void read_reportChannelQuality() {
		Xml examXml = Xml
				.importXml(Test_JoinNode_ReadIn.reportChannelQuality());
		if (examXml == null)
			return;
		XmlModelFactory fac = XmlModelFactory.getFactory();
		XmlModel inModel = fac.getInstance(examXml,
				TransferDirect.INPUT_FROM_JOINMANAGE);
		TreeRecord tree = examXml.handleWith(inModel);

		// XmlModel outModel = fac.getInstance(inModel.code, "输出");
		// String outStr = outModel.generateXml(tree);
		// System.out.println(outStr);
	}

	public static String reportNetStatus() {
		return xml2Str
				.run("C:\\Documents and Settings\\Administrator\\桌面\\mywork\\已调试的接口\\内部\\reportNetStatus.xml");
	}

	public static void read_reportNetStatus() {
		Xml examXml = Xml.importXml(Test_JoinNode_ReadIn.reportNetStatus());
		if (examXml == null)
			return;
		XmlModelFactory fac = XmlModelFactory.getFactory();
		XmlModel inModel = fac.getInstance(examXml,
				TransferDirect.INPUT_FROM_JOINMANAGE);
		examXml.handleWith(inModel);
	}

	public static String reportSetlinkResult() {
		return xml2Str
				.run("C:\\Documents and Settings\\Administrator\\桌面\\mywork\\已调试的接口\\内部\\reportSetlinkResult.xml");
	}

	public static void read_reportSetlinkResult() {
		Xml examXml = Xml.importXml(Test_JoinNode_ReadIn.reportSetlinkResult());
		if (examXml == null)
			return;
		XmlModelFactory fac = XmlModelFactory.getFactory();
		XmlModel inModel = fac.getInstance(examXml,
				TransferDirect.INPUT_FROM_JOINMANAGE);
		TreeRecord tree = examXml.handleWith(inModel);
	}

	public static String getSiteNodeLinkStatus() {
		return xml2Str
				.run("C:\\Documents and Settings\\Administrator\\桌面\\mywork\\已调试的接口\\内部\\getSiteNodeLinkStatus(2).xml");
	}

	public static void write_getSiteNodeLinkStatus() {
		Xml examXml = Xml.importXml(Test_JoinNode_ReadIn
				.getSiteNodeLinkStatus());
		if (examXml == null)
			return;
		XmlModelFactory fac = XmlModelFactory.getFactory();
		XmlModel inModel = fac.getInstance(examXml,
				TransferDirect.INPUT_FROM_JOINMANAGE);
		TreeRecord tree = examXml.handleWith(inModel);
		// System.out.println(tree.getRecordFieldValue(2, 0, "localSiteID"));
	}

	public static String reportAlarm() {
		return xml2Str
				.run("C:\\Documents and Settings\\Administrator\\桌面\\mywork\\已调试的接口\\内部\\reportAlarm.xml");
	}

	public static void write_reportAlarm() {
		Xml examXml = Xml.importXml(Test_JoinNode_ReadIn.reportAlarm());
		if (examXml == null)
			return;
		XmlModelFactory fac = XmlModelFactory.getFactory();
		XmlModel inModel = fac.getInstance(examXml,
				TransferDirect.INPUT_FROM_JOINMANAGE);
		TreeRecord tree = examXml.handleWith(inModel);
		// System.out.println(tree.getRecordFieldValue(2, 0, "localSiteID"));
	}

	public static String getNetStatus() {
		return xml2Str
				.run("C:\\Documents and Settings\\Administrator\\桌面\\mywork\\已调试的接口\\内部\\getNetStatus.xml");
	}

	public static void write_getNetStatus() {
		Xml examXml = Xml.importXml(Test_JoinNode_ReadIn.getNetStatus());
		if (examXml == null)
			return;
		XmlModelFactory fac = XmlModelFactory.getFactory();
		XmlModel inModel = fac.getInstance(examXml,
				TransferDirect.INPUT_FROM_JOINMANAGE);
		TreeRecord tree = examXml.handleWith(inModel);
	}

	public static String getEquipStatus() {
		return xml2Str
				.run("C:\\Documents and Settings\\Administrator\\桌面\\mywork\\已调试的接口\\内部\\getEquipStatus.xml");
	}

	public static void write_getEquipStatus() {
		Xml examXml = Xml.importXml(Test_JoinNode_ReadIn.getEquipStatus());
		if (examXml == null)
			return;
		XmlModelFactory fac = XmlModelFactory.getFactory();
		XmlModel inModel = fac.getInstance(examXml,
				TransferDirect.INPUT_FROM_JOINMANAGE);
		TreeRecord tree = examXml.handleWith(inModel);
	}

	public static String getChannelStatus() {
		return xml2Str
				.run("C:\\Documents and Settings\\Administrator\\桌面\\mywork\\已调试的接口\\内部\\getChannelStatus.xml");
	}

	public static void write_getChannelStatus() {
		Xml examXml = Xml.importXml(Test_JoinNode_ReadIn.getChannelStatus());
		if (examXml == null)
			return;
		XmlModelFactory fac = XmlModelFactory.getFactory();
		XmlModel inModel = fac.getInstance(examXml,
				TransferDirect.INPUT_FROM_JOINMANAGE);
		TreeRecord tree = examXml.handleWith(inModel);
	}
}
