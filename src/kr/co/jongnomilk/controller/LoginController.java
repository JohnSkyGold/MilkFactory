package kr.co.jongnomilk.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.Session;

import kr.co.jongnomilk.dao.EmployeeDAO;
import kr.co.jongnomilk.model.EmployeeData;

public class LoginController implements Controller{

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		response.setContentType("text/html);charset=utg-8");
		request.setCharacterEncoding("UTF-8");
		String id = request.getParameter("user_id");
		String passwd = request.getParameter("pwd"); 
		
		System.out.println("id: " + id);
		System.out.println("passwd: " + passwd);
		
		
		EmployeeDAO dao = new EmployeeDAO();
		EmployeeData vo = dao.getEmployee(id, Integer.parseInt(passwd));
		request.setAttribute("vo", vo);
		System.out.println(vo);
		
		if(vo == null){
			System.out.println("fail");
			return "login_fail.jsp";
		}else {
			System.out.println("success");
			return "login_success.jsp";
		}
		
//		if("milk".equals(id) && "milk".equals(passwd)) {
//			return "login_success.jsp";
//		}else {
//			return "login_fail.jsp";
//		}
	}
	

}
