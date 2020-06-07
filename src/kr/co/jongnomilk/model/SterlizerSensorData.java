package kr.co.jongnomilk.model;

public class SterlizerSensorData extends SensorData {
	public SterlizerSensorData() {
		
	}
	
	public SterlizerSensorData(String flame, int waterLevel, int weight, int temperature) {
		this.flame = flame;
		this.waterLevel = waterLevel;
		this.weight = weight;
		this.temperature = temperature;
	}
}
