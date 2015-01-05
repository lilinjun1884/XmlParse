package com.zjnms.xmlParse;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import oracle.jdbc.OracleTypes;

/**
 * 存储过程执行对象，表示存储过程的一次执行，标记一个指定的存储过程。需要按顺序提供执行它的参数
 * 
 * @author llj
 * 
 */
public class ProcedureProcess {
	// 存储过程呼叫string，保存在接口模板的tableName中
	public String procedureCallStr;
	private List<ProcParam> procParamValues;

	// private DataAccess da = new DataAccess();;

	/**
	 * 构造存储过程执行对象
	 * 
	 * @param procedureCallStr
	 *            存储过程呼叫字符串 format: {call procedureName(?,?,..)}
	 */
	public ProcedureProcess(String procedureCallStr) {
		this.procedureCallStr = procedureCallStr;
		procParamValues = new ArrayList<ProcParam>();
	}

	/**
	 * 在打开的连接上执行插入一条记录的存储过程（不自动提交） ，返回该插入操作的输出参数，传递连接对象的参数是为了由连接对象自己控制提交
	 * 
	 * @return 返回该插入操作的输出参数
	 * @exception ExcuteProcedureException
	 *                执行出错时抛出
	 */
	public List<ProcParam> insertWithReturnOutParam(Connection conn)
			throws ExcuteProcedureException, NumberFormatException {
		// 存储过程调用对象
		try {
			CallableStatement proc = null;
			// 检查参数个数是否相符
			if (!procParamAmountCheck())
				new ExcuteProcedureException("参数不一致").printStackTrace();

			if (conn.isClosed())
				new ExcuteProcedureException("已关闭的连接").printStackTrace();
			// 取消自动提交
			if (conn.getAutoCommit())
				conn.setAutoCommit(false);

			proc = conn.prepareCall(procedureCallStr);
			// 设置参数
			for (int i = 0; i < procParamValues.size(); ++i) {
				setParam(proc, procParamValues.get(i), i + 1);
			}
			// 执行该存储过程
			proc.execute();
			List<ProcParam> params = getOutparam(proc);
			proc.close();
			return params;
		} catch (NumberFormatException e) {
			throw e;
		} catch (SQLException e) {
			try {
				// 出现异常则回滚事务
				conn.rollback();
				System.out.println("已回滚");
				conn.close();
				// e.printStackTrace();
				System.out.println(e.getMessage());
			} catch (SQLException ex) {
				try {
					// 回滚的异常处理
					conn.close();
					// ex.printStackTrace();
				} catch (SQLException exc) {
					// 关闭连接的异常处理
					// TODO 写入系统日志
					throw new ExcuteProcedureException("执行出错，回滚事务失败，无法关闭数据库连接");
				}
				throw new ExcuteProcedureException("执行出错，回滚事务失败");
			}
			// 回滚后续抛异常，告诉调用者事务已被中断，由调用者再次处理该异常
			throw new ExcuteProcedureException("执行出错，已回滚事务");
		}
		// 返回自增主键，自增主键置于参数列的最后，参数的下标从1开始
		// return proc.getInt(procParamValues.size());
	}

	/**
	 * 获取存储过程的输出参数
	 * 
	 * @param proc
	 * @return
	 */
	private List<ProcParam> getOutparam(CallableStatement proc) {
		List<ProcParam> keys = new ArrayList<ProcParam>();
		try {
			for (int i = 0; i < this.procParamValues.size(); ++i) {
				ProcParam param = this.procParamValues.get(i);
				if (param.getDirect() != -1)
					continue;
				String value;
				if (param.getParamType().equals("number")) {
					value = Integer.valueOf(proc.getInt(i + 1)).toString();
					param.setParamValue(value);
				}
				if (param.getParamType().equals("varchar2")) {
					value = proc.getString(i + 1);
					param.setParamValue(value);
				}
				keys.add(param);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return keys;
	}

	/**
	 * 查询并返回结果集的ResultSet对象，由调用者管理连接对象
	 * 
	 * @return
	 */
	public ResultSet selectInRS(Connection conn) throws NumberFormatException {
		// 检查参数个数是否相符
		if (!procParamAmountCheck()) {
			System.out.println("参数不一致");
			return null;
		}
		try {
			CallableStatement proc = conn.prepareCall(procedureCallStr);
			// 设置参数
			// 游标参数位于参数列表的最后
			for (int i = 0; i < procParamValues.size(); ++i) {
				setParam(proc, procParamValues.get(i), i + 1);
			}
			proc.execute();
			int cursorIndex = getCursorParamIndex();
			ResultSet res = (ResultSet) proc.getObject(cursorIndex);
			return res;
		} catch (NumberFormatException e) {
			throw e;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	private int getCursorParamIndex() {
		for (int i = 0; i < this.procParamValues.size(); ++i) {
			if (this.procParamValues.get(i).getParamType().equals("cursor"))
				return i + 1;
		}
		return -1;
	}

	/**
	 * 设置存储过程执行对象的参数
	 * 
	 * @param proc
	 *            存储过程语句对象
	 * @param paramValue
	 *            参数值
	 * @param paramIndex
	 *            参数的下标（从1开始）
	 */
	private void setParam(CallableStatement proc, ProcParam paramValue,
			int paramIndex) throws NumberFormatException {
		try {
			String value = paramValue.getParamValue();
			String type = paramValue.getParamType();
			int direct = paramValue.getDirect();
			if (type == null){
				new Exception("参数的类型不能为空").printStackTrace();
				return;
			}
			if (direct == 1) {

				// 字符串参数
				if (type.equals("varchar2"))
					proc.setString(paramIndex, value);
				// 数字参数
				if (type.equals("number")) {
					// System.out.println(paramValue.getParamName()+" "+type+" "+value);
					proc.setInt(paramIndex, Integer.parseInt(value));
				}
				// 日期参数
				if (type.equals("date"))
					proc.setDate(paramIndex, new Date(Long.parseLong(value)));
			} else if (direct == -1) {

				// 自增主键位于insert的存储过程，游标参数位于select的存储过程
				// 自增主键参数，置于参数列的最后，输入中用到
				if (type.equals("varchar2"))
					proc.registerOutParameter(paramIndex, Types.VARCHAR);
				if (type.equals("number"))
					proc.registerOutParameter(paramIndex, Types.NUMERIC);
				if (type.equals("date"))
					proc.registerOutParameter(paramIndex, Types.DATE);
				// 游标参数，置于参数列的最后，输出中用到
				if (type.equals("cursor"))
					proc.registerOutParameter(paramIndex, OracleTypes.CURSOR);
			}
		} catch (NumberFormatException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.print(e.getMessage());
		}
	}

	/**
	 * 检测存储过程参数数量是否一致
	 * 
	 * @return
	 */
	private boolean procParamAmountCheck() {
		int startIndex = 0;
		int count = 0;
		// 查找 ? 的数量
		while (startIndex != -1) {
			startIndex = this.procedureCallStr.indexOf("?", startIndex);
			// 超出字符串长度，退出
			if (startIndex == -1)
				break;
			++startIndex;
			++count;
		}
		// 判断 ? 的数量和存储过程执行对象参数的数量是否相等
		if (count == this.procParamValues.size())
			return true;
		return false;
	}

	/**
	 * 获取存储过程呼叫string（唯一确定一个存储过程，保存在接口模板的tableName中）
	 */
	public String getProcedureCallStr() {
		return procedureCallStr;
	}

	/**
	 * 增加存储过程参数值，参数值与存储过程参数是一一对应的
	 * 
	 * @param value
	 *            参数值
	 * @param type
	 *            参数类型
	 */
	public void addProcParamValue(String name, String value, String valueType,
			int direct) {
		procParamValues.add(new ProcParam(name, value, valueType, direct));
	}

	public void addProcParamValue(String value, String valueType, int direct) {
		procParamValues.add(new ProcParam(value, valueType, direct));
	}
}

/**
 * 表示存储过程参数值的对象
 * 
 * @author llj
 * 
 */
class ProcParam {
	private String paramName;
	private String paramValue;
	private String paramType;
	// typedef int direct;
	private int direct;

	/**
	 * 构造一个存储过程参数值
	 * 
	 * @param name
	 *            参数名
	 * @param value
	 *            参数值
	 * @param type
	 *            参数值的类型
	 * @param direct
	 *            参数值在存储过程中的输入输出方向 <tab>1 输入 <tab>-1 输出
	 */
	public ProcParam(String name, String value, String type, int direct) {
		this(value, type, direct);
		this.paramName = name;
	}

	public ProcParam(String value, String type, int direct) {
		this.paramValue = value;
		this.paramType = type;
		this.direct = direct;
	}

	/**
	 * 获取参数值
	 * 
	 * @return
	 */
	public String getParamValue() {
		return paramValue;
	}
	/**
	 * 获取参数的名称
	 * @return
	 */
	public String getParamName() {
		return paramName;
	}

	/**
	 * 设置参数的名称
	 * 
	 * @param paramName
	 */
	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	/**
	 * 设置存储过程参数的输入输出方向
	 * <p>
	 * 1 输入参数
	 * <p>
	 * -1 输出参数
	 * 
	 * @param direct
	 */
	public void setDirect(int direct) {
		this.direct = direct;
	}

	/**
	 * 设置参数值
	 * 
	 * @param paramValue
	 */
	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	/**
	 * 获取参数值的类型
	 * 
	 * @return
	 */
	public String getParamType() {
		return paramType;
	}

	/**
	 * 设置参数值的类型
	 * 
	 * @param paramType
	 */
	public void setParamType(String paramType) {
		this.paramType = paramType;
	}

	/**
	 * 获取参数中存储过程中的输入输出方向 <tab>1 输入 <tab>-1 输出
	 */
	public int getDirect() {
		return this.direct;
	}
}

/**
 * 执行存储过程失败时抛出的异常对象
 * 
 * @author llj
 * 
 */
class ExcuteProcedureException extends SQLException {
	/**
	 * 派生SQLException类jdk建议增加的成员，暂无意义
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 构造存储过程异常对象
	 * 
	 * @param reason
	 *            出错原因
	 */
	public ExcuteProcedureException(String reason) {
		super(reason);
	}
}
