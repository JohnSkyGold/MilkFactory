package kr.co.jongnomilk.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class DispatcherServlet
 */
@WebServlet("/DispatcherServlet")
public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
        

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}
 
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri = request.getRequestURI();
		System.out.println("uri: " + uri);
		String path = uri.substring(uri.lastIndexOf("/"));
		System.out.println("path: " + path);
		HandlerMapping mappings = new HandlerMapping();
		
		Controller controller = mappings.getController(path);
		
		
		String returnURL = "index.jsp"; //try-catch로 묶어야 해서 try블록 밖에다 만듦.
		
		try{
			returnURL = controller.handleRequest(request, response);
		}catch(Exception e) {
			System.out.println("e: " + e);
		}
		
		RequestDispatcher dispatcher;
		if("img".equals(returnURL.substring(0, 3))) {
			dispatcher = request.getRequestDispatcher("/" + returnURL);
			System.out.println("/" + returnURL);
		}else {
			dispatcher = request.getRequestDispatcher("/WEB-INF/" + returnURL);
			System.out.println("/WEB-INF/" + returnURL);
		}
		dispatcher.forward(request, response);
	}

}
