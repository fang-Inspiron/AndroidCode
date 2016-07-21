package com.jdbc.dbutils;

import java.lang.reflect.Field;
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

import com.jdbc.dbutils.domain.UserInfo;

public class JdbcUtils {

	// ��ʾ�������ݿ���û���
	private final String USERNAME = "root";
	// �������ݿ������
	private final String PASSWORD = "root";
	// �������ݿ��������Ϣ
	private final String DRIVER = "com.mysql.jdbc.Driver";
	// �������ݿ�ĵ�ַ
	private final String URL = "jdbc:mysql://localhost:3306/mydb";
	// �������ݿ������
	private Connection connection;
	// ����SQL���ִ�ж���
	private PreparedStatement pstmt;
	// �����ѯ���صĽ������
	private ResultSet resultSet;

	public JdbcUtils() {
		// TODO Auto-generated constructor stub
		try {
			//������������
			Class.forName(DRIVER);
			System.out.println("ע�������ɹ�������");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	// ���������ݿ������
	public Connection getConnection() {
		try {
			connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			System.out.println("���ݿ����ӳɹ�");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("���ݿ�����ʧ��");
		}
		return connection;
	}

	// ��ɶ����ݿ�ı����ӡ�ɾ�����޸ĵĲ���
	public boolean updateBypreparedStatement(String sql, List<Object> params)
			throws SQLException {
		boolean flag = false;
		int result = -1;// ���û�ִ����ӡ�ɾ�����޸Ĳ�����ʱ����Ӱ�����ݿ������
		pstmt = connection.prepareStatement(sql);
		int index = 1;
		if (params != null && !params.isEmpty()) {
			for (int i = 0; i < params.size(); i++) {
				pstmt.setObject(index++, params.get(i));
			}
		}
		result = pstmt.executeUpdate();
		flag = result > 0 ? true : false;

		return flag;
	}

	/*
	 * ��ѯ���ص�����¼
	 */
	public Map<String, Object> findSimpleResult(String sql, List<Object> params)
			throws SQLException {
		Map<String, Object> map = new HashMap<String, Object>();
		// ���ռλ��
		int index = 1;
		pstmt = connection.prepareStatement(sql);
		if (params != null && !params.isEmpty()) {
			for (int i = 0; i < params.size(); i++) {
				pstmt.setObject(index++, params.get(i));
			}
		}
		// ִ�в�ѯ��������صĽ��
		resultSet = pstmt.executeQuery();// ���ز�ѯ���
		ResultSetMetaData metaData = resultSet.getMetaData();
		int col_len = metaData.getColumnCount();// ����е�����
		while (resultSet.next()) {
			for (int i = 0; i < col_len; i++) {
				String cols_name = metaData.getColumnName(i + 1);
				Object cols_value = resultSet.getObject(cols_name);
				if (cols_value == null) {
					cols_value = "";
				}
				map.put(cols_name, cols_value);
			}
		}

		return map;
	}

	/*
	 * ��ѯ���ض�����¼
	 */
	public List<Map<String, Object>> findMoreResult(String sql,
			List<Object> params) throws SQLException {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		// ���ռλ��
		int index = 1;
		pstmt = connection.prepareStatement(sql);
		if (params != null && !params.isEmpty()) {
			for (int i = 0; i < params.size(); i++) {
				pstmt.setObject(index++, params.get(i));
			}
		}
		// ִ�в�ѯ��������صĽ��
		resultSet = pstmt.executeQuery();
		ResultSetMetaData metaData = resultSet.getMetaData();
		int cols_len = metaData.getColumnCount();
		while (resultSet.next()) {
			Map<String, Object> map = new HashMap<String, Object>();
			for (int i = 0; i < cols_len; i++) {
				String cols_name = metaData.getColumnName(i + 1);
				Object cols_value = resultSet.getObject(cols_name);
				if (cols_value == null) {
					cols_name = "";
				}
				map.put(cols_name, cols_value);
			}
			list.add(map);
		}

		return list;
	}

	// jdbc�ķ�װ�����÷��������ʵ��
	public <T> T findSimpleRefResult(String sql, List<Object> params,
			Class<T> cls) throws Exception {
		T resultObject = null;
		pstmt = connection.prepareStatement(sql);
		// ���ռλ��
		int index = 1;
		pstmt = connection.prepareStatement(sql);
		if (params != null && !params.isEmpty()) {
			for (int i = 0; i < params.size(); i++) {
				pstmt.setObject(index++, params.get(i));
			}
		}
		resultSet = pstmt.executeQuery();
		ResultSetMetaData metaData = resultSet.getMetaData();
		int cols_len = metaData.getColumnCount();
		while (resultSet.next()) {
			// ͨ��������ƴ���ʵ��
			resultObject = cls.newInstance();
			for (int i = 0; i < cols_len; i++) {
				String clos_name = metaData.getColumnName(i + 1);
				Object clos_value = resultSet.getObject(clos_name);
				if (clos_value == null) {
					clos_value = "";
				}
				Field field = cls.getDeclaredField(clos_name);
				field.setAccessible(true);// ��javabean�ķ���privateȨ��
				field.set(resultObject, clos_value);
			}
		}
		return resultObject;
	}

	// ͨ��������Ʒ������ݿ�
	public <T> List<T> findMoreRefResult(String sql, List<Object> params,
			Class<T> cls) throws Exception {
		List<T> list = new ArrayList<T>();
		int index = 1;
		pstmt = connection.prepareStatement(sql);
		if (params != null && !params.isEmpty()) {
			for (int i = 0; i < params.size(); i++) {
				pstmt.setObject(index++, params.get(i));
			}
		}
		resultSet = pstmt.executeQuery();
		ResultSetMetaData metaData = resultSet.getMetaData();
		int cols_len = metaData.getColumnCount();
		while (resultSet.next()) {
			T resultObject = cls.newInstance();
			for (int i = 0; i < cols_len; i++) {
				String clos_name = metaData.getColumnName(i + 1);
				Object clos_value = resultSet.getObject(clos_name);
				if (clos_value == null) {
					clos_value = "";
				}
				Field field = cls.getDeclaredField(clos_name);
				field.setAccessible(true);
				field.set(resultObject, clos_value);
			}
			list.add(resultObject);
		}

		return list;
	}

	public void releaseConn() {
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();

		/*
		 * �����ݿ������
		 */
		// String sql = "insert into userinfo(username, pswd) values(?, ?)";
		// List<Object> params = new ArrayList<Object>();
		// params.add("rose");
		// params.add("123456");
		// try {
		// boolean flag = jdbcUtils.updateBypreparedStatement(sql, params);
		// System.out.println(flag);
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }
		
		/*
		 *  ��ѯ������Ϣ
		 */
//		String sql = "select * from userinfo where id = ? ";
//		List<Object> params = new ArrayList<Object>();
//		params.add(1);
//		try {
//			Map<String, Object> map = jdbcUtils.findSimpleResult(sql, params);
//			System.out.println(map);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		/*
		 * ��ѯ������Ϣ
		 */
//		String sql = "select * from userinfo ";
//		try {
//			List<Map<String, Object>> list = jdbcUtils.findMoreResult(sql, null);
//			System.out.println(list);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} finally {
//			jdbcUtils.releaseConn();
//		}

		/*
		 * ���Է������ ������¼
		 */
//		String sql = "select * from userinfo where id = ? ";
//		List<Object> params = new ArrayList<Object>();
//		params.add(1);
//		try {
//			UserInfo userInfo = jdbcUtils.findSimpleRefResult(sql, params, UserInfo.class);
//			System.out.println(userInfo);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} finally {
//			jdbcUtils.releaseConn();
//		}
		
		/*
		 * ���Է������ ������¼
		 */
		String sql = "select * from userinfo ";
		try {
			List<UserInfo> list = jdbcUtils.findMoreRefResult(sql, null, UserInfo.class);
			System.out.println(list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConn();
		}
		
	}

}
