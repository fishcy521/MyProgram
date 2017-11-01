package com.feicuiedu.utils;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Administrator on 2017/4/28.
 */
public class C3P0Connection {
	
	private static final ComboPooledDataSource dataSource = new ComboPooledDataSource();
	
	//获取连接
	public static Connection getConnection() throws SQLException {
		Connection con = null;
		con = dataSource.getConnection();
		return con;
	}
	
	//获取连接池
	public static DataSource getDataSource() {
		return dataSource;
	}
}
