package kr.co.jongnomilk.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import kr.co.jongnomilk.dao.ComponentLogDAO;
import kr.co.jongnomilk.model.ComponentLogData;
import kr.co.jongnomilk.model.Data;
import kr.co.jongnomilk.model.PastData;
import kr.co.jongnomilk.model.SensorData;
import kr.co.jongnomilk.model.SterlizerSensorData;

public class SensorCompareUtil {
//	controlCode= buzzer(7,8)| redLed(6) | yellow(5) | green(4) | heat(3) | outlet(2) | entran(1) 
	private static int count = 5;
	private static byte controlCode = 0b00000000;

	
	private static byte buzzerOff = (byte)(0b00 << 6);  //00-²¨Áü, 01-Á¤»ó, 10-¿ÀÀÛµ¿, 11-È­Àç
	private static byte buzzerNormal = (byte)(0b01 << 6);
	private static byte buzzerMalfunction = (byte)(0b10 << 6);
	private static byte buzzerFire = (byte)(0b11 << 6);
	private static byte redLedOn = (byte)(0b1 << 5);
	private static byte redLedOff = (byte)(0b0 << 5);
	private static byte yellowLedOn = (byte)(0b1 << 4);
	private static byte yellowLedOff = (byte)(0b0 << 4);
	private static byte greenLedOn = (byte)(0b1 << 3);
	private static byte greenLedOff = (byte)(0b0 << 3);
	private static byte heaterOn = (byte)(0b1 << 2);
	private static byte heaterOff = (byte)(0b0 << 2);
	private static byte outletOpen = (byte)(0b1 << 1);
	private static byte outletClose = (byte)(0b0 << 1);
	private static byte entranceOpen = 0b1;
	private static byte entranceClose = 0b0;
	
	private static Date date = new Date();
	private static ComponentLogDAO logDAO = ComponentLogDAO.getComponentLogDAO();
	private static ComponentLogData entranceLog = new ComponentLogData(1, 0, date);
	private static ComponentLogData outletLog = new ComponentLogData(2, 0, date);
	private static ComponentLogData heaterLog = new ComponentLogData(3, 0, date);
	private static ComponentLogData greenLedLog = new ComponentLogData(4, 0, date);
	private static ComponentLogData yellowLedLog = new ComponentLogData(5, 0, date);
	private static ComponentLogData redLedLog = new ComponentLogData(6, 0, date);
	private static ComponentLogData buzzerLog = new ComponentLogData(7, 0, date);

	
	public static byte getControlCode(SterlizerSensorData sensorData, PastData pastData) {
//		entranceLog = logDAO.getNewestComponentLog(1);
//		outletLog = logDAO.getNewestComponentLog(2);
//		heaterLog = logDAO.getNewestComponentLog(3);
//		
//		if(entranceLog == null) {
//			entranceLog = new ComponentLogData(1, 0, date);
//		}
//		if(outletLog == null) {
//			outletLog = new ComponentLogData(2, 0, date);
//		}
//		if(heaterLog == null) {
//			heaterLog = new ComponentLogData(3, 0, date);
//		}
		if(entranceLog.getState() == 1) {//phase2
			controlCode = (byte)(getCodeFromFlame(sensorData.getFlame(), pastData)
					| getCodeAfterEntranceOpened1(sensorData.getWaterLevel(), pastData)
					| getCodeAfterEntranceOpened2(sensorData.getWeight(), pastData)
					| getCodeWhenHeaterOff(sensorData.getTemperature(), pastData));
		}else if(outletLog.getState() == 1) {//phase6
			controlCode = (byte)(getCodeFromFlame(sensorData.getFlame(), pastData)
					| getCodeAfterOutletOpened1(sensorData.getWaterLevel(), pastData)
					| getCodeAfterOutletOpened2(sensorData.getWeight(), pastData)
					| getCodeWhenHeaterOff(sensorData.getTemperature(), pastData));
		}else if(heaterLog.getState() == 1) {//phase4
			controlCode = (byte)(getCodeFromFlame(sensorData.getFlame(), pastData)
					| getCodeAfterEntranceClosed1(sensorData.getWaterLevel(), pastData)
					| getCodeAfterEntranceClosed2(sensorData.getWeight(), pastData)
					| getCodeWhenHeaterOn(sensorData.getTemperature(), pastData));
		}else {
			if(findRecentOffPin() == entranceLog.getPinNumber()) { //phase3
				controlCode = (byte)(getCodeFromFlame(sensorData.getFlame(), pastData)
						| getCodeAfterEntranceClosed1(sensorData.getWaterLevel(), pastData)
						| getCodeAfterEntranceClosed2(sensorData.getWeight(), pastData)
						| getCodeWhenHeaterOff(sensorData.getTemperature(), pastData));
			}else if(findRecentOffPin() == outletLog.getPinNumber()) {//phase1
				controlCode = (byte)(getCodeFromFlame(sensorData.getFlame(), pastData)
						| getCodeAfterOutletClosed1(sensorData.getWaterLevel(), pastData)
						| getCodeAfterOutletClosed2(sensorData.getWeight(), pastData)
						| getCodeWhenHeaterOff(sensorData.getTemperature(), pastData));
			}else if(findRecentOffPin() == heaterLog.getPinNumber()) {//phase5
				controlCode = (byte)(getCodeFromFlame(sensorData.getFlame(), pastData)
						| getCodeAfterEntranceClosed1(sensorData.getWaterLevel(), pastData)
						| getCodeAfterEntranceClosed2(sensorData.getWeight(), pastData)
						| getCodeWhenHeaterOff(sensorData.getTemperature(), pastData));
			}
			
		}
		parseCodeAndInsert(controlCode, pastData);
		return controlCode;
	}
	
	
	private static byte getCodeFromFlame(String flame, PastData pastData) {
		byte code = 0; 
		if("T".equals(flame)) {
			if(pastData.getFlameCount() == 0) {
				pastData.setFlameCount(pastData.getFlameCount() + 1);
			}else if(pastData.getFlameCount() < count) {
				pastData.setFlameCount(pastData.getFlameCount() + 1);
			}else if(pastData.getFlameCount() == count) { 
//				countUtil.setFlameCount(0);
				code = (byte)(buzzerFire | redLedOn);
			}
		}else {
			pastData.setFlameCount(0);
		}
		return code;
	}
	
	
	
	private static byte getCodeAfterEntranceOpened1(int waterLevel, PastData pastData) {
		byte code = 0; 
		if(waterLevel < SensorDataCriteria.getWaterlevelmaximum()) {
			if(pastData.getWaterLevelCount() == 0) {
				pastData.setWaterLevelCount(pastData.getWaterLevelCount() + 1);
				pastData.setPastWaterLevel(waterLevel);
			}else if(pastData.getWaterLevelCount() <= count) {
				pastData.setWaterLevelCount(pastData.getWaterLevelCount() + 1);
			}else if(pastData.getWaterLevelCount() > count) { 
				System.out.println(pastData.getWaterLevelCount());
				pastData.setWaterLevelCount(0);
				if(waterLevel <= pastData.getPastWaterLevel()) {
					code = (byte)(buzzerMalfunction | yellowLedOn | entranceOpen);	
//					
//					entranceLog.setLogData(1, 1);
					
					
				}
			}
		}else if(waterLevel >= SensorDataCriteria.getWaterlevelmaximum()){
			pastData.setWaterLevelCount(0);
			pastData.setPastWaterLevel(waterLevel);
			code = (byte)(buzzerNormal | greenLedOn | entranceClose);
//			
//			entranceLog.setLogData(1, 0);
		
		}
		return code;
	}
	
	private static byte getCodeAfterEntranceClosed1(int waterLevel, PastData pastData) {
		byte code = 0; 
		if(pastData.getWaterLevelCount() == 0) {
			pastData.setWaterLevelCount(pastData.getWaterLevelCount() + 1);
			pastData.setPastWaterLevel(waterLevel);
		}else if(pastData.getWaterLevelCount() <= count) {
			pastData.setWaterLevelCount(pastData.getWaterLevelCount() + 1);
		}else if(pastData.getWaterLevelCount() > count) { 
			pastData.setWaterLevelCount(0);
			if(waterLevel == pastData.getPastWaterLevel()) {  
				if(findRecentOffPin() == entranceLog.getPinNumber()) {
					code = (byte)(buzzerNormal | greenLedOn | heaterOn);
//					
//					heaterLog.setLogData(3, 1);
		
				}
			}else {
				code = (byte)(buzzerMalfunction | yellowLedOn | entranceClose);	
//				
//				entranceLog.setLogData(1, 0);
			
			}
		}
		return code;
	}
	
	private static byte getCodeAfterOutletOpened1(int waterLevel, PastData pastData) {
		byte code = 0; 
		if(waterLevel > SensorDataCriteria.getWaterlevelminimum()){
			if(pastData.getWaterLevelCount() == 0) {
				pastData.setWaterLevelCount(pastData.getWaterLevelCount() + 1);
				pastData.setPastWaterLevel(waterLevel);
			}else if(pastData.getWaterLevelCount() <= count) {
				pastData.setWaterLevelCount(pastData.getWaterLevelCount() + 1);
			}else if(pastData.getWaterLevelCount() > count) {
				pastData.setWaterLevelCount(0);
				if(waterLevel >= pastData.getPastWaterLevel()) {
					code = (byte)(buzzerMalfunction | yellowLedOn | outletOpen);
//					
//					outletLog.setLogData(2, 1);
				
				}	
			}
		}
		return code;
	}
	
	private static byte getCodeAfterOutletClosed1(int waterLevel, PastData pastData) {
		byte code = 0; 
		if(pastData.getWaterLevelCount() == 0) {
			pastData.setWaterLevelCount(pastData.getWaterLevelCount() + 1);
			pastData.setPastWaterLevel(waterLevel);
		}else if(pastData.getWaterLevelCount() <= count) {
			pastData.setWaterLevelCount(pastData.getWaterLevelCount() + 1);
		}else if(pastData.getWaterLevelCount() > count) { 
			pastData.setWaterLevelCount(0);
			if((waterLevel <= pastData.getPastWaterLevel()) && 
					(findRecentOffPin() == outletLog.getPinNumber())) {
				code = (byte)(buzzerNormal | greenLedOn | entranceOpen);
//				
//				entranceLog.setLogData(1, 1);
				
			}else {
				code = (byte)(buzzerMalfunction | yellowLedOn | outletClose);	
//				
				outletLog.setLogData(2, 0);
			
			}
		}
		return code;
	}
	
	private static byte getCodeAfterEntranceOpened2(int weight, PastData pastData) {
		byte code = 0; 
		if(weight < SensorDataCriteria.getWeightmaximum()) {
			if(pastData.getWeightCount() == 0) {
				pastData.setWeightCount(pastData.getWeightCount() + 1);
				pastData.setPastWeight(weight);
			}else if(pastData.getWeightCount() <= count) {
				pastData.setWeightCount(pastData.getWeightCount() + 1);
			}else if(pastData.getWeightCount() > count) { 
				pastData.setWeightCount(0);
				if(weight <= pastData.getPastWeight()) {
					code = (byte)(buzzerMalfunction | yellowLedOn | entranceOpen);	
//					
//					entranceLog.setLogData(1, 1);
				
				}
			}
		}else if(weight >= SensorDataCriteria.getWeightmaximum()){
			pastData.setWeightCount(0);
			pastData.setPastWeight(weight);
			code = (byte)(buzzerNormal | greenLedOn | entranceClose);
//			
//			entranceLog.setLogData(1, 0);
		
		}
		return code;
	}
	
	private static byte getCodeAfterEntranceClosed2(int weight, PastData pastData) {
		byte code = 0; 
		if(pastData.getWeightCount() == 0) {
			pastData.setWeightCount(pastData.getWeightCount() + 1);
			pastData.setPastWeight(weight);
		}else if(pastData.getWeightCount() <= count) {
			pastData.setWeightCount(pastData.getWeightCount() + 1);
		}else if(pastData.getWeightCount() > count) { 
			pastData.setWeightCount(0);
			if(weight == pastData.getPastWeight()) {
				if(findRecentOffPin() == entranceLog.getPinNumber()) {
					code = (byte)(buzzerNormal | greenLedOn | heaterOn);
//					
//					heaterLog.setLogData(3, 1);
				
				}
			}else {
				code = (byte)(buzzerMalfunction | yellowLedOn | entranceClose);	
//				
//				entranceLog.setLogData(1, 0);
			
			}
		}
		return code;
	}
	
	private static byte getCodeAfterOutletOpened2(int weight, PastData pastData) {
		byte code = 0; 
		if(weight > SensorDataCriteria.getWeightminimum()){
			if(pastData.getWeightCount() == 0) {
				pastData.setWeightCount(pastData.getWeightCount() + 1);
				pastData.setPastWeight(weight);
			}else if(pastData.getWeightCount() <= count) {
				pastData.setWeightCount(pastData.getWeightCount() + 1);
			}else if(pastData.getWeightCount() > count) {
				pastData.setWeightCount(0);
				if(weight >= pastData.getPastWeight()) {
					code = (byte)(buzzerMalfunction | yellowLedOn | outletOpen);
//					
//					outletLog.setLogData(2, 1);
					
				}	
			}
		}else {
			code = (byte)(buzzerNormal | greenLedOn | outletClose);
//			
//			outletLog.setLogData(2, 0);
		
		}
		return code;
	}
	
	private static byte getCodeAfterOutletClosed2(int weight, PastData pastData) {
		byte code = 0; 
		if(pastData.getWeightCount() == 0) {
			pastData.setWeightCount(pastData.getWeightCount() + 1);
			pastData.setPastWeight(weight);
		}else if(pastData.getWeightCount() <= count) {
			pastData.setWeightCount(pastData.getWeightCount() + 1);
		}else if(pastData.getWeightCount() > count) { 
			pastData.setWeightCount(0);
			if((weight <= pastData.getPastWeight()) && 
					(findRecentOffPin() == outletLog.getPinNumber())) {
				code = (byte)(buzzerNormal | greenLedOn | entranceOpen);
//				
//				entranceLog.setLogData(1, 1);
		
			}else {
				code = (byte)(buzzerMalfunction | yellowLedOn | outletClose);	
//				
//				outletLog.setLogData(2, 0);
			
			}
		}
		return code;
	}
	
	
	
	
	
	private static byte getCodeWhenHeaterOff(int temperature, PastData pastData) {
		byte code = 0; 
		if(temperature <= SensorDataCriteria.getTemperaturemaximum()) {
			if(pastData.getTemperatureCount() == count) {
				pastData.setTemperatureCount(pastData.getTemperatureCount() + 1);
				pastData.setPastTemperature(temperature);
			}else if(pastData.getTemperatureCount() <= count) {
				pastData.setTemperatureCount(pastData.getTemperatureCount() + 1);
			}else if(pastData.getTemperatureCount() > count) {
				pastData.setTemperatureCount(0);
				if(temperature <= pastData.getPastTemperature()) {
					if(findRecentOffPin() == heaterLog.getPinNumber()) {
						code = (byte)(buzzerNormal | greenLedOn | outletOpen);
//						
//						outletLog.setLogData(2, 1);
					
					}
				}else {
					code = (byte)(buzzerMalfunction | yellowLedOn | heaterOff);
//					
//					heaterLog.setLogData(3, 0);
				
				}
			}
		}else if(temperature > SensorDataCriteria.getTemperaturemaximum()){
			if(pastData.getTemperatureCount() == 0) {
				pastData.setTemperatureCount(pastData.getTemperatureCount() + 1);
				pastData.setPastTemperature(temperature);
			}else if(pastData.getTemperatureCount() <= count) {
				pastData.setTemperatureCount(pastData.getTemperatureCount() + 1);
			}else if(pastData.getTemperatureCount() > count) {
				pastData.setTemperatureCount(0);
				if(temperature >= pastData.getPastTemperature()) {
					code = (byte)(buzzerMalfunction | yellowLedOn | heaterOff);
//					
//					heaterLog.setLogData(3, 0);
					
				}
			}
		}
		return code;
		
	}
	
	private static byte getCodeWhenHeaterOn(int temperature, PastData pastData) {
		byte code = 0; 
		if(temperature < SensorDataCriteria.getTemperaturemaximum()) {
			if(pastData.getTemperatureCount() == 0) {
				pastData.setTemperatureCount(pastData.getTemperatureCount() + 1);
				pastData.setPastTemperature(temperature);
			}else if(pastData.getTemperatureCount() <= count) {
				pastData.setTemperatureCount(pastData.getTemperatureCount() + 1);
			}else if(pastData.getTemperatureCount() > count) { 
				pastData.setTemperatureCount(0);
				if(temperature <= pastData.getPastTemperature()) {
					code = (byte)(buzzerMalfunction | yellowLedOn | heaterOn);	
//					
//					heaterLog.setLogData(3, 1);
					
				}
			}
		}else if(temperature >= SensorDataCriteria.getTemperaturemaximum()) {
			pastData.setTemperatureCount(0);
			pastData.setPastTemperature(temperature);
			code = (byte)(buzzerNormal | greenLedOn | heaterOff);
//			
			heaterLog.setLogData(3, 0);
		
		}
		return code;
	}
	
	private static int findRecentOffPin() {
		int pinNumber = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
	
			long entranceOffTime = sdf.parse(entranceLog.getTimeLog()).getTime();
			long outletOffTime = sdf.parse(outletLog.getTimeLog()).getTime();
			long heaterOffTime = sdf.parse(heaterLog.getTimeLog()).getTime();
			if((entranceLog.getState() == 0) && (outletLog.getState() == 0) && (heaterLog.getState() == 0)) {
				if(outletOffTime >= entranceOffTime) {
					if(outletOffTime >= heaterOffTime) {
						pinNumber = outletLog.getPinNumber();
					}else {
						pinNumber = heaterLog.getPinNumber();
					}
				}else {
					if(entranceOffTime >= heaterOffTime) {
						pinNumber = entranceLog.getPinNumber();
					}else {
						pinNumber = heaterLog.getPinNumber();
					}
				}
			}
			
			
		}catch(ParseException e) {
			System.out.println("e: " + e);
		}
		return pinNumber;
	}
	
	private static void parseCodeAndInsert(byte code, PastData pastData) {
		if(code != 0) {
			//entrance
			if((code & 0b1) == 0b1) {
				if(pastData.getPastEntranceState() == 0) {
					int rc = 0;
					while(rc == 0) {
						entranceLog.setLogData(1);
						rc = logDAO.insertComponentLog(entranceLog);
					}
					pastData.setCountsToZero();
					System.out.println("en on");
					pastData.setPastEntranceState(1);
					
				}
			}else {
				if(pastData.getPastEntranceState() == 1) {
					int rc = 0;
					while(rc == 0) {
						entranceLog.setLogData(0);
						rc = logDAO.insertComponentLog(entranceLog);
						
					}
					pastData.setCountsToZero();
					System.out.println("en off");
					pastData.setPastEntranceState(0);
				}
			}
			//outlet
			if((rightShiftCode(code, 1) & 0b1) == 0b1) {
				if(pastData.getPastOutletState() == 0) {
					int rc = 0;
					while(rc == 0) {
						outletLog.setLogData(1);
						rc = logDAO.insertComponentLog(outletLog);
						
					}
					pastData.setCountsToZero();
					System.out.println("out on");
					pastData.setPastOutletState(1);
				}
			}else {
				if(pastData.getPastOutletState() == 1) {
					int rc = 0;
					while(rc == 0) {
						outletLog.setLogData(0);
						rc = logDAO.insertComponentLog(outletLog);
						
					}
					pastData.setCountsToZero();
					System.out.println("out off" );
					pastData.setPastOutletState(0);
				}
			}
			//heater
			if((rightShiftCode(code, 2) & 0b1) == 0b1) {
				if(pastData.getPastHeaterState() == 0) {
					int rc = 0;
					while(rc == 0) {
						heaterLog.setLogData(1);;
						rc = logDAO.insertComponentLog(heaterLog);
						
					}
					pastData.setCountsToZero();
					System.out.println("heat on");
					pastData.setPastHeaterState(1);
				}
			}else {
				if(pastData.getPastHeaterState() == 1) {
					int rc = 0;
					while(rc == 0) {
						heaterLog.setLogData(0);
						rc = logDAO.insertComponentLog(heaterLog);
						
					}
					pastData.setCountsToZero();
					System.out.println("heat off");
					pastData.setPastHeaterState(0);
				}
			}
			//greenled
			if((rightShiftCode(code, 3) & 0b1) == 0b1) {
				int rc = 0;
				while(rc == 0) {
					greenLedLog.setLogData(1);;
					rc = logDAO.insertComponentLog(greenLedLog);
					
				}
				System.out.println("green on");
				pastData.setPastGreenLedState(1);
			}
			
			//yellowled
			if((rightShiftCode(code, 4) & 0b1) == 0b1) {
				if(pastData.getPastYellowLedState() == 0) {
					int rc = 0;
					while(rc == 0) {
						yellowLedLog.setLogData(1);;
						rc = logDAO.insertComponentLog(yellowLedLog);
						
					}
					System.out.println("yellow on");
					pastData.setPastYellowLedState(1);
				}
			}
			//redled
			if((rightShiftCode(code, 5) & 0b1) == 0b1) {
				if(pastData.getPastRedLedState() == 0) {
					int rc = 0;
					while(rc == 0) {
						redLedLog.setLogData(1);;
						rc = logDAO.insertComponentLog(redLedLog);
						
					}
					System.out.println("red on");
					pastData.setPastRedLedState(1);
				}
			}
			//buzzer
			switch((byte)(rightShiftCode(code, 6) & 0b11)) {
			case 0b00:
				break;
			case 0b01:
				int rc1 = 0;
				while(rc1 == 0) {
					buzzerLog.setLogData(1);;
					rc1 = logDAO.insertComponentLog(buzzerLog);
					
				}
				System.out.println("Buzzer 1");
				pastData.setPastBuzzerState(1);
				break;
			case 0b10:
				if(pastData.getPastBuzzerState() == 0) {
					int rc2 = 0;
					while(rc2 == 0) {
						buzzerLog.setLogData(2);;
						rc2 = logDAO.insertComponentLog(buzzerLog);
						
					}
					System.out.println("Buzzer 2");
					pastData.setPastBuzzerState(2);
				}
				break;
			case 0b11:
				if(pastData.getPastBuzzerState() == 0) {
					int rc3 = 0;
					while(rc3 == 0) {
						buzzerLog.setLogData(3);;
						rc3 = logDAO.insertComponentLog(buzzerLog);
						
					}
					System.out.println("Buzzer 3");
					pastData.setPastBuzzerState(3);
				}
				break;
			}
		}
	}
	
	private static byte rightShiftCode(byte code, int shift) {
		return (byte)(code >> shift);	
	}

	
	
	
	public static void main(String[] args) {
		
		PastData pastData = new PastData();
		
		
		
		long startTime = System.currentTimeMillis();
		
		int i = 0;
		int j = 0;
		String f = "F";
		while(true) {
			
			if(((System.currentTimeMillis() - startTime) % 1000) == 0) {
				System.out.println("============================");
//				if((System.currentTimeMillis() - startTime) > 10000) {
//					f = "T";
//				}
				if(entranceLog.getState() == 1) {
					i++;
					j=0;
					System.out.println("phase 2");

				}else if(outletLog.getState() == 1) {
					i--;
					
					System.out.println("phase 6");

				}else if(heaterLog.getState() == 1) {
					
					j++;
					System.out.println("phase 4");


				}else {
					if(findRecentOffPin() == entranceLog.getPinNumber()) {
						System.out.println("phase 3");

					}else if(findRecentOffPin() == outletLog.getPinNumber()) {
						System.out.println("phase 1");

					}else if(findRecentOffPin() == heaterLog.getPinNumber()) {
						j--;
						System.out.println("phase 5");

					}
					
				}
				System.out.println("f: " + f + ", i: " + i + ", j: " + j);
				SterlizerSensorData sensorData = new SterlizerSensorData(f, i, i, j);
				System.out.println(Integer.toBinaryString(getControlCode(sensorData, pastData)));
				System.out.println("count: " + pastData.getWaterLevelCount() + ", " + pastData.getWeightCount());
				System.out.println("en: " + entranceLog.getState() + ", out: " + outletLog.getState() + ", heat: " + heaterLog.getState());
			}
		}
		
		
	}
	
}
