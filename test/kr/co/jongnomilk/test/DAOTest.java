package kr.co.jongnomilk.test;

import kr.co.jongnomilk.dao.ComponentLogDAO;
import kr.co.jongnomilk.dao.SensorDAO;
import kr.co.jongnomilk.model.ComponentLogData;
import kr.co.jongnomilk.model.SensorData;

public class DAOTest {

	public DAOTest() {
//		SensorData sensorData = new SensorData();
//		sensorData.setFlame("1024");
//		sensorData.setWaterLevel(23);
//		sensorData.setWeight(23);
//		sensorData.setTemperature(23);
//		SensorDAO sensorDAO = SensorDAO.getSensorDAO();
//		
//		int rc = sensorDAO.insertSensorData(sensorData);
//		System.out.println(rc);
		
		ComponentLogDAO logDAO = ComponentLogDAO.getComponentLogDAO();
		ComponentLogData heaterLog = logDAO.getNewestComponentLog(3);
		
		System.out.println(heaterLog);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new DAOTest();
	}

}
