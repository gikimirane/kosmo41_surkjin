package com.study.jsp;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/FormEx")
public class FormEx extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException 
	{
		System.out.println("doGet~~");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException 
	{
		System.out.println("doPost~~");
		request.setCharacterEncoding("UTF-8");
		
		String nm = request.getParameter("name");
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		
		String[] hobbys = request.getParameterValues("hobby");
		String mjr = request.getParameter("major");
		String prc = request.getParameter("protocol");
		
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		out.println("<html><head></head><body>");
		out.println("이름 : " + nm + "<br>");
		out.println("아이디 : " + id + "<br>");
		out.println("비밀번호 : " + pw + "<br>");
		out.println("취미 : " + Arrays.toString(hobbys) + "<br>");
		out.println("전공 : " + mjr + "<br>");
		out.println("프로토콜 : " + prc + "<br>");
		out.println("</body></html>");
		
		out.close();
	}

}
