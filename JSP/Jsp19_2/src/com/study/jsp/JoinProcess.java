package com.study.jsp;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/JoinProcess")
public class JoinProcess extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Connection con;
	private PreparedStatement pstmt;
	
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:xe";
	String uid = "scott";
	String upw = "tiger";
	
	private String id, pw, name, phn1, phn2, phn3, gend;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException 
	{
		System.out.println("doGet");
		actionDo(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException 
	{
		System.out.println("doPost");
		actionDo(request, response);
	}
	
	protected void actionDo(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		
		id = request.getParameter("id");
		pw = request.getParameter("pw");
		name = request.getParameter("name");
		phn1 = request.getParameter("phone1");
		phn2 = request.getParameter("phone2");
		phn3 = request.getParameter("phone3");
		gend = request.getParameter("gender");
		
		//값체크후 정상이면 이후 진행
		String sql = "insert into member values(?, ?, ?, ?, ?)";
		
		try {
			Class.forName(driver);
			con =  DriverManager.getConnection(url, uid, upw);
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			pstmt.setString(3, name);
			pstmt.setString(4, phn1+"-"+phn2+"-"+phn3);
			pstmt.setString(5, gend);
			
			int updCnt = pstmt.executeUpdate();
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			
			if(updCnt == 1) {
				System.out.println("Insert success");
				out.println("[{\"result\":\"ok\",\"desc\":\"none\"}]");
				out.close();
			}		
		} catch(Exception e) {
			System.out.println("Insert fail");
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("[{\"result\":\"fail\",\"desc\":\"이미 사용중인 아이디가 있습니다.\"}]");
			out.close();
		} finally {
			try {
				if( pstmt!=null) pstmt.close();
				if( con!=null) con.close();
			}catch(Exception e) {}
		}
	}
}
