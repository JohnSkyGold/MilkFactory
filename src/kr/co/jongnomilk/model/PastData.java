package kr.co.jongnomilk.model;

public class PastData { 
	private int weightCount = 0;
	private int waterLevelCount = 0;
	private int temperatureCount = 0;
	private int flameCount = 0;
	private int pastWeight;
	private int pastWaterLevel;
	private int pastTemperature;
	private String pastFlame;
	
	private int pastEntranceState;
	private int pastOutletState;
	private int pastHeaterState;
	private int pastGreenLedState;
	private int pastRedLedState;
	private int pastYellowLedState;
	private int pastBuzzerState;
	
	public int getWeightCount() {
		return weightCount;
	}
	public void setWeightCount(int weightCount) {
		this.weightCount = weightCount;
	}
	public int getWaterLevelCount() {
		return waterLevelCount;
	}
	public void setWaterLevelCount(int waterLevelCount) {
		this.waterLevelCount = waterLevelCount;
	}
	public int getTemperatureCount() {
		return temperatureCount;
	}
	public void setTemperatureCount(int temperatureCount) {
		this.temperatureCount = temperatureCount;
	}
	public int getFlameCount() {
		return flameCount;
	}
	public void setFlameCount(int flameCount) {
		this.flameCount = flameCount;
	}
	public int getPastWeight() {
		return pastWeight;
	}
	public void setPastWeight(int pastWeight) {
		this.pastWeight = pastWeight;
	}
	public int getPastWaterLevel() {
		return pastWaterLevel;
	}
	public void setPastWaterLevel(int pastWaterLevel) {
		this.pastWaterLevel = pastWaterLevel;
	}
	public int getPastTemperature() {
		return pastTemperature;
	}
	public void setPastTemperature(int pastTemperature) {
		this.pastTemperature = pastTemperature;
	}
	public String getPastFlame() {
		return pastFlame;
	}
	public void setPastFlame(String pastFlame) {
		this.pastFlame = pastFlame;
	}
	public int getPastEntranceState() {
		return pastEntranceState;
	}
	public void setPastEntranceState(int pastEntranceState) {
		this.pastEntranceState = pastEntranceState;
	}
	public int getPastOutletState() {
		return pastOutletState;
	}
	public void setPastOutletState(int pastOutletState) {
		this.pastOutletState = pastOutletState;
	}
	public int getPastHeaterState() {
		return pastHeaterState;
	}
	public void setPastHeaterState(int pastHeaterState) {
		this.pastHeaterState = pastHeaterState;
	}
	public int getPastGreenLedState() {
		return pastGreenLedState;
	}
	public void setPastGreenLedState(int pastGreenLedState) {
		this.pastGreenLedState = pastGreenLedState;
	}
	public int getPastRedLedState() {
		return pastRedLedState;
	}
	public void setPastRedLedState(int pastRedLedState) {
		this.pastRedLedState = pastRedLedState;
	}
	public int getPastYellowLedState() {
		return pastYellowLedState;
	}
	public void setPastYellowLedState(int pastYellowLedState) {
		this.pastYellowLedState = pastYellowLedState;
	}
	public int getPastBuzzerState() {
		return pastBuzzerState;
	}
	public void setPastBuzzerState(int pastBuzzerState) {
		this.pastBuzzerState = pastBuzzerState;
	}
	
	public void setCountsToZero() {
		this.waterLevelCount = 0;
		this.weightCount = 0;
		this.temperatureCount = 0;
	}
	
	 
	 
	

	
	
	
	
}
