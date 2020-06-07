package kr.co.jongnomilk.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.jongnomilk.dao.SensorDAO;
import kr.co.jongnomilk.model.SensorData;
import kr.co.jongnomilk.util.TestThread;


public class ControllerTest2 implements Controller{

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String serialNumber = request.getParameter("serialNo");
		ServletContext application =request.getServletContext();
		application.setAttribute(serialNumber + "onAir", false);
		
		return null;
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
	
	byte getCode() {
		byte control = 0b00000000;
		byte buzzer = 0b11;
		byte led1 = 0b1;
		byte led2 = 0b1;
		byte led3 = 0b1;
		byte heatpad = 0b1;
		byte motor1 = 0b1;
		byte motor2 = 0b1;

		control = (byte)(control | buzzer << 6 | led1 << 5 | led2 << 4 | led3 << 3 | 
				heatpad << 2 | motor2 << 1 | motor1);
		return control;
	}
	

}
