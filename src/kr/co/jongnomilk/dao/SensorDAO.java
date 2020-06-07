package kr.co.jongnomilk.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import kr.co.jongnomilk.model.SensorData;


public class SensorDAO extends DAO{
	private static SensorDAO sensorDAO = new SensorDAO();
	
	public static SensorDAO getSensorDAO() {
		return sensorDAO;
	}
	
	
	public int insertSensorData(SensorData sensorData) {
		Connection conn = null;
		PreparedStatement ps = null;
		int resultCnt = 0;

		try {
			conn = connect();
			ps = conn.prepareStatement("insert into statement(fa_serialnumber, flame, waterlevel, weight, temperature, time_log) "
					+ "values('MS01', ?, ?, ?, ?, sysdate) ");
			ps.setString(1, sensorData.getFlame());
			ps.setInt(2, sensorData.getWaterLevel());
			ps.setInt(3, sensorData.getWeight());
			ps.setInt(4, sensorData.getTemperature());
			
			resultCnt = ps.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(conn, ps);
		}
		return resultCnt;
	}
}
