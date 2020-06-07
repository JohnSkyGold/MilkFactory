package kr.co.jongnomilk.test;

import java.sql.Connection;

import kr.co.jongnomilk.dao.EmployeeDAO;
import kr.co.jongnomilk.model.EmployeeData;
import kr.co.jongnomilk.util.JDBCUtil;

public class LoginTest {

	public LoginTest() {
		
		
		String id="A0001";
		int passwd = 1234;
		EmployeeDAO dao = new EmployeeDAO();
		EmployeeData vo = dao.getEmployee(id, passwd);
		
		
		if(vo == null){
			System.out.println("login fail");
		}else {
			System.out.println("login success");
			System.out.println(vo.getName());
		}
	}

	public static void main(String[] args) {
		new LoginTest();

	}

}
