package kr.co.jongnomilk.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FacilityItemData {
	String serialNumber;
	String categoryCode;
	String itemName;
	SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	String purchasingDate;
	int location;
	String maintenanceDate;
	String ip;
	
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getCategoryCode() {
		return categoryCode;
	}
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getPurchasingDate() {
		return purchasingDate;
	}
	public void setPurchasingDate(Date date) {
		this.purchasingDate = timeFormat.format(date);
	}
	public int getLocation() {
		return location;
	}
	public void setLocation(int location) {
		this.location = location;
	}
	public String getMaintenanceDate() {
		return maintenanceDate;
	}
	public void setMaintenanceDate(Date date) {
		this.maintenanceDate = timeFormat.format(date);
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	@Override
	public String toString() {
		return "FacilityItemData [serialNumber=" + serialNumber + ", categoryCode=" + categoryCode + ", itemName="
				+ itemName + ", purchasingDate=" + purchasingDate + ", location=" + location + ", maintenanceDate="
				+ maintenanceDate + ", ip=" + ip + "]";
	}
	
	
}
