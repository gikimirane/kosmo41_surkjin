<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ page import="java.sql.DriverManager" %>
 <%@ page import="java.sql.ResultSet" %>
 <%@ page import="java.sql.Statement" %>
 <%@ page import="java.sql.Connection" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%!
	Connection conn;
	Statement stmt;
	ResultSet rsult;
	
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:xe";
	String uid = "scott";
	String upw = "tiger";
	String sql = "select * from member";
%>
<title>Jsp 18-1</title>
</head>
<body>
<%
	try{
		Class.forName(driver);
		conn = DriverManager.getConnection(url, uid, upw);
		stmt = conn.createStatement();
		rsult = stmt.executeQuery(sql);
		
		while(rsult.next()){
			String id = rsult.getString("id");
			String pw = rsult.getString("pw");
			String nm = rsult.getString("name");
			String ph = rsult.getString("phone");
			
			out.println("아이디: " + id + " 비밀번호: " + pw + " 이름: " + nm + " 전화번호: " + ph + "<br>");
		}
	}catch(Exception e) {
	
	}finally {
		try {
				
			if( rsult!=null)  rsult.close();
			if( stmt!=null) stmt.close();
			if( conn!=null) conn.close();
			
		}catch(Exception e) {
			
		}
	} 
%>
</body>
</html>