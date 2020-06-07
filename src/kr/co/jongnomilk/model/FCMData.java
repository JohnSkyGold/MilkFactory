package kr.co.jongnomilk.model;

public class FCMData {
	Data data;
	String to;
	String collapse_key = "type_a";
	public Data getData() {
		return data;
	}
	public void setData(Data data) {
		this.data = data;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	
}
