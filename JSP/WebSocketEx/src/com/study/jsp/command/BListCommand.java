package com.study.jsp.command;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.study.jsp.BDao;
import com.study.jsp.BDto;
import com.study.jsp.BpageInfo;

public class BListCommand implements BCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
	{
		
		int nPage =1;
		String board="", search="", word="";
		try {
			String sPage = request.getParameter("page");
			board = request.getParameter("board");
			search = request.getParameter("search");
			word = request.getParameter("word");
			nPage = Integer.parseInt(sPage);
		} catch (Exception e) {}
		
		BDao dao = BDao.getInstance();
		BpageInfo pinfo = dao.acticlePage(nPage, board, search, word);
		request.setAttribute("page", pinfo);	
		
		nPage = pinfo.getCurPage();
		HttpSession session = request.getSession();
		session.setAttribute("curPage", nPage);
		session.setAttribute("board", board);
		session.setAttribute("search", search);
		session.setAttribute("word", word);
		System.out.println("list.do-b: " + board);	
		System.out.println("list.do-s: " + search);	
		System.out.println("list.do-w: " + word);	
		ArrayList<BDto> dtos = dao.list(nPage, board, search, word);
		request.setAttribute("list", dtos);
	}

}
