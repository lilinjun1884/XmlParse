package com.zjnms.xmlParse;

/**
 * 
 * @author llj
 * 
 */
public class ModelException extends Exception {
	private static final long serialVersionUID = -3909784248197809974L;
	private EntityModel model;

	/**
	 * 不匹配的模板和xml字符串
	 * 
	 * @param model
	 */
	public ModelException(EntityModel model) {
		this(model, "");
	}

	public ModelException(EntityModel model, String mess) {
		super(mess);
		this.model = model;
	}

	/**
	 * 
	 */
	public String getMessage() {
		return model.toString() + " => " + super.getMessage();
	}
}

/**
 * 未找到模板描述的xml元素异常
 * 
 * @author llj
 * 
 */
class NotFindElmtDescribeByModel extends Exception {
	private static final long serialVersionUID = 8849980507093287983L;
	private EntityModel model;

	/**
	 * 未找到模板描述的xml元素异常
	 * 
	 * @param model
	 */
	public NotFindElmtDescribeByModel(EntityModel model) {
		this.model = model;
	}

	public String getMessage() {
		return "未找到模板中定义的元素: " + model.toString() + " ，请检查模板和xml字符串";
	}
}

/**
 * 不匹配的模板和xml字符串
 * 
 * @author llj
 * 
 */
class XmlAndModelNotMatching extends Exception {
	private static final long serialVersionUID = 628135645788297399L;
	private EntityModel model;

	/**
	 * 不匹配的模板和xml字符串
	 * 
	 * @param model
	 */
	public XmlAndModelNotMatching(EntityModel model) {
		this.model = model;
	}

	public String getMessage() {
		return model.toString() + " 的子模板与xml字符串描述的不一致，请检查是否正确填写了pnodeid和nodeid";
	}
}
