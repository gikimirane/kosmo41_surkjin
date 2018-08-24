<%@ page import="java.util.Enumeration"  %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Jsp 13-5</title>
</head>
<body>
	<%
		String sName;
		String sValue;
		Enumeration enumeration1 = session.getAttributeNames();
		int i=0;
		while(enumeration1.hasMoreElements()){
			i++;
			sName = enumeration1.nextElement().toString();
			sValue = session.getAttribute(sName).toString();
			out.println("Name" + " : " + sName + "<br>");
			out.println("Value" + " : " + sValue + "<br>");
		}
		
		if(i==0)
			out.println("해당 세션이 삭제되었습니다." + "<br>");
	%>
</body>
</html>