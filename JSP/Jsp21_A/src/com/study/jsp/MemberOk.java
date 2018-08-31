package com.study.jsp;
import java.sql.Timestamp;
import com.study.jsp.MembersDAO;
import com.study.jsp.MembersDTO;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("*.do")
public class MemberOk extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public  MemberOk() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException 
	{
		System.out.println("doGet");
		actionDo(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException 
	{
		System.out.println("doPost");
		actionDo(request, response);
	}
	
	private void actionDo(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException 
	{
		System.out.println("actionDo");
		String uri = request.getRequestURI();
		System.out.println("uri : " + uri);
		String conPath = request.getContextPath();
		System.out.println("conPath : " + conPath);
		String command = uri.substring(conPath.length());
		System.out.println("command : " + command);
		
		if (command.equals("/joinOk.do")) {
			System.out.println("joinOk");
			joinOk(request, response);
		} else if (command.equals("/loginOk.do")) {
			System.out.println("loginOk");
			loginOk(request, response);
		} else if (command.equals("/modifyOk.do")) {
			System.out.println("modifyOk");
			modifyOk(request, response);
		} else if (command.equals("/logout.do")) {
			System.out.println("logout");
			request.getSession().invalidate();
			response.sendRedirect("login.jsp");
		}
	}
	
	public void joinOk (HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
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
	
	public void loginOk (HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
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
	
	public void modifyOk (HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
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
