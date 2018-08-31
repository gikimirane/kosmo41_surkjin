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
		try {
			String sPage = request.getParameter("page");
			nPage = Integer.parseInt(sPage);
		} catch (Exception e) {}
		
		BDao dao = BDao.getInstance();
		BpageInfo pinfo = dao.acticlePage(nPage);
		request.setAttribute("page", pinfo);	
		
		nPage = pinfo.getCurPage();
		
		HttpSession session = request.getSession();
		session.setAttribute("curPage", nPage);
		
		
		ArrayList<BDto> dtos = dao.list(nPage);
		request.setAttribute("list", dtos);
	}

}
