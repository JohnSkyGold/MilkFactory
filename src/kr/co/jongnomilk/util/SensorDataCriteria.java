package kr.co.jongnomilk.util;

public class SensorDataCriteria {
	private static final int weightMinimum = 0;
	private static final int weightMaximum = 8;
	private static final int waterLevelMinimum = 0;
	private static final int waterLevelMaximum = 8;
	private static final int flameMinimum = 0;
	private static final int flameMaximum = 1025 ;
	private static final int temperatureMinimum = 0;
	private static final int temperatureMaximum = 8;
	private static final int weightWarningTime = 0;

	
	public static int getWeightminimum() {
		return weightMinimum;
	}
	public static int getWeightmaximum() {
		return weightMaximum;
	}
	public static int getWaterlevelminimum() {
		return waterLevelMinimum;
	}
	public static int getWaterlevelmaximum() {
		return waterLevelMaximum;
	}
	
	public static int getTemperatureminimum() {
		return temperatureMinimum;
	}
	public static int getTemperaturemaximum() {
		return temperatureMaximum;
	}
	public static int getWeightwarningtime() {
		return weightWarningTime;
	}
	
	public static int getFlameminimum() {
		return flameMinimum;
	}
	public static int getFlamemaximum() {
		return flameMaximum;
	}

	
	
}
