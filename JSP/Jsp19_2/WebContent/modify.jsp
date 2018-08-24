<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.DriverManager" %>
 <%@ page import="java.sql.ResultSet" %>
 <%@ page import="java.sql.Statement" %>
 <%@ page import="java.sql.Connection" %>
 
 <%!
 	Connection conn;
	Statement stmt;
	ResultSet rsult;
	
	String driver = "oracle.jdbc.driver.OracleDriver";
	String url = "jdbc:oracle:thin:@localhost:1521:xe";
	String uid = "scott";
	String upw = "tiger";
	String sql = "select * from member";
	
	String name, id, pw, phn1, phn2, phn3, gend;
 %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Modify</title>
<script src="http://code.jquery.com/jquery.js"></script>
<script>
	function form_check() {
		submit_ajax();
	}

	function submit_ajax() {
		
		var qStr = $("#ModifyProcess").serialize();
		
		$.ajax({
			url  : '/Jsp19_2/ModifyProcess',
			type : 'POST',
			data : qStr,
			dataType : 'json',
			success : function (json){
				var results = eval(json);
				if(results[0].result=="ok"){
					alert("정상적으로 회원정보가 수정되었습니다");
					window.location.replace("modifyResult.jsp");
				} else {
					alert(results[0].desc);
				}	
			}
		});
	}
	
</script>
</head>
<body>
<%
	id = (String)session.getAttribute("id");

	String sql = "select * from member where id = '"+id+"'";
	
	Class.forName(driver);
	conn = DriverManager.getConnection(url, uid, upw);
	stmt = conn.createStatement();
	rsult = stmt.executeQuery(sql);
	
	String phn="";
	
	while(rsult.next()){
		pw = rsult.getString("pw");
		name = rsult.getString("name");
		phn = rsult.getString("phone");
		gend = rsult.getString("Gender");
	}
	
	phn1 = phn.substring(0, 3);
	phn2 = phn.substring(4, 8);
	phn3 = phn.substring(9, 13);
%>
<form name="ModifyProcess" id="ModifyProcess">
		아이디 : <%= id %><br/>
		비밀번호 : <input type="text" name="pw" value=<%=pw%> size="10"><br/>
		이름 : <input type="text" name="name" value=<%=name%> size="15"><br/>
		전화번호 : <select name="phone1" value=<%=phn1%>>
			<option value="010">010</option>
			<option value="011">011</option>
			<option value="016">016</option>
			<option value="017">017</option>
			<option value="018">018</option>
			<option value="019">019</option>
		</select> -
		<input type="text" name="phone2" value=<%=phn2%> size="5"> -
		<input type="text" name="phone3" value=<%=phn3%> size="5"> <br/>
		<%
			if(gend.equals("1")){
		%>
		성별구분 : <input type="radio" name="gender" value="1" checked="checked">남 &nbsp;
				   <input type="radio" name="gender" value="2">여 <br><br>
		<%
			} else {
		%>	  
		성별구분 : <input type="radio" name="gender" value="1">남 &nbsp;
				   <input type="radio" name="gender" value="2" checked="checked">여 <br><br> 
		<%
			}
		%>
		<input type="button" value="정보수정" onclick="form_check()" /> <input type="reset" value="취소">
	</form>
</body>
</html>