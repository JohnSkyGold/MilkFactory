package kr.co.jongnomilk.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import kr.co.jongnomilk.model.EmployeeData;
import kr.co.jongnomilk.util.JDBCUtil;


public class EmployeeDAO extends DAO{
	private static EmployeeDAO dao = new EmployeeDAO();
	public static EmployeeDAO getInstance(){
		return dao;
	}
	
	public EmployeeData getEmployee(String empId, int pwd){
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			conn = connect();
			
			StringBuffer query = new StringBuffer();
			query.append("SELECT employee_id, pw, age, sex, position, department, name, authority ");
			query.append("FROM employee ");
			query.append("WHERE employee_id = ? ");
			query.append("AND   pw = ? ");
						
			ps = conn.prepareStatement( query.toString() );
			ps.setString(1, empId);
			ps.setInt(2, pwd);
			rs = ps.executeQuery();
			while(rs.next()){
				EmployeeData vo = new EmployeeData();
			    vo.setEmployeeID(rs.getString(1));
			    vo.setPassword(rs.getInt(2));
			    vo.setAge(rs.getInt(3));
			    vo.setSex(rs.getString(4));
			    vo.setPosition(rs.getString(5));
			    vo.setDeparture(rs.getString(6));
			    vo.setName(rs.getString(7));
			    vo.setAuthority(rs.getString(8));
				return vo;
			}
		}catch(Exception ex){
			System.out.println("getCustomer exception : " + ex);
			close(conn, ps, rs);
		}
		
		return null;
	}
}
