package kr.co.jongnomilk.util;

import java.sql.Connection;
import java.sql.DriverManager;


public class JDBCUtil {
	public static Connection getConnection(String dirverClassName, String url, String id, String passwd){
		Connection conn = null;
		try{
			Class.forName(dirverClassName);
			conn = DriverManager.getConnection(url, id, passwd);
		}catch(Exception ex){
			System.out.println(ex);
			throw new RuntimeException("db 접속시 오류 발생 : " + ex);
		}
		return conn;
	}
	
	public static Connection getConnection(String url, String id, String passwd){
		return getConnection("", url, id, passwd);
	}
	
	//Company 2
	public static Connection getConnection(){
		return getConnection("oracle.jdbc.driver.OracleDriver", "jdbc:oracle:thin:@127.0.0.1:1521:xe", "test1", "1234");
	}
	
//	//Court
//	public static Connection getConnection(){
//		return getConnection("oracle.jdbc.driver.OracleDriver", "jdbc:oracle:thin:@192.168.128.11:1521:orcl", "stc00", "stc00");
//	}
	
	public static void main(String args[]){
		Connection conn = JDBCUtil.getConnection();
		if(conn != null)
			System.out.println("ok");
		else
			System.out.println("error");
	}

}
