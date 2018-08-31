package com.study.jsp;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class loginOk implements Service {
	
	public loginOk(){
		
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter wr = response.getWriter();
		 
	 	String id = request.getParameter("id");
	 	String pw = request.getParameter("pw");
	 	
	 	MembersDAO dao = MembersDAO.getInstance();
	 	int checkNum = dao.userCheck(id, pw);
	 	if(checkNum == -1) {
	 		wr.println("<html><head></head><body>");
			wr.println("<script language='javascript'>");
			wr.println("alert('아이디가 존재하지 않습니다.');");
			wr.println("history.go(-1);");
			wr.println("</script>");
			wr.println("</body></html>");
	 	} else if (checkNum == 0){
	 		wr.println("<html><head></head><body>");
			wr.println("<script language='javascript'>");
			wr.println("alert('비밀번호가 틀립니다.');");
			wr.println("history.go(-1);");
			wr.println("</script>");
			wr.println("</body></html>");
	 	} else if (checkNum == 1){
			MembersDTO dto = dao.getMember(id);
			HttpSession session = request.getSession();
			if (dto == null) {
				wr.println("<html><head></head><body>");
				wr.println("<script language='javascript'>");
				wr.println("alert('존재하지 않는 회원 입니다.');");
				wr.println("history.go(-1);");
				wr.println("</script>");
				wr.println("</body></html>");
			} else {
				String name = dto.getName();
				session.setAttribute("id", id);
				session.setAttribute("name", name);
				session.setAttribute("ValidMem", "yes");
				response.sendRedirect("main.jsp");
			}
	 	}
	 	wr.close();
	}

}
