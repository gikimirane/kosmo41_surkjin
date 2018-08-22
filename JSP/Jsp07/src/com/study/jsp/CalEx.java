package com.study.jsp;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/CalEx")
public class CalEx extends HttpServlet {
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
		
		String num1 = request.getParameter("num1");
		String num2 = request.getParameter("num2");
		String cal = request.getParameter("cals");

//		if(예외) {
//			doFail(request, response, erros);
//			return;
//		}
		
		int n1 = Integer.parseInt(num1);
		int n2 = Integer.parseInt(num2);
		float r = 0;
		
		String rst = "";
		if(cal.equals("add")) {
			r = n1 + n2;
			rst = "더하기 결과: " + r;
		}
		else if(cal.equals("sub")) {
			r = n1 - n2;
			rst = "빼기 결과: " + r;
		}
		else if(cal.equals("mul")) {
			r = n1 * n2;
			rst = "곱하기 결과: " + r;
		}
		else {
			r = n1 / n2;
			rst = "나누기 결과: " + r;
		}
		
		
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		out.println("<html><head></head><body>");
		out.println("첫번째수 : " + num1 + "<br>");
		out.println("두번째수 : " + num2 + "<br><br>");
		out.println(rst + "<br>");
		
		out.println("</body></html>");
		
		out.close();
	}

}
