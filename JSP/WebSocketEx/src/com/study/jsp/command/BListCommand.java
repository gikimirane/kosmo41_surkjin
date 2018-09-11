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
		String board="";
		try {
			String sPage = request.getParameter("page");
			nPage = Integer.parseInt(sPage);
			board = request.getParameter("board");
		} catch (Exception e) {}
		
		BDao dao = BDao.getInstance();
		BpageInfo pinfo = dao.acticlePage(nPage, board);
		request.setAttribute("page", pinfo);	
		
		nPage = pinfo.getCurPage();
		
		HttpSession session = request.getSession();
		session.setAttribute("curPage", nPage);
		session.setAttribute("board", board);
		System.out.println("list.do: " + board);	
		ArrayList<BDto> dtos = dao.list(nPage, board);
		request.setAttribute("list", dtos);
	}

}
