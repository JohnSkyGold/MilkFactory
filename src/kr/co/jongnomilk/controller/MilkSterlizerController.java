package kr.co.jongnomilk.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.annotations.SerializedName;

import kr.co.jongnomilk.dao.ComponentLogDAO;
import kr.co.jongnomilk.dao.FacilityItemDAO;
import kr.co.jongnomilk.dao.SensorDAO;
import kr.co.jongnomilk.model.ComponentLogData;
import kr.co.jongnomilk.model.Data;
import kr.co.jongnomilk.model.FCMMsgData;
import kr.co.jongnomilk.model.FacilityItemData;
import kr.co.jongnomilk.model.OnAirData;
import kr.co.jongnomilk.model.PastData;
import kr.co.jongnomilk.model.SensorData;
import kr.co.jongnomilk.model.SterlizerSensorData;
import kr.co.jongnomilk.util.CameraControlUtil;
import kr.co.jongnomilk.util.FCMServerThread;
import kr.co.jongnomilk.util.ImageDownloadThread;
import kr.co.jongnomilk.util.SensorCompareUtil;

public class MilkSterlizerController implements Controller{

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		요청 파라미터 받기
		String serialNumber = request.getParameter("serial_no");
		String flame = request.getParameter("flame");
		String waterLevel = request.getParameter("waterLevel");
		String weight = request.getParameter("weight");
		String temperature = request.getParameter("temperature");
		
//		존재하는 기기인지 확인
		FacilityItemData item = new FacilityItemData();
		FacilityItemDAO dao = FacilityItemDAO.getFacilityItemDAO();
		if(dao.getFacilityItemData("MS01") == null) {
			return "confailtest.jsp";
		}
		
		
//		데이터 가공
		flame = (flame != null)? flame : "F";
		waterLevel = (waterLevel != null)? waterLevel : "0";
		weight = (weight != null)? weight : "0";
		temperature = (temperature != null)? temperature : "0";
		
//		센서 데이터 저장
		SterlizerSensorData sensorData = new SterlizerSensorData(flame,
				Integer.parseInt(waterLevel),
				Integer.parseInt(weight),
				Integer.parseInt(temperature));
		SensorDAO sensorDAO = SensorDAO.getSensorDAO();
		int rc = 0;
		while(rc == 1) {
			rc = sensorDAO.insertSensorData(sensorData);
			System.out.println("rc: " + rc);
		}
		
//		세션에서 CountUtil 가져오기
		HttpSession session = request.getSession();
		PastData countUtil;
		if(session.getAttribute("countUtil") != null) {
			countUtil = (PastData)session.getAttribute("countUtil");
		}else {
			countUtil = new PastData();
		}
		
		
		
//		컨트롤 코드 생성 후 CountUtil 세션에 저장
		byte controlCode = SensorCompareUtil.getControlCode(sensorData, countUtil);
		session.setAttribute("countUtil", countUtil);
		
//		컨트롤 코드 전송
		String ip = "192.168.0.5";
		boolean flag = false;
		if(controlCode != 0) {
			sendFCMAlarm(controlCode, serialNumber, request);
			flag = sendArduinoMessage(ip, controlCode);
		}
		
//		응답
		if(rc == 1) {
			return "contest.jsp";
		}else {
			return "confailtest.jsp";
		}
	}
	
	boolean sendArduinoMessage(String ip, byte controlCode){
		  boolean flag = false;
		  Socket s = null;
		  InputStream is = null; 	OutputStream os;
		  System.out.println("ip : " + ip);
		  try{
		    s = new Socket(ip,8765);
		    is = s.getInputStream();
		    os = s.getOutputStream();
		    
		    os.write(controlCode);
		    int rData = is.read();
		    if(rData != 'E'){      flag = true;    }
		  }catch(IOException e){
		    System.out.println("sendError : " + e);
		  }finally{
		    if(s != null){
		      try{        s.close();      }catch(IOException e){}
		    }
		  }
		  return flag;
	}
	
	void sendFCMAlarm(byte controlCode, String serialNumber, HttpServletRequest request) {
		boolean saveCode = false;
		if(shiftCode(controlCode, 4) == 0b1) {
			FCMServerThread fcmThread = new FCMServerThread();
			FCMMsgData fcmMsgData = new FCMMsgData();
			Data data = new Data();
			data.setTitle("오작동 경보");
			data.setContent(serialNumber + "에서 오작동이 발견되었습니다.");
			
			fcmMsgData.setSerialNumber("MS01");
			fcmMsgData.setMsg("오작동");
			
			data.setFcmMsgData(fcmMsgData);
			fcmThread.setData(data);
			
			fcmThread.start();
			saveCode = true;
		}
		if(shiftCode(controlCode, 5) == 0b1) {
//			화재 카메라
			String flag = CameraControlUtil.controlCamera(1);
			int rc = 0;
			while("true".equals(flag) && (rc == 1)) {
				ComponentLogData logData = new ComponentLogData();
				logData.setPinNumber(8);
				logData.setState(1);
				ComponentLogDAO logDAO = ComponentLogDAO.getComponentLogDAO();
				rc = logDAO.insertComponentLog(logData);
			}
			ImageDownloadThread download = new ImageDownloadThread();
			download.setData(request, serialNumber);
			download.start();
			ServletContext application =request.getServletContext();
			
			
//			화재 FCM
			FCMServerThread fcmThread = new FCMServerThread();
			FCMMsgData fcmMsgData = new FCMMsgData();
			Data data = new Data();
			data.setTitle("화재 경보");
			data.setContent(serialNumber + "주위에서 화재가 발생했습니다.");
			
			fcmMsgData.setSerialNumber("MS01");
			fcmMsgData.setMsg("화재발생");
			
			data.setFcmMsgData(fcmMsgData);
			fcmThread.setData(data);
			
			fcmThread.start();
			saveCode = true;
		}
		if(saveCode == true) {
			ServletContext context = request.getServletContext();
			context.setAttribute(serialNumber, controlCode);
		}
	}
	
	byte shiftCode(byte controlCode, int shift) {
		return (byte)(controlCode >> shift);
	}


}
