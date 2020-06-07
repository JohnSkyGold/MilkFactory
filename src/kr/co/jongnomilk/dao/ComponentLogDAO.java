package kr.co.jongnomilk.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


import kr.co.jongnomilk.model.ComponentLogData;
import kr.co.jongnomilk.model.SensorData;

public class ComponentLogDAO extends DAO {
	private static ComponentLogDAO componentLogDAO = new ComponentLogDAO();
	
	public static ComponentLogDAO getComponentLogDAO() {
		return componentLogDAO;
	}
	
	public ComponentLogData getNewestComponentLog(int pinNumber){
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try{
			conn = connect();
			
			StringBuffer query = new StringBuffer();
			query.append("select rownum, newlog.co_state, newlog.time_log, newlog.fa_serialnumber, newlog.co_pinno  ");
			query.append("from(select co_state, time_log, fa_serialnumber, co_pinno from component_log where co_pinno = ? order by time_log desc) ");
			query.append("newlog where rownum=1 ");	
			ps = conn.prepareStatement( query.toString() ); 
			ps.setInt(1, pinNumber);
			rs = ps.executeQuery();
			if(rs.next()){
				ComponentLogData logData = new ComponentLogData();
				logData.setState(rs.getInt(2));
				logData.setTimeLog(rs.getDate(3));
				logData.setSerialNumber(rs.getString(4));
			    logData.setPinNumber(rs.getInt(5));
				return logData;
			}
		}catch(Exception ex){
			System.out.println("getNewestComponentLog exception : " + ex);
			close(conn, ps, rs);
		}
		
		return null;			
	}
	
	
	public int insertComponentLog(ComponentLogData componentLogData) {
		Connection conn = null;
		PreparedStatement ps = null;
		int resultCnt = 0;

		try {
			conn = connect();
			ps = conn.prepareStatement("insert into component_log(co_state, time_log, fa_serialnumber, co_pinno) "
					+ "values(?, sysdate, ?, ?)");
			ps.setInt(1, componentLogData.getState());
			ps.setString(2, componentLogData.getSerialNumber());
			ps.setInt(3, componentLogData.getPinNumber());
			
			resultCnt = ps.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(conn, ps);
		}
		return resultCnt;
	}
}
