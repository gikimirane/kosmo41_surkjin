package com.study.jsp;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/LoginProcess")
public class LoginProcess extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private Connection con;
	private PreparedStatement pstmt;
	private ResultSet rsult;
	
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:xe";
	String uid = "scott";
	String upw = "tiger";

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException 
	{
		actionDo(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException 
	{
		actionDo(request, response);
	}
	
	protected void actionDo(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException 
	{
		String id, pw, name="", phn, gend;
		
		id = request.getParameter("id");
		pw = request.getParameter("pw");
		
		String sql = "select * from member where id=? and pw=?";
		
		try {
			Class.forName(driver);
			con =  DriverManager.getConnection(url, uid, upw);
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			
			rsult = pstmt.executeQuery();
			
			while(rsult.next()) {
				name = rsult.getString("name");
				phn = rsult.getString("phone");
				gend = rsult.getString("gender");
			}
			
			HttpSession session = request.getSession();
			session.setAttribute("name", name);
			session.setAttribute("id", id);
			session.setAttribute("pw", pw);
			
			response.sendRedirect("loginResult.jsp");
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if( rsult!=null) rsult.close();
				if( pstmt!=null) pstmt.close();
				if( con!=null) con.close();
			}catch(Exception e2) {
				e2.printStackTrace();
			}
		}
	}

}
