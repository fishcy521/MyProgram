package com.feicuiedu.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/24.
 */
public class DBUtil {
	
	private static Connection con;
	
	private DBUtil(){
		
	}
	
	private static Connection getCon() {
		
		if (con == null) {
			try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				String url="jdbc:mysql://192.168.1.110:3306/chenyan";
				con = DriverManager.getConnection(url, "root", "feicui@mysql1234");
				
				
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return con;
	}
	public static List<Map<String,Object>> query(String sql,String[] args) {
		
		List<Map<String,Object>> lstResult = new ArrayList<>();
		con = getCon();
		
		try {
			PreparedStatement preparedStatement = con.prepareStatement(sql);
			if (args != null && args.length>0) {
				for (int i = 0;i<args.length;i++) {
					preparedStatement.setString(i+1,args[i]);
				}
			}
			
			ResultSet rs = preparedStatement.executeQuery();
			
			ResultSetMetaData rsd = rs.getMetaData();
			
			int columnCount = rsd.getColumnCount();
			while(rs.next()) {
				
				Map<String,Object> tmpMap = new HashMap<>();
				for (int i = 0; i < columnCount; i++) {
					String columnName = rsd.getColumnName(i+1);
					Object  value = rs.getObject(columnName);
					
					tmpMap.put(columnName,value);
				}
				
				lstResult.add(tmpMap);
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return lstResult;
	}
}
