package kr.co.jongnomilk.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import kr.co.jongnomilk.model.ChartData;

public class ChartController implements Controller {

	@Override
	public String handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String chartNumber = request.getParameter("chart_no");
		switch(chartNumber) {
		case "1":
			break;
		case "2":
			break;
		}
		ChartData chartData = new ChartData();
		Gson gson = new Gson();
		String chartJson = gson.toJson(chartData);
		
		request.setAttribute("chartJson", chartJson);
		
		return "chart.jsp";
	}

	public static void main(String[] args) {
		

	}

}
