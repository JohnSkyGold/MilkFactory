package kr.co.jongnomilk.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import kr.co.jongnomilk.util.JDBCUtil;


public abstract class DAO {
	public Connection connect(){
		Connection conn = null;
		try{
			conn = JDBCUtil.getConnection();
		}catch(RuntimeException ex){
			throw ex;
		}
		return conn;
	}

	public void close(Connection conn, PreparedStatement ps, ResultSet rs){
		if(rs != null){
			try{ rs.close(); } catch(Exception ex){}
		}		
		close(conn, ps);
	} // close
	
	public void close(Connection conn, PreparedStatement ps){
		if(ps != null){
			try{ ps.close(); } catch(Exception ex){}
		}
		
		if(conn != null){
			try{ conn.close(); } catch(Exception ex){}
		}
	} 
}
