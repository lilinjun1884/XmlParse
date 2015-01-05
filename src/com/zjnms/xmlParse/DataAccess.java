package com.zjnms.xmlParse;

import java.sql.*;
import java.util.ResourceBundle;

/**
 * 数据库访问对象
 * 
 * @author llj
 */
public class DataAccess {
	// 数据库驱动
	private String dbdriver;
	// 服务器地址
	private String url;
	// 用户名
	private String user;
	// 密码
	private String pwd;
	// 数据库连接对象
	private Connection _conn;

	public DataAccess() {
		// 配置连接信息
		cfgConn();
	}

	private void cfgConn() {
		// 获取资源对象
		ResourceBundle resource = ResourceBundle
				.getBundle("com.zjnms.xmlParse.DBConfig");
		dbdriver = resource.getString("dbdriver");
		url = resource.getString("url");
		user = resource.getString("user");
		pwd = resource.getString("pwd");
	}

	/**
	 * 设置数据库驱动字符串
	 * 
	 * @param DBDriver
	 */
	// public void setDbdriver(String DBDriver)
	// {
	// this.dbdriver = DBDriver;
	// }
	/**
	 * 获取数据库驱动字符串
	 * 
	 * @return
	 */
	public String getDbdriver() {
		return dbdriver;
	}

	/**
	 * 设置服务器地址字符串
	 * 
	 * @return
	 */
	// public void setUrl(String url)
	// {
	// this.url = url;
	// }
	/**
	 * 获取服务器地址字符串
	 * 
	 * @return
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * 设置用户名
	 * 
	 * @param user
	 */
	// public void setUser(String user)
	// {
	// this.user = user;
	// }
	/**
	 * 获取用户名
	 * 
	 * @return
	 */
	public String getUser() {
		return user;
	}

	/**
	 * 设置密码
	 * 
	 * @param pwd
	 */
	// public void setPwd(String pwd)
	// {
	// this.pwd = pwd;
	// }
	/**
	 * 获取密码
	 * 
	 * @return
	 */
	public String getPwd() {
		return pwd;
	}

	/**
	 * 打开数据库连接
	 */
	public Connection OpenConn() {
		try {
			// 加载数据库驱动对象
			Class.forName(dbdriver);
			// 获取连接对象
			this._conn = DriverManager.getConnection(url, user, pwd);
			// System.out.println(_conn.toString());
			return _conn;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return null;
	}

	/**
	 * 获取连接对象
	 * 
	 * @return
	 */
	public Connection getConn() {
		try {
			if (this._conn == null || this._conn.isClosed())
				OpenConn();
			return this._conn;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 关闭数据库连接
	 */
	public void CloseConn() {
		try {
			_conn.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	/**
	 * 使用sql语句查询数据
	 * 
	 * @param sql
	 * @return 结果集
	 */
	public ResultSet Query(String sql) {
		try {
			Statement stmt = _conn.createStatement();
			return stmt.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return null;
	}

	public ResultSet execProcedure(CallableStatement proc) {
		return null;
	}

	/**
	 * 使用sql语句更新数据
	 * 
	 * @param sql
	 * @return 更新的数据条数
	 */
	public int Update(String sql) {
		try {
			// 
			Statement stmt = _conn
					.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_READ_ONLY);
			int cnt = stmt.executeUpdate(sql);
			stmt.close();
			return cnt;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return 0;
		
	}

	/*// 类的方法测试
	public static void main(String[] args) {
		DataAccess da = new DataAccess();
		System.out.println(da.getDbdriver() + da.getPwd() + da.getUrl()
				+ da.getUser()); 
		// dataAccess.OpenConn();

		// int id = //dataAccess.Insert(
		// "insert into Tbpro_Main(pronum,proname,Prodes,Nlevel,isdel,version)"+
		// //
		// "values('005','name','prode',1,0,'2.1')");

		int id = da.Insert("select * from TBPRO_MAIN");
		System.out.println(id);
	}*/

}
