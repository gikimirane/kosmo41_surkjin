<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ page import="java.sql.DriverManager" %>
 <%@ page import="java.sql.ResultSet" %>
 <%@ page import="java.sql.Statement" %>
 <%@ page import="java.sql.Connection" %>
 
 <%!
 	Connection con;
	Statement stmt;
	ResultSet rs;
	
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:xe";
	String uid = "scott";
	String upw = "tiger";
 %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Jsp 20-2</title>
</head>
<body>
<%

try{
		Class.forName(driver);
		con = DriverManager.getConnection(url, uid, upw);
		
		String sql = "select * from member";
		stmt = con.createStatement();
		rs = stmt.executeQuery(sql);
		
		while (rs.next()){
			String id = rs.getString("id");
			String pw = rs.getString("pw");
			String name = rs.getString("name");
			String phone = rs.getString("phone");
			String gender = rs.getString("gender");
			
			out.println("아이디: " + id +
						",비밀번호: " + pw  +
						",이름: " + name +
						",연락처: " + phone +
						",성별: " + gender + "<br>");
		}
		
	} catch (Exception e) {
		if(e.getMessage().contains("ORA-00001")){
			out.println("중복되는 회원아이디가 있습니다.");
		}
	} finally {
		try {
			if( rs != null) rs.close();
			if( stmt != null) stmt.close();
			if( con  != null) con.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
%>
</body>
</html>