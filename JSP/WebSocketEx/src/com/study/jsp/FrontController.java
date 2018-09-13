package com.study.jsp;
import java.sql.Timestamp;
import com.study.jsp.BDao;
import com.study.jsp.BDto;
import com.study.jsp.command.BCommand;
import com.study.jsp.command.BContentCommand;
import com.study.jsp.command.BDeleteCommand;
import com.study.jsp.command.BJoinOkCommand;
import com.study.jsp.command.BListCommand;
import com.study.jsp.command.BLoginOkCommand;
import com.study.jsp.command.BModifyCommand;
import com.study.jsp.command.BModifyMemberCommand;
import com.study.jsp.command.BReplyCommand;
import com.study.jsp.command.BReplyViewCommand;
import com.study.jsp.command.BWriteCommand;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("*.do")
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public  FrontController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException 
	{
		actionDo(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException 
	{
		actionDo(request, response);
	}
	
	private void actionDo(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		String viewPage = null;
		BCommand command = null;
		
		String uri = request.getRequestURI();
		String conPath = request.getContextPath();
		String com = uri.substring(conPath.length());
		
		HttpSession session = request.getSession();
		int curPage = 1;
		
		if(session.getAttribute("curPage") != null) {
			curPage = (int)session.getAttribute("curPage");
		}
		String board = (String)request.getParameter("board");
		session.setAttribute("board", board);
		if (com.equals("/write_view.do")) {
			viewPage = "write_view.jsp";
		} else if (com.equals("/write.do")) {
			command = new BWriteCommand();
			command.execute(request, response);
			viewPage = "list.do?page="+curPage+"&board="+board;
		} else if (com.equals("/list.do")) {
			command = new BListCommand();
			command.execute(request, response);
			viewPage = "list.jsp";
		} else if (com.equals("/content_view.do")) {
			command = new BContentCommand();
			command.execute(request, response);
			viewPage = "content_view.jsp";
		} else if (com.equals("/modify_view.do")) {
			command = new BContentCommand();
			command.execute(request, response);
			viewPage = "modify_view.jsp";
		} else if (com.equals("/modify.do")) {
			command = new BModifyCommand();
			command.execute(request, response);
			command = new BContentCommand();
			command.execute(request, response);
			viewPage = "content_view.jsp";
		}  else if (com.equals("/delete.do")) {
			command = new BDeleteCommand();
			command.execute(request, response);
			viewPage = "list.do?page="+curPage+"&board="+board;
		} else if (com.equals("/reply_view.do")) {
			command = new BReplyViewCommand();
			command.execute(request, response);
			viewPage = "reply_view.jsp";
		} else if (com.equals("/reply.do")) {
			command = new BReplyCommand();
			command.execute(request, response);
			viewPage = "list.do?page="+curPage+"&board="+board;
		} else if (com.equals("/loginOk.do")) {
			command = new BLoginOkCommand();
			command.execute(request, response);
			return;
			//viewPage = "client.jsp";
		}else if (com.equals("/joinOk.do")) {
			command = new BJoinOkCommand();
			command.execute(request, response);
			return;
			//viewPage = "client.jsp";
		}else if (com.equals("/modifyMember.do")) {
			command = new BModifyMemberCommand();
			command.execute(request, response);
			return;
			//viewPage = "client.jsp";modifyMemberOk
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
		dispatcher.forward(request, response);
	}
}
	
