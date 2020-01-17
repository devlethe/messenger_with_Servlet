package com.test.messenger.database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBManager {

	public static Connection getConnection() throws SQLException {
		String url = "jdbc:oracle:thin:@localhost:1521:orcl";
		String user = "root";
		String password = "user";

		Connection conn = DriverManager.getConnection(url, user, password);
		System.out.println("DB Connect; conn=" + conn);
		return conn;
	}

	public static void dbClose(ResultSet rs, PreparedStatement ps, Connection conn) throws SQLException {
		Close(rs, ps, null, null, conn);
	}

	public static void dbClose(PreparedStatement ps, Connection conn) throws SQLException {
		Close(null, ps, null, null, conn);
	}

	public static void dbClose(ResultSet rs, Statement stmt, Connection conn) throws SQLException {
		Close(rs, null, stmt, null, conn);
	}

	public static void dbClose(Statement stmt, Connection conn) throws SQLException {
		Close(null, null, stmt, null, conn);
	}

	public static void dbClose(ResultSet rs, CallableStatement cs, Connection conn) throws SQLException {
		Close(rs, null, null, cs, conn);
	}

	public static void dbClose(CallableStatement cs, Connection conn) throws SQLException {
		Close(null, null, null, cs, conn);
	}

	private static void Close(ResultSet rs, PreparedStatement ps, Statement stmt, CallableStatement cs, Connection conn)
			throws SQLException {

		if (rs != null)
			rs.close();
		System.out.println("ResultSet Close");

		if (ps != null)
			ps.close();
		System.out.println("PreparedStatement Close");

		if (stmt != null)
			stmt.close();
		System.out.println("Statement Close");

		if (cs != null)
			cs.close();
		System.out.println("CallableStatement Close");

		if (conn != null)
			conn.close();
		System.out.println("DB Close");
	}

	/*
	 * private static void Close(Connection conn) throws SQLException { if (conn !=
	 * null) conn.close(); System.out.println("DB Close"); }
	 * 
	 * private static void Close(Statement stmt) throws SQLException { if (stmt !=
	 * null) stmt.close(); System.out.println("Statement Close"); }
	 * 
	 * private static void Close(PreparedStatement ps) throws SQLException { if (ps
	 * != null) ps.close(); System.out.println("PreparedStatement Close"); }
	 * 
	 * private static void Close(CallableStatement cs) throws SQLException { if (cs
	 * != null) cs.close(); System.out.println("CallableStatement Close"); }
	 * 
	 * private static void Close(ResultSet rs) throws SQLException { if (rs != null)
	 * rs.close(); System.out.println("ResultSet Close"); }
	 */
}
