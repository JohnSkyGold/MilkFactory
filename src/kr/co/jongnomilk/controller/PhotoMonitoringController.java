package kr.co.jongnomilk.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.jongnomilk.test.PhotoTest;

public class PhotoMonitoringController implements Controller{

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String realPath = request.getServletContext().getRealPath("/img/");
		System.out.println(realPath);
        File file2 = new File(realPath);
        File[] files = file2.listFiles();
        String lastImage = files[files.length - 1].getName();
  
//		
		return "img/" + lastImage;
	}

}
