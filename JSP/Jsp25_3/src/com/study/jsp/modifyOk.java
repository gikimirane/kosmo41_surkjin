package com.study.jsp;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class modifyOk implements Service {
	
	public modifyOk() {
		
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		MembersDTO dto = new MembersDTO();
		String id = (String)request.getSession().getAttribute("id");
		dto.setId(id);
	
		dto.setPw(request.getParameter("pw"));
		dto.seteMail(request.getParameter("eMail"));
		dto.setAddress(request.getParameter("address"));
		
		MembersDAO dao = MembersDAO.getInstance();
		int ri = dao.updateMember(dto);
		
		PrintWriter wr = response.getWriter();
		
		if(ri == 1){	
			wr.println("<html><head></head><body>");
			wr.println("<script language='javascript'>");
			wr.println("alert('정보가 수정되었습니다.');");
			wr.println("document.location.href=\'main.jsp\';");
			wr.println("</script>");
			wr.println("</body></html>");
		} else {
			wr.println("<html><head></head><body>");
			wr.println("<script language='javascript'>");
			wr.println("alert('정보수정에 실패했습니다.');");
			wr.println("history.go(-1);");
			wr.println("</script>");
			wr.println("</body></html>");
		}
		wr.close();
	}

}
