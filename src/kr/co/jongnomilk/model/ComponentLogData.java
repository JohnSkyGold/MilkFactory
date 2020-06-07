package kr.co.jongnomilk.model;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class ComponentLogData {
	String serialNumber = "MS01";
	int pinNumber;
	int state;
	SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	String timeLog;
	
	public ComponentLogData(int pinNumber, int state, Date date) {
		
		this.pinNumber = pinNumber;
		this.state = state;
		this.timeLog = timeFormat.format(date);
	}
	public ComponentLogData() {
		
	}
	
	public void setLogData(int state) {
		this.state = state;
		this.timeLog = timeFormat.format(new Date());
	}
	
	public void setLogData(int pinNumber, int state) {
		this.pinNumber = pinNumber;
		this.state = state;
		this.timeLog = timeFormat.format(new Date());
	}
	
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public int getPinNumber() {
		return pinNumber;
	}
	public void setPinNumber(int pinNumber) {
		this.pinNumber = pinNumber;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getTimeLog() {
		return timeLog;
	}
	public void setTimeLog(Date date) {
		this.timeLog = timeFormat.format(date);
	}
	@Override
	public String toString() {
		return "ComponentLogData [serialNumber=" + serialNumber + ", pinNumber=" + pinNumber + ", state=" + state
				+ ", timeLog=" + timeLog + "]";
	}
	
	
	
}
