package com.study.jsp02;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ContextInitParam")
public class ContextInitParam extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("doGet~~");
		
		String database = getServletContext().getInitParameter("database");
		String connect = getServletContext().getInitParameter("connect");
		String path2 = getServletContext().getInitParameter("path2");
		
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		out.println("<html><head></head><body>");
	
		out.println("database : " + database + "<br>");
		out.println("connect : " + connect + "<br>");
		out.println("path : " + path2 + "<br>");
		
		out.println("</body></html>");
		
		out.close();
	}

}
