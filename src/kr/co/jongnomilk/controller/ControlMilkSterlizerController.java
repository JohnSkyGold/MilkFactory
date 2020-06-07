package kr.co.jongnomilk.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.co.jongnomilk.dao.ComponentLogDAO;
import kr.co.jongnomilk.dao.FacilityItemDAO;
import kr.co.jongnomilk.dao.SensorDAO;
import kr.co.jongnomilk.model.ComponentLogData;
import kr.co.jongnomilk.model.FacilityItemData;
import kr.co.jongnomilk.model.PastData;

public class ControlMilkSterlizerController implements Controller{

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		String serialNumber = request.getParameter("serial_no");
////		존재하는 기기인지 확인
//		FacilityItemData item = new FacilityItemData();
//		FacilityItemDAO dao = FacilityItemDAO.getFacilityItemDAO();
//		if(dao.getFacilityItemData("MS01") == null) {
//			return "confailtest.jsp";
//		}
		String pinNo = request.getParameter("pin_no");
		String controlCode = request.getParameter("control_code");
		
		System.out.println(pinNo);
		System.out.println(controlCode);
		
		byte code = getControlCode(pinNo, controlCode, request);
		String ip = "192.168.0.5";
		boolean flag = false;
		System.out.println(code);
		flag = sendArduinoMessage(ip, code);
		System.out.println("flag: " + flag);
		
		
		if(flag == true) {
			ComponentLogData logData = new ComponentLogData();
			logData.setPinNumber(Integer.parseInt(pinNo));
			logData.setState(0);
			ComponentLogDAO logDAO = ComponentLogDAO.getComponentLogDAO();
			logDAO.insertComponentLog(logData);
			
			return "control_success.jsp";
		}else {
			return "control_fail.jsp";
		}
	}
	
	boolean sendArduinoMessage(String ip, byte data){
		  boolean flag = false;
		  Socket s = null;
		  InputStream is = null; 	OutputStream os;
		  System.out.println("ip : " + ip);
		  try{
		    s = new Socket(ip,8765);
		    is = s.getInputStream();
		    os = s.getOutputStream();
		    
		    os.write(data);
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
	
	byte getControlCode(String pinNo, String controlCode, HttpServletRequest request) {
		byte code = (byte)request.getServletContext().getAttribute("MS01");
		byte buzzer = 0b00;
		byte yellowLed = 0b0 << 4; 
		byte redLed = 0b0 << 5;
		
		if("2".equals(pinNo)) {
			code = (byte)(code | yellowLed);
			System.out.println("do 2");
		}else if("3".equals(pinNo)){
			code = (byte)(code | redLed);
			System.out.println("do 3");
		}else if("4".equals(pinNo)) {
			System.out.println("do4");
			switch(controlCode) {
			case "00":
				buzzer = 0b00;
				break;
			case "01":
				buzzer = 0b01;
				break;
			case "10":
				buzzer = 0b10;
				break;
			case "11":
				buzzer = 0b11;
				break;
			}
			code = (byte)(code | buzzer << 6);
		}
		return code;
	}
	
	
}
