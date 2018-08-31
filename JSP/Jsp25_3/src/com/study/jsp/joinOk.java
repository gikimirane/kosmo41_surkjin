package com.study.jsp;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class joinOk implements Service {

	public joinOk(){
		
	}
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		MembersDTO dto = new MembersDTO();
		MembersDAO dao = MembersDAO.getInstance();
		HttpSession session = request.getSession();
		
		dto.setId(request.getParameter("id"));
		dto.setPw(request.getParameter("pw"));
		dto.setName(request.getParameter("name"));
		dto.setrDate(new Timestamp(System.currentTimeMillis()));
		dto.seteMail(request.getParameter("eMail"));
		dto.setAddress(request.getParameter("address"));
		
		PrintWriter wr = response.getWriter();
		
		if(dao.confirmId(dto.getId()) == MembersDAO.MEMBER_EXIT) {
			wr.println("<html><head></head><body>");
			wr.println("<script language='javascript'>");
			wr.println("alert('아이디가 이미 존재합니다.');");
			wr.println("history.back();");
			wr.println("</script>");
			wr.println("</body></html>");
		} else {
			int ri = dao.insertMembers(dto);
			if(ri == MembersDAO.MEMBER_JOIN_SUCESS) {
				session.setAttribute("id", dto.getId());
				wr.println("<html><head></head><body>");
				wr.println("<script language='javascript'>");
				wr.println("alert('회원가입을 축하합니다.');");
				wr.println("document.location.href=\'login.jsp\';");
				wr.println("</script>");
				wr.println("</body></html>");
			}  else {
				wr.println("<html><head></head><body>");
				wr.println("<script language='javascript'>");
				wr.println("alert('회원가입에 실패했습니다..');");
				wr.println("document.location.href=\'login.jsp\';");
				wr.println("</script>");
				wr.println("</body></html>");
			}
		}
		wr.close();

	}

}
