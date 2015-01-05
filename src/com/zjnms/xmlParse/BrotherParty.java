package com.zjnms.xmlParse;

import java.util.ArrayList;
import java.util.List;

/**
 * xml兄弟元素交互的对象，处理兄弟元素间的数据请求
 * 
 * @author llj
 * 
 */
public class BrotherParty {
	private List<Model_Elmt_Binder> brothers;
	private List<Model_Elmt_Binder> requesters;
	private boolean hasOrdered = false;

	public BrotherParty(List<Model_Elmt_Binder> brothers) {
		this.brothers = brothers;
		requesters = new ArrayList<Model_Elmt_Binder>();
	}

	/**
	 * 判断是否已安排了该会议
	 * 
	 * @return
	 */
	public boolean hasOrdered() {
		return hasOrdered;
	}

	/**
	 * 尝试安排会议
	 * 
	 * @return
	 */
	public boolean tryOrder() {
		for (int i = 0; i < brothers.size(); ++i) {
			Model_Elmt_Binder brother = brothers.get(i);
			ElmtModel nodePoint = brother.getElmtModel();
			if (nodePoint.hasRequestBrother()) {
				requesters.add(brother);
			}
		}
		if (requesters.size() == 0)
			hasOrdered = false;
		else
			hasOrdered = true;
		return hasOrdered;
	}

	/**
	 * 开始会议
	 * 
	 * @return
	 */
	public boolean start() {
		if (!hasOrdered)
			return false;
		for (int i = 0; i < requesters.size(); ++i) {
			Model_Elmt_Binder requester = requesters.get(i);
			boolean canResponse = false;
			for (int j = 0; j < this.brothers.size(); ++j) {
				Model_Elmt_Binder responser = brothers.get(j);
				if (requester.equals(responser))
					continue;
				// if (!requester.hasSameParentElmt(responser))
				// continue;
				if (canResponse = responser.helpBrother(requester))
					break;
			}
			if (!canResponse){
				new Exception(requester.toString()+" 无法向兄弟请求数据").printStackTrace();
//				System.out.println(MyElement.equalParent(requester.getXmlPoint().getCurrXmlElmts().get(0), brothers.get(0).getXmlPoint().getCurrXmlElmts().get(0)));
			}
		}
		return true;
	}

}
