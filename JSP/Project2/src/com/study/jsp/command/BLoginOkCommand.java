package com.study.jsp.command;

import java.io.PrintWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.study.jsp.MembersDAO;
import com.study.jsp.MembersDTO;

public class BLoginOkCommand implements BCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
	{
		try {
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
	 	} else if (checkNum == 2){
	 		wr.println("<html><head></head><body>");
			wr.println("<script language='javascript'>");
			wr.println("alert('블랙리스트 대상자입니다. 로그인 할 수 없습니다.');");
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
				//dao.updateLogin(id, -1, 0);
				response.sendRedirect("client.jsp");
			}
	 	}
	 	wr.close();
		} catch(Exception e) {
			
		}
	}

}
