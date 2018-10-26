package com.study.spring.command;

import java.util.ArrayList;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.study.spring.dao.BpageInfo;
import com.study.spring.dao.IDao;
import com.study.spring.dto.BDto;

@Component("list")
public class BListCommand implements BCommand {

	@Autowired
	private SqlSession sqlSession;
	
	int listCount = 10;
	int pageCount = 10;
	
	@Override
	public void execute(HttpServletRequest request, Model model)
	{
//		Map<String, Object>map = model.asMap();
//		HttpServletRequest request = (HttpServletRequest)map.get("request");
		int nPage =1;
		try {
			String sPage = request.getParameter("page");
			nPage = Integer.parseInt(sPage);
		} catch (Exception e) {}
		
		IDao dao = sqlSession.getMapper(IDao.class);
			
		int totalCount = dao.acticlePage(nPage);
		
		
		int totalPage = totalCount / listCount;

		if (totalCount % listCount > 0) totalPage++;

		int myCurPage = nPage;
		if (myCurPage > totalPage)	myCurPage = totalPage;
		if (myCurPage < 1)			myCurPage = 1;

		int startPage = ((nPage - 1) / pageCount) * pageCount + 1;
		int endPage = startPage + pageCount - 1;
		if (endPage > totalPage)     endPage = totalPage;

		BpageInfo pinfo = new BpageInfo(totalCount, listCount, totalPage, 
								myCurPage, pageCount, startPage, endPage);
		
		model.addAttribute("page", pinfo);	
		
		nPage = pinfo.getCurPage();
		System.out.println("nPage: " + nPage);
		model.addAttribute("curPage", nPage);
		
		ArrayList<BDto> dtos = dao.list(nPage*listCount, (nPage-1)*listCount + 1);
		model.addAttribute("list", dtos);

	}

}
