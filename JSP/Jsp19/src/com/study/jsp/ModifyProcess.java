package com.study.jsp;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/ModifyProcess")
public class ModifyProcess extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private Connection con;
	private PreparedStatement pstmt;
	
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:xe";
	String uid = "scott";
	String upw = "tiger";
	
	private String id, pw, name, phn1, phn2, phn3, gend;
	HttpSession session;

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
		request.setCharacterEncoding("UTF-8");
		session = request.getSession();
		
		id = (String)session.getAttribute("id");
		
		pw = request.getParameter("pw");
		name = request.getParameter("name");
		phn1 = request.getParameter("phone1");
		phn2 = request.getParameter("phone2");
		phn3 = request.getParameter("phone3");
		gend = request.getParameter("gender");
		
        if(pwConfirm())	{
        	System.out.println("OK");
        	
        	try {
        		
        		Class.forName(driver);
    			con =  DriverManager.getConnection(url, uid, upw);
    			
    			String sql ="update member set name=?, phone=?, gender=? where id=?";
    			pstmt = con.prepareStatement(sql);
   
    			pstmt.setString(1, name);
    			pstmt.setString(2, phn1+"-"+phn2+"-"+phn3);
    			pstmt.setString(3, gend);
    			pstmt.setString(4, id);
    			
    			int updCnt = pstmt.executeUpdate();
    			
    			if(updCnt==1) {
    				System.out.println("Update success");
    				session.setAttribute("name", name);
    				response.setContentType("text/html; charset=UTF-8");
        			PrintWriter out = response.getWriter();
    				out.println("[{\"result\":\"ok\",\"desc\":\"none\"}]");
    				out.close();
    			}	
        	} catch(Exception e) {
        		System.out.println("Update fail");
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("[{\"result\":\"fail\",\"desc\":\"회원정보 수정에 실패했습니다.\"}]");
				out.close();
    		} finally {
    			try {
    				if( pstmt!=null) pstmt.close();
    				if( con!=null) con.close();
    			}catch(Exception e) {}
    		}
        } else {
        	System.out.println("패스워드가 일치하지 않습니다.");
        	response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("[{\"result\":\"fail\",\"desc\":\"패스워드가 일치하지 않습니다.\"}]");
			out.close();
        }
	}
	
	private boolean pwConfirm() {
		boolean rs = false;
		
		String sessionPw = (String)session.getAttribute("pw");
		
		if(sessionPw.equals(pw)) {
			rs = true;
		} else {
			rs = false;
		}
		return rs;
	}
	
}
