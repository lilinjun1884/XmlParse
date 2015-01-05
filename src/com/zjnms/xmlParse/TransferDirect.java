package com.zjnms.xmlParse;

/**
 * 以本系统为中心，表示接口的输入输出方向的枚举
 * 
 * @author llj
 * 
 */
public enum TransferDirect {
	/**
	 * 来自综合网管的输入
	 */
	INPUT_FROM_WEBMASTER,
	/**
	 * 来自接入节点的输入
	 */
	INPUT_FROM_JOINNODE,
	/**
	 * 来自台站的输入
	 */
	INPUT_FROM_STATION,
	/**
	 * 来自网络管理设备的输入
	 */
	INPUT_FROM_NETMANAGE,
	/**
	 * 来自接入控制器的输入
	 */
	INPUT_FROM_JOINMANAGE,
	/**
	 * 来自下级网管的输入
	 */
	INPUT_FROM_LOWERJOINMANAGE,
	/**
	 * 到综合网管的输出
	 */
	OUTPUT_TO_WEBMASTER,
	/**
	 * 到接入节点的输出
	 */
	OUTPUT_TO_JOINNODE,
	/**
	 * 到台站的输出
	 */
	OUTPUT_TO_STATION,
	/**
	 * 到网络管理设备的输出
	 */
	OUTPUT_TO_NETMANAGE,
	/**
	 * 到接入控制器的输出
	 */
	OUTPUT_TO_JOINMANAGE,
	/**
	 * 到下级网管的输出
	 */
	OUTPUT_TO_LOWERJOINMANAGE,
	/**
	 * 到上级网管的输出
	 */
	OUTPUT_TO_UPERJOINMANAGE
}
