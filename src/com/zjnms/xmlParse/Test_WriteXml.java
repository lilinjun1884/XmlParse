package com.zjnms.xmlParse;

import java.util.List;

/**
 * 测试网络管理设备中，输出到其他系统的接口协议（xml字符串）
 * 
 * @author llj
 * 
 */
public class Test_WriteXml {
	public static void main(String[] arg) {
//		write_reportObjAdd();
		write_setManagedElement();
//		write_reportCfgStatus();
//		write_getEquipStatus();
//		write_reportLogicSubCfgParam();
//		write_reportFreqRequest();
//		write_reportLgSubTrainStatus();
//		write_reportObjDel();
//		write_reportPropChange();
//		write_getAllChildrenOfParent();
//		write_getComponentStatus();
//		write_getMoveNodeLinkStatus();
//		write_setResourceParamCmd();
		write_setLgSubParamCmd1();//任务模式
//		write_setLgSubParamCmd3();//广播模式
	}

	public static void write_setResourceParamCmd() {
		XmlModelFactory factory = XmlModelFactory.getFactory();
		XmlModel model = factory.getInstance("setResourceParamCmd",
				TransferDirect.OUTPUT_TO_JOINNODE);
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
	
	public static void write_getLogicSubCfgParamXml() {
		XmlModelFactory factory = XmlModelFactory.getFactory();
		XmlModel model = factory.getInstance("getLogicSubCfgParam", TransferDirect.OUTPUT_TO_WEBMASTER);
		// 指定数据的起始查询id，该数据与模板是对应的，模板通过数据生成 输出xml文件字符串
		// String pronum = "002";
		Record record = new Record();
		record.addRecordField("taskId", "0255");
		record.addRecordField("taskname", "test");
		record.addRecordField("logicalsubnetid", "13_03_1003");
		TreeRecord tree = new TreeRecord(record, false);

		Record child1 = new Record();
		child1.addRecordField("chainId", "13_03_1001");
		Record child2 = new Record();
		child2.addRecordField("chainId", "13_03_1002");

		tree.addLeafInRoot(child1);
		tree.addLeafInRoot(child2);

		// 用模板生成该数据的xml内容字符串
		String xmlStr = model.generateXml(tree);

		System.out.println(xmlStr);
		// <session sId="781120476356620" username="admin" password="admin">
	}

	public static void write_setManagedElement() {
		XmlModelFactory factory = XmlModelFactory.getFactory();
		XmlModel model = factory.getInstance("setManagedElement", TransferDirect.OUTPUT_TO_JOINNODE);

		Record record1 = new Record();
		// 调用者填写要上报的数据
		record1.addRecordField("meId", "1");
		record1.addRecordField("zyCode", "");
		record1.addRecordField("meType", "");
		record1.addRecordField("meLevel", "");
		record1.addRecordField("superiorDepartName", "");
		record1.addRecordField("superiorDepartIP", "");
		record1.addRecordField("owner", "");
		record1.addRecordField("platId", "");
		record1.addRecordField("nativeName", "");
		record1.addRecordField("manageIP", "1");
		record1.addRecordField("serviceIP", "");
		record1.addRecordField("userLabel", "");
		record1.addRecordField("phone", "");
		record1.addRecordField("jifang", "");
		record1.addRecordField("province", "");
		record1.addRecordField("place", "");
		record1.addRecordField("gisLongitude", "");
		record1.addRecordField("gisLattitude", "1");
		record1.addRecordField("additionalInfo", "");
		record1.addRecordField("accessNodeID", "fs");
		record1.addRecordField("callID", "fs");
		record1.addRecordField("siteNum", "fs");
		record1.addRecordField("siteSecureIP", "fs");
		record1.addRecordField("generalGatewayIP", "fs");
		Record record2 = new Record();
		// 调用者填写要上报的数据
		record2.addRecordField("meId", "1");
		record2.addRecordField("zyCode", "");
		record2.addRecordField("meType", "");
		record2.addRecordField("meLevel", "");
		record2.addRecordField("superiorDepartName", "");
		record2.addRecordField("superiorDepartIP", "");
		record2.addRecordField("owner", "");
		record2.addRecordField("platId", "");
		record2.addRecordField("nativeName", "");
		record2.addRecordField("manageIP", "1");
		record2.addRecordField("serviceIP", "");
		record2.addRecordField("userLabel", "");
		record2.addRecordField("phone", "");
		record2.addRecordField("jifang", "");
		record2.addRecordField("province", "");
		record2.addRecordField("place", "");
		record2.addRecordField("gisLongitude", "");
		record2.addRecordField("gisLattitude", "1");
		record2.addRecordField("additionalInfo", "");
		record1.addRecordField("accessNodeID", "fs");
		record1.addRecordField("callID", "fs");
		record1.addRecordField("siteNum", "fs");
		record1.addRecordField("siteSecureIP", "fs");
		record1.addRecordField("generalGatewayIP", "fs");

		Record root = new Record();
		root.addRecordField("root", "value");
		TreeRecord tree = new TreeRecord(root, true);
		tree.addLeafInRoot(record1);
		tree.addLeafInRoot(record2);

		String xmlStr = model.generateXml(tree);
		System.out.println(xmlStr);
	}

	public static void write_reportObjAdd() {
		XmlModelFactory factory = XmlModelFactory.getFactory();
		XmlModel model = factory.getInstance("reportObjAdd", TransferDirect.OUTPUT_TO_WEBMASTER);

		Record record = new Record();
		// 调用者填写要上报的数据
		// record.addRecordField("sId", "781120476356620");
		// record.addRecordField("username", "admin");
		// record.addRecordField("password", "admin");

		record.addRecordField("objID", "01_22_01||001");
		record.addRecordField("objType", "01");
		record.addRecordField("meId", "01_22_01||001");
		record.addRecordField("zyCode", "");
		record.addRecordField("ownerLogicSubnetIds", "");
		record.addRecordField("ownerTaskIds", "");
		record.addRecordField("meType", "01");
		record.addRecordField("meLevel", "01");
		record.addRecordField("owner", "01");
		record.addRecordField("platId", "17367041");
		record.addRecordField("nativeName", "海军基本指挥所");
		record.addRecordField("Ip", "12.12.192.161");
		record.addRecordField("userLabel", "海军基本指挥所");
		record.addRecordField("communicationState", "1");
		record.addRecordField("serviceState", "1");
		record.addRecordField("phone", "");
		record.addRecordField("jifang", "");
		record.addRecordField("province", "01");
		record.addRecordField("place", "");
		record.addRecordField("gisLongitude", "");
		record.addRecordField("gisLattitude", "");
		record.addRecordField("additionalInfo", "");

		// record.addRecordField("equipmentId", "11");
		// record.addRecordField("zyCode", "11");
		// record.addRecordField("ownerLogicSubnetIds", "11");
		// record.addRecordField("ownerTaskIds", "11");
		// record.addRecordField("equipmentType", "11");
		// record.addRecordField("platId", "11");
		// record.addRecordField("owner", "11");
		// record.addRecordField("nativeName", "11");
		// record.addRecordField("userLabel", "11");
		// record.addRecordField("manufacturedBy", "11");
		// record.addRecordField("place", "11");
		// record.addRecordField("gisLongitude", "11");
		// record.addRecordField("gisLattitude", "11");
		// record.addRecordField("serviceState", "11");
		// record.addRecordField("useState", "11");
		// record.addRecordField("useState", "11");
		// record.addRecordField("eqpIps", "11");
		// record.addRecordField("additionalInfo", "11");

		TreeRecord tree = new TreeRecord(record, false);

		// 用模板生成该数据的xml内容字符串
		String xmlStr = model.generateXml(tree);
		System.out.println(xmlStr);
	}

	public static void write_reportCfgStatus() {
		XmlModelFactory factory = XmlModelFactory.getFactory();
		XmlModel model = factory.getInstance("reportCfgStatus", TransferDirect.OUTPUT_TO_WEBMASTER);

		Record record = new Record();
		// 调用者填写要上报的数据
		record.addRecordField("returnStatus", "1");
		record.addRecordField("meID", "");
		record.addRecordField("systemID", "01_22_0001");
		record.addRecordField("paramReturnType", "1");
		record.addRecordField("taskName", "短波网(V.1.0.5)");
		record.addRecordField("taskID", "1|285278209|20130716152111");

		TreeRecord tree = new TreeRecord(record, false);

		// 用模板生成该数据的xml内容字符串
		String xmlStr = model.generateXml(tree);
		System.out.println(xmlStr);
	}

	public static void write_getEquipStatus() {
		XmlModelFactory factory = XmlModelFactory.getFactory();
		XmlModel model = factory.getInstance("getEquipStatus", TransferDirect.OUTPUT_TO_JOINNODE);

		Record record = new Record();
		// 调用者填写要上报的数据
		record.addRecordField("equipID", "1");

		TreeRecord tree = new TreeRecord(record, false);

		// 用模板生成该数据的xml内容字符串
		String xmlStr = model.generateXml(tree);
		System.out.println(xmlStr);
	}

	public static void write_reportLogicSubCfgParam() {
		XmlModelFactory factory = XmlModelFactory.getFactory();
		XmlModel model = factory.getInstance("reportLogicSubCfgParam", TransferDirect.OUTPUT_TO_WEBMASTER);

		Record root = new Record();
		root.addRecordField("systemID", "01_22_0001");
		root.addRecordField("logicSubnetCount", "1");
		root.addRecordField("taskName", "短波网(V.1.0.5)");
		root.addRecordField("taskID", "1|285278209|20130716152111");

		TreeRecord tree = new TreeRecord(root, false);

		Record record1_1 = new Record();
		record1_1.addRecordField("logicSubnetID", "01_22_01_1001");
		Record record1_2 = new Record();
		record1_2.addRecordField("logicSubnetID", "01_22_01_1002");
		tree.addLeafInRoot(record1_1);
		tree.addLeafInRoot(record1_2);

		List<TreeRecord> childTrees = tree.getChildTrees();

		int recordAmout = 2;
		for (int i = 0; i < childTrees.size(); ++i) {
			TreeRecord child = childTrees.get(i);
			for (int j = 0; j < recordAmout; ++j) {
				Record record2_j = new Record();

				record2_j.addRecordField("freqNum", "16" + (i + 1) + (j + 1));
				record2_j.addRecordField("bizChannelMType", "USB" + (i + 1)
						+ (j + 1));
				record2_j.addRecordField("callChannelMType", "USB" + (i + 1)
						+ (j + 1));
				record2_j.addRecordField("logicSubnetMode", "智能通信" + (i + 1)
						+ (j + 1));
				child.addLeafInRoot(record2_j);
			}
		}

		String xmlStr = model.generateXml(tree);
		System.out.println(xmlStr);
	}

	public static void write_reportLogicSubCfgParam2() {
		XmlModelFactory factory = XmlModelFactory.getFactory();
		XmlModel model = factory.getInstance("reportLogicSubCfgParam", TransferDirect.OUTPUT_TO_WEBMASTER);

		Record root = new Record();
		root.addRecordField("systemID", "01_22_0001");
		root.addRecordField("logicSubnetCount", "1");
		root.addRecordField("taskName", "短波网(V.1.0.5)");
		root.addRecordField("taskID", "1|285278209|20130716152111");

		TreeRecord tree = new TreeRecord(root, false);

		Record record1_1 = new Record();
		record1_1.addRecordField("logicSubnetID", "01_22_01_1001");
		tree.addLeafInRoot(record1_1);

		List<TreeRecord> childTrees = tree.getChildTrees();
		TreeRecord childTree = childTrees.get(0);
		Record record2_1 = new Record();

		record2_1.addRecordField("freqNum", "16");
		record2_1.addRecordField("bizChannelMType", "USB");
		record2_1.addRecordField("callChannelMType", "USB");
		record2_1.addRecordField("logicSubnetMode", "智能通信");
		childTree.addLeafInRoot(record2_1);

		String xmlStr = model.generateXml(tree);
		System.out.println(xmlStr);
	}

	public static void write_reportFreqRequest() {
		XmlModelFactory factory = XmlModelFactory.getFactory();
		XmlModel model = factory.getInstance("reportFreqRequest", TransferDirect.OUTPUT_TO_WEBMASTER);

		Record root = new Record();
		root.addRecordField("taskID", "1|285278209|20130716152111");
		root.addRecordField("logicSubnetID", "01_22_01_1001");
		root.addRecordField("requestFlag", "1");

		TreeRecord tree = new TreeRecord(root, false);

		String xmlStr = model.generateXml(tree);
		System.out.println(xmlStr);
	}

	public static void write_reportLgSubTrainStatus() {
		XmlModelFactory factory = XmlModelFactory.getFactory();
		XmlModel model = factory.getInstance("reportLgSubTrainStatus", TransferDirect.OUTPUT_TO_WEBMASTER);

		Record root = new Record();
		root.addRecordField("reason", "");
		root.addRecordField("trainResult", "1");
		root.addRecordField("notifyType", "2");
		root.addRecordField("logicSubnetID", "01_22_01_1001");
		root.addRecordField("taskID", "1|285278209|20130716152111");

		TreeRecord tree = new TreeRecord(root, false);

		String xmlStr = model.generateXml(tree);
		System.out.println(xmlStr);
	}

	public static void write_ApplyDeviceChain() {
		XmlModelFactory factory = XmlModelFactory.getFactory();
		XmlModel model = factory.getInstance("ApplyDeviceChain", TransferDirect.OUTPUT_TO_WEBMASTER);

		Record root = new Record();
		root.addRecordField("systemID", "01_22_0001");
		root.addRecordField("platID", "17367041");
		root.addRecordField("terID", "02_13_5000||5000||13_01||6");
		root.addRecordField("terAudioType", "12");
		root.addRecordField("operateType", "1");
		root.addRecordField("channelType", "1");
		root.addRecordField("additionalInfo", "");

		TreeRecord tree = new TreeRecord(root, false);

		String xmlStr = model.generateXml(tree);
		System.out.println(xmlStr);
	}

	public static void write_reportObjDel() {
		XmlModelFactory factory = XmlModelFactory.getFactory();
		XmlModel model = factory.getInstance("reportObjDel", TransferDirect.OUTPUT_TO_WEBMASTER);

		Record root = new Record();
		root.addRecordField("objID", "01_22_0001");
		root.addRecordField("objType", "17367041");

		TreeRecord tree = new TreeRecord(root, false);

		String xmlStr = model.generateXml(tree);
		System.out.println(xmlStr);
	}

	public static void write_reportPropChange() {
		XmlModelFactory factory = XmlModelFactory.getFactory();
		XmlModel model = factory.getInstance("reportPropChange", TransferDirect.OUTPUT_TO_WEBMASTER);

		Record root = new Record();
		root.addRecordField("manufacturedBy", "dfgdfg");
		root.addRecordField("objType", "08");
		root.addRecordField("objID", "01_22_0001||001||20_01||0001");
		root.addRecordField("taskID", "1|285278209|20130716152111");
//		root.addRecordField("serviceIP", "01_22_01_1001");

		TreeRecord tree = new TreeRecord(root, false);

		String xmlStr = model.generateXml(tree);
		System.out.println(xmlStr);
	}

	public static void write_getAllChildrenOfParent() {
		XmlModelFactory factory = XmlModelFactory.getFactory();
		XmlModel model = factory.getInstance("getAllChildrenOfParent", TransferDirect.OUTPUT_TO_JOINNODE);
//		// 修改为输出模板
//		ElmtModel elmt = null;
//		try {
//			elmt = model.getRootEntity();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		List<EntityModel> rootAttrs = elmt.getAccompanyModels();
//		rootAttrs.get(0).datafrom = "identity";
//		rootAttrs.get(1).defaultvalue = "admin";
//		rootAttrs.get(2).defaultvalue = "admin";
//		List<ElmtModel> elmts = null;
//		while (!elmt.xmlname.equals("getAllChildrenOfParent")) {
//			elmts = elmt.getChildElmtModels();
//			elmt = elmts.get(0);
//		}
//		for (AttrModelIterator iter = new AttrModelIterator(elmt
//				.getAccompanyModels()); iter.hasNext();) {
//			AttrModel attr = (AttrModel) iter.next();
//			attr.datafrom = "prgm";
//			attr.judge();
//		}
		Record root = new Record();
		root.addRecordField("cType", "dfgdfg");
		root.addRecordField("pType", "08");
		root.addRecordField("pID", "01_22_0001||001||20_01||0001");
		root.addRecordField("count", "1|285278209|20130716152111");

		TreeRecord tree = new TreeRecord(root, false);

		String xmlStr = model.generateXml(tree);
		System.out.println(xmlStr);
	}

	public static void write_getComponentStatus() {
		XmlModelFactory factory = XmlModelFactory.getFactory();
		XmlModel model = factory.getInstance("getComponentStatus", TransferDirect.OUTPUT_TO_JOINNODE);

		Record root = new Record();
		root.addRecordField("componentID", "D18-99");

		TreeRecord tree = new TreeRecord(root, false);

		String xmlStr = model.generateXml(tree);
		System.out.println(xmlStr);
	}
	public static void write_getMoveNodeLinkStatus(){
		XmlModelFactory factory = XmlModelFactory.getFactory();
		XmlModel model = factory.getInstance("getMoveNodeLinkStatus", TransferDirect.OUTPUT_TO_JOINNODE);

		Record root = new Record();
		root.addRecordField("taskID", "1");
		root.addRecordField("logicSubnetID", "2");
		root.addRecordField("subNetSort", "3");
		root.addRecordField("logicSubnetType", "4");
		root.addRecordField("componentID", "4-34");
		
		TreeRecord tree = new TreeRecord(root, false);

		String xmlStr = model.generateXml(tree);
		System.out.println(xmlStr);
	}
	public static void write_setLgSubParamCmd1(){
		XmlModelFactory factory = XmlModelFactory.getFactory();
		XmlModel model = factory.getInstance("setLgSubParamCmd1", TransferDirect.OUTPUT_TO_JOINNODE);

		Record root = new Record();
		root.addRecordField("taskID", "00|319029250|20130911150805");
		root.addRecordField("logicSubnetID", "00_22_13_6004");
		root.addRecordField("subNetSort", "3");
		root.addRecordField("logicSubnetType", "4");
		
		TreeRecord tree = new TreeRecord(root, false);

		String xmlStr = model.generateXml(tree);
		System.out.println(xmlStr);
	}
	
	public static void write_setLgSubParamCmd3(){
		XmlModelFactory factory = XmlModelFactory.getFactory();
		XmlModel model = factory.getInstance("setLgSubParamCmd3", TransferDirect.OUTPUT_TO_JOINNODE);

		Record root = new Record();
		root.addRecordField("taskID", "1|285278209|20130716152111");
		root.addRecordField("logicSubnetID", "01_22_01_1001");
		root.addRecordField("subNetSort", "3");
		root.addRecordField("logicSubnetType", "4");
		
		TreeRecord tree = new TreeRecord(root, false);

		String xmlStr = model.generateXml(tree);
		System.out.println(xmlStr);
	}

}
