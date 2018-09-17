package com.study.jsp.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.study.jsp.BDao;
import com.study.jsp.BDto;

public class BReplyViewCommand implements BCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
	{
		String bId = request.getParameter("bId");
		String brd = request.getParameter("board");
		BDao dao = BDao.getInstance();
		BDto dto = dao.reply_view(bId, brd);
		
		request.setAttribute("reply_view", dto);	
	}

}
