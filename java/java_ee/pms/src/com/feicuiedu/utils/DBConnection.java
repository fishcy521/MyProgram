package com.feicuiedu.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Administrator on 2017/4/27.
 */
public class DBConnection {
	private static DBConnection dbc;
	private Connection con;
	private final static String DRIVER = "driver";
	private final static String URL = "url";
	private final static String USER_NAME = "username";
	private final static String PASSWORD = "password";
	private DBConnection() {
		try {
			Class.forName(Config.getValue(DRIVER)).newInstance();
			con = DriverManager.getConnection(Config.getValue(URL), Config.getValue(USER_NAME), Config.getValue(PASSWORD));
		}
		catch (InstantiationException e) {
			e.printStackTrace();
		}
		catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static DBConnection getInstance() {
		
		if (dbc == null) {
			dbc = new DBConnection();
		}
		return dbc;
	}
	
	public Connection getCon() {
		return con;
	}
	
	public void setCon(Connection con) {
		this.con = con;
	}
	
	public static void main(String[] args) {
		/*Connection c = DBConnection.getInstance().getCon();
		
		if (c == null) {
			System.out.println("哈哈");
		}
		else {
			System.out.println("hehe");
		}*/
		
		
	}
}
