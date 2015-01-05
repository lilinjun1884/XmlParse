package com.zjnms.xmlParse;

/**
 * 表示模板中的存储过程输入参数模板，在数据表Tbbase_Interdesc中， ename指定为sqlparam，且outvalue !=
 * null的模板为存储过程输入参数模板
 * 
 * @author llj
 * 
 */                                                                      
public class SqlOutparamModel extends SqlparamModel {                    
	public SqlOutparamModel(XmlModel xmlModel, int entityIndex,          
			ElmtModel parentElmtModel) {                                 
		super(xmlModel, entityIndex, parentElmtModel);                   
	}                                                                    
                                                                         
	public String getOutKey() {       
		// --------------------------------------------------------
		// 存储过程输出参数模板以 colname 作为输出键
		// --------------------------------------------------------
		if (this.colname == null)
			new Exception("输出参数的 colname 不能为空").printStackTrace();
		return this.colname;
	}
}
