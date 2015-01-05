package com.zjnms.xmlParse;

/**
 * 测试接入节点中，输出到其他系统的接口协议（xml字符串）
 * 
 * @author llj
 * 
 */
public class Test_JoinNode_WriteXml {
	public static void main(String[] arg) {
//		write_reportChannelQuality();
//		write_reportObjAdd();
//		setResourceParamCmd();
		write_channelDetectPorpConfig();
	}

	public static void write_channelDetectPorpConfig() {
		XmlModelFactory factory = XmlModelFactory.getFactory();
		XmlModel model = factory.getInstance("channelDetectPorpConfig",
				TransferDirect.OUTPUT_TO_JOINMANAGE);

		Record record = new Record();
		// 调用者填写要上报的数据
		record.addRecordField("taskId", "1");
		record.addRecordField("logicSubnetId", "01_22_0001");
		record.addRecordField("channelNum", "32");
		record.addRecordField("componentId", "12");
		record.addRecordField("operateType", "34");
		TreeRecord tree = new TreeRecord(record, false);

		// 用模板生成该数据的xml内容字符串
		String xmlStr = model.generateXml(tree);
		System.out.println(xmlStr);
	}

	public static void write_reportChannelQuality() {
		XmlModelFactory factory = XmlModelFactory.getFactory();
		XmlModel model = factory.getInstance("reportChannelQuality",
				TransferDirect.OUTPUT_TO_NETMANAGE);

		Record record = new Record();
		// 调用者填写要上报的数据
		record.addRecordField("taskID", "1");
		record.addRecordField("logicSubnetID", "01_22_0001");
		record.addRecordField("sitePlatID", "32");
		record.addRecordField("movePlatID", "12");
		record.addRecordField("freq", "34");
		record.addRecordField("Level", "1");
		record.addRecordField("time", "13");
		TreeRecord tree = new TreeRecord(record, false);

		// 用模板生成该数据的xml内容字符串
		String xmlStr = model.generateXml(tree);
		System.out.println(xmlStr);
	}

	public static void write_reportObjAdd() {
		XmlModelFactory factory = XmlModelFactory.getFactory();
		XmlModel model = factory.getInstance("reportObjAdd",
				TransferDirect.OUTPUT_TO_NETMANAGE);

		Record record = new Record();
		record.addRecordField("netId", "1");
		record.addRecordField("platId", "01_22_0001");
		record.addRecordField("userLabel", "01_22_0001");

		TreeRecord tree = new TreeRecord(record, false);
		String xmlStr = model.generateXml(tree);
		System.out.println(xmlStr);
	}

	public static void setResourceParamCmd() {
		XmlModelFactory factory = XmlModelFactory.getFactory();
		XmlModel model = factory.getInstance("setResourceParamCmd",
				TransferDirect.OUTPUT_TO_JOINMANAGE);
		Record record = new Record();
		record.addRecordField("siteManageEquiptID", "01_22_0001");
		record.addRecordField("siteManageEquiptIP", "01_22_0001");
		record.addRecordField("trafficManageIP", "01_22_0001");
		TreeRecord tree = new TreeRecord(record, false);

		Record child = new Record();
		child.addRecordField("equipID", "1");
		child.addRecordField("manageIP", "01_22_0001");
		child.addRecordField("serviceIP", "01_22_0001");
		child.addRecordField("voiceIP", "01_22_0001");

		Record child12 = new Record();
		child12.addRecordField("equipID", "1");
		child12.addRecordField("manageIP", "01_22_0001");
		child12.addRecordField("serviceIP", "01_22_0001");
		child12.addRecordField("voiceIP", "01_22_0001");
		TreeRecord tempTree = tree.getLeafs().get(0);
		TreeRecord childTree1 = tempTree.addLeafInRoot(child);
		TreeRecord childTree2 = tempTree.addLeafInRoot(child12);

		Record child21 = new Record();
		child21.addRecordField("componentID", "1");
		child21.addRecordField("componentType", "01_22_0001");
		childTree1.addLeafInRoot(child21);

		Record child22 = new Record();
		child22.addRecordField("componentID", "1");
		child22.addRecordField("componentType", "01_22_0001");
		childTree2.addLeafInRoot(child22);
		String xmlStr = model.generateXml(tree);
		System.out.println(xmlStr);
	}
}
