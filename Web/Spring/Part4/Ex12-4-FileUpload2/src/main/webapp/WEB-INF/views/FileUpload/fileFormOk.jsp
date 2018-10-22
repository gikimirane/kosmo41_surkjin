<%@ page import="java.util.Enumeration" %>
<%@ page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy" %>
<%@ page import="com.oreilly.servlet.MultipartRequest" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getRealPath("fileFolder");

	int size = 1024 * 1024 *10;
	
	String file = "";
	String oriFile = "";
	
	try{
		MultipartRequest multi = new MultipartRequest(request, path, size,
						"UTF-8", new DefaultFileRenamePolicy());
		Enumeration files = multi.getFileNames();
		String str = (String)files.nextElement();
		file = multi.getFilesystemName(str);
		oriFile = multi.getOriginalFileName(str);
	} catch (Exception e) {
		e.printStackTrace();
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Jsp 22-2</title>
</head>
<body>
	file upload success!
</body>
</html>