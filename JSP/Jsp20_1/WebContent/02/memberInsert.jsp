<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ page import="java.sql.DriverManager" %>
 <%@ page import="java.sql.ResultSet" %>
 <%@ page import="java.sql.PreparedStatement" %>
 <%@ page import="java.sql.Connection" %>
 
 <%!
 	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;
	
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:xe";
	String uid = "scott";
	String upw = "tiger";
 %>
<!DOCTYPE>
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
		int n;
		String sql = "insert into member values (?, ?, ?, ?, ?)";
		pstmt = con.prepareStatement(sql);
		
		pstmt.setString(1, "abc");
		pstmt.setString(2, "123");
		pstmt.setString(3, "홍길동");
		pstmt.setString(4, "010-1234-1234");
		pstmt.setString(5, "1");
		
		n = pstmt.executeUpdate();
		
		pstmt.setString(1, "def");
		pstmt.setString(2, "456");
		pstmt.setString(3, "전우치");
		pstmt.setString(4, "010-1234-3434");
		pstmt.setString(5, "1");
		
		n = pstmt.executeUpdate();
		
		pstmt.setString(1, "ghi");
		pstmt.setString(2, "456");
		pstmt.setString(3, "손오공");
		pstmt.setString(4, "010-3456-3434");
		pstmt.setString(5, "1");
		
		n = pstmt.executeUpdate();
		
		if(n==1) out.println("insert sucess");
		
	} catch (Exception e) {
		if(e.getMessage().contains("ORA-00001")){
			out.println("중복되는 회원아이디가 있습니다.");
		}
	} finally {
		try {
			if( rs != null) rs.close();
			if( pstmt != null) pstmt.close();
			if( con  != null) con.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

%>
	<br />
	<a href="memberDataView.jsp">회원정보 보기</a>
</body>
</html>