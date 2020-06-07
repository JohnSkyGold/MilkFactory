<%@page import="kr.co.jongnomilk.model.EmployeeData"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%><%
   	EmployeeData data = (EmployeeData)request.getAttribute("vo");
    out.println("{\"authority\" : " + data.getAuthority() + ", \"position\" : " + data.getPosition() +
    	", \"department\" : " + data.getDeparture() + ", \"name\" : " + data.getName() + " }");
    %>
    
    