package com.study.spring.command;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import com.study.spring.dao.IDao;

@Component("reply")
public class BReplyCommand implements BCommand {

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public void execute(HttpServletRequest request, Model model)
	{
//		Map<String, Object>map = model.asMap();
//		HttpServletRequest request = (HttpServletRequest)map.get("request");
		
		String bId = request.getParameter("bId");
		String bName = request.getParameter("bName");
		String bTitle = request.getParameter("bTitle");
		String bContent = request.getParameter("bContent");
		int bGroup = Integer.parseInt(request.getParameter("bGroup"));
		int bStep = Integer.parseInt(request.getParameter("bStep"));
		int bIndent = Integer.parseInt(request.getParameter("bIndent"));
		
		model.addAttribute("curPage", request.getParameter("page"));
		model.addAttribute("page", request.getParameter("page"));
	
		IDao dao = sqlSession.getMapper(IDao.class);
		dao.replyShape(bGroup, bStep);
		dao.reply(bName, bTitle, bContent, bGroup, bStep, bIndent);
	}

}
