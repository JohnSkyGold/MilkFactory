package kr.co.jongnomilk.controller;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.jongnomilk.dao.ComponentLogDAO;
import kr.co.jongnomilk.model.ComponentLogData;
import kr.co.jongnomilk.util.CameraControlUtil;

public class CameraOffController implements Controller{

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	//		String serialNumber = request.getParameter("serial_no");
	////	존재하는 기기인지 확인
	//	FacilityItemData item = new FacilityItemData();
	//	FacilityItemDAO dao = FacilityItemDAO.getFacilityItemDAO();
	//	if(dao.getFacilityItemData("MS01") == null) {
	//		return "confailtest.jsp";
	//	}
		String pinNo = request.getParameter("pin_no");
		String controlCode = request.getParameter("control_code");
		
		System.out.println(pinNo);
		System.out.println(controlCode);
		
		String flag = CameraControlUtil.controlCamera(Integer.parseInt(controlCode));
		ServletContext application =request.getServletContext();
		application.setAttribute("MS01" + "onAir", false);
		
		if("false".equals(flag)) {
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

}
