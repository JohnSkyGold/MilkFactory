package kr.co.jongnomilk.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SensorData {
	protected String serialNumber;
	protected int weight;
	protected int waterLevel;
	protected int temperature;
	protected String flame;
	protected SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	protected String timeLog;
	
	public SensorData() {
		
	}
	
	public SensorData(String flame, int waterLevel, int weight, int temperature) {
		this.flame = flame;
		this.waterLevel = waterLevel;
		this.weight = weight;
		this.temperature = temperature;
	}
	
	public String getFlame() {
		return flame;
	}
	public void setFlame(String flame) {
		this.flame = flame;
	}
	//int timeLog;  //simpledateformat or gregorian
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public int getWaterLevel() {
		return waterLevel;
	}
	public void setWaterLevel(int waterLevel) {
		this.waterLevel = waterLevel;
	}
	public int getTemperature() {
		return temperature;
	}
	public void setTemperature(int temperature) {
		this.temperature = temperature;
	}
	public String getTimeLog() {
		return timeLog;
	}
	public void setTimeLog(Date date) {
		this.timeLog = timeFormat.format(date);
	}
	
	
	
	
	

}
