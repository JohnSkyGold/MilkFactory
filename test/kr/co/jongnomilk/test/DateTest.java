package kr.co.jongnomilk.test;

import java.text.SimpleDateFormat;
import java.util.Date;

import kr.co.jongnomilk.model.ComponentLogData;

public class DateTest {

	public DateTest() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		String time = sdf.format(date);
		ComponentLogData entranceLog = new ComponentLogData(1, 0, date);
		try {
//			System.out.println(sdf.parse(entranceLog.getTimeLog()).getTime());
//			System.out.println("a");
			System.out.println(time);
			System.out.println(sdf.parse(time).getTime());
			System.out.println(entranceLog.getTimeLog());
			System.out.println(sdf.parse(entranceLog.getTimeLog()).getTime());
		}catch(Exception e) {}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new DateTest();
	}

}
