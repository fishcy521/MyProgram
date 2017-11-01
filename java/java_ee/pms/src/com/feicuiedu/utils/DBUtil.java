package com.feicuiedu.utils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/27.
 */
public class DBUtil {
	private static PreparedStatement preparedStatement;
	
	/**
	 * 用于查询的方法
	 *
	 * @param sql  sql语句字符串
	 * @param args sql语句的参数
	 * @return
	 */
	public static List<Map<String, Object>> queryMethod(String sql, Object[] args) {
		
		List<Map<String, Object>> result = new ArrayList<>();
		
		try {
			//preparedStatement = DBConnection.getInstance().getCon().prepareStatement(sql);
			preparedStatement = C3P0Connection.getConnection().prepareStatement(sql);
			if (args != null && args.length > 0) {
				for (int i = 0; i < args.length; i++) {
					preparedStatement.setObject(i + 1, args[i]);
				}
			}
			
			ResultSet resultSet = preparedStatement.executeQuery();
			ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
			
			// 获取列总数
			int columnCount = resultSetMetaData.getColumnCount();
			
			
			while (resultSet.next()) {
				Map<String, Object> columnMap = new HashMap<>();
				for (int i = 0; i < columnCount; i++) {
					
					// 获取列名
					String columnName = resultSetMetaData.getColumnName(i + 1);
					Object columnValue = resultSet.getObject(columnName);
					
					columnMap.put(columnName.toLowerCase(), columnValue);
				}
				
				result.add(columnMap);
			}
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * 用户新增，修改，删除的方法
	 *
	 * @param sql  sql语句字符串
	 * @param args sql语句参数
	 */
	public static void updateMethod(String sql, String[] args) throws SQLException {
		
		preparedStatement = C3P0Connection.getConnection().prepareStatement(sql);
		
		if (args != null && args.length > 0) {
			for (int i = 0; i < args.length; i++) {
				preparedStatement.setObject(i + 1, args[i]);
			}
		}
		
		preparedStatement.executeUpdate();
		
		
	}
}
