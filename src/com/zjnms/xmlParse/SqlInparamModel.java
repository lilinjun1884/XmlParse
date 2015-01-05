package com.zjnms.xmlParse;

import java.util.ArrayList;
import java.util.List;

/**
 * 表示模板中的存储过程输入参数模板，在数据表Tbbase_Interdesc中， ename指定为sqlparam，且invalue !=
 * null的模板为存储过程输入参数模板
 * 
 * @author llj
 * 
 */
public class SqlInparamModel extends SqlparamModel implements
		TreeRecordRequester {
	private boolean hasDataFromPrgm = false;

	public SqlInparamModel(XmlModel xmlModel, int entityIndex,
			ElmtModel parentElmtModel) {
		super(xmlModel, entityIndex, parentElmtModel);
	}

	/**
	 * 判断模板的数据是否来源于树记录
	 * 
	 * @return
	 */
	public boolean dataFromPrgm() {
		return hasDataFromPrgm;
	}

	public boolean dataFromMyElement() {
		return this.hasDataFrom() && !hasDataFromPrgm;
	}

	/**
	 * 获取从数据来源中查找值的键名
	 * 
	 * @return
	 */
	public String getDataFromFieldName() {
		if (!this.hasDataFrom())
			new Exception(this.toString() + ": datafrom为空").printStackTrace();
		if (hasDataFromPrgm) {
			if (colname == null)
				new NullPointerException(this.toString()
						+ ": sqlInparamModel的datafrom=prgm 时，colname不能为空")
						.printStackTrace();
			return this.colname;
		} else
			return datafrom;
	}

	public void judge() {
		super.judge();
		if (this.hasDataFrom()) {
			if (this.datafrom.equals("prgm"))
				this.hasDataFromPrgm = true;
		}
	}

	public void requestGroupRecords(TreeRecord tree) {
		if (!hasDataFromPrgm)
			new Exception(this.toString() + ": datafrom != prgm，不能从外部获取记录")
					.printStackTrace();
		// 从树记录中获取存储过程参数组记录（非迭代方式）
		if (!this.parentElmtModel.hasReadGroupRecord()) {
			List<String> keys = new ArrayList<String>();
			List<EntityModel> accompanies = this.parentElmtModel
					.getAccompanyModels();
			for (int i = 0; i < accompanies.size(); ++i) {
				EntityModel accompany = accompanies.get(i);
				if (accompany instanceof SqlInparamModel) {
					SqlInparamModel inparam = (SqlInparamModel) accompany;
					if (inparam.hasDataFromPrgm)
						keys.add(inparam.getDataFromFieldName());
				}
			}
			this.parentElmtModel.setGroupRecord(tree.getGroupRecord(keys));
		}
	}

}
