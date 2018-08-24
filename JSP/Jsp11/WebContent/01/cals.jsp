<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>cals</title>
</head>
<body>
<%
	request.setCharacterEncoding("UTF-8");

	String num1 = request.getParameter("num1");
	String num2 = request.getParameter("num2");
	String cal = request.getParameter("cals");
	
	int n1=0, n2=0;
	try{
		n1 = Integer.parseInt(num1);
		n2 = Integer.parseInt(num2);
	}catch(NumberFormatException e){
		
	}
	float r = 0;

	String rst = "";
	try{
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
	}catch(Exception e){
		
	}
%>
	첫번째수 : <%= num1 %> <br/>
	두번째수 : <%= num2 %> <br/><br/>
	<%= rst %> <br/>

</body>
</html>