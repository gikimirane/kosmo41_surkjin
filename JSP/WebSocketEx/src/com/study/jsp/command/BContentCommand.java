package com.study.jsp.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.study.jsp.BDao;
import com.study.jsp.BDto;

public class BContentCommand implements BCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
	{
		String bId = request.getParameter("bId");
		String board = request.getParameter("board");
		BDao dao = BDao.getInstance();
		BDto dto = dao.content_view(bId, board);
		System.out.println("conte_v: " + board);
		request.setAttribute("content_view", dto);	
		request.setAttribute("board", board);	
	}

}
