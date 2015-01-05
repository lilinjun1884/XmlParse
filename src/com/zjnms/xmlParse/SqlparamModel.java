package com.zjnms.xmlParse;

/**
 * 表示模板中的存储过程参数模板，在数据表Tbbase_Interdesc中，ename指定为sqlparam的模板为存储过程参数模板
 * 
 * @author llj
 * 
 */
public class SqlparamModel extends EntityModel {
	public SqlparamModel(XmlModel xmlModel, int entityIndex,
			ElmtModel parentElmtModel) {
		super(xmlModel, entityIndex, parentElmtModel);
	}

	public EntityModel recognize() {
		// ------------------------------------------------------------------------------
		// 模板中，ename = sqlparam 说明该模板是一个存储过程输入输出参数，且
		// 当模板的outvalue != null时，模板是输出参数模板，当模板的 invalue != null 时，
		// 模板是输入参数模板
		// ------------------------------------------------------------------------------
		if (outvalue == null ^ invalue == null) {
			EntityModel model = null;
			if (outvalue != null)
				model = new SqlOutparamModel(this.xmlModel, this.entityIndex,
						this.parentElmtModel);

			if (invalue != null)
				model = new SqlInparamModel(this.xmlModel, this.entityIndex,
						this.parentElmtModel);
			this.copyInfo2(model);
			return model;
		} else
			new Exception(this.toString()
					+ " 的 outvalue 或 invalue 需有且只有一个有值，来标识是存储过程的输入参数还是输出参数")
					.printStackTrace();
		return null;
	}
}
