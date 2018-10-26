package com.study.spring.command;

import java.util.Map;
import org.apache.ibatis.session.SqlSession;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import com.study.spring.dao.IDao;
import com.study.spring.dto.BDto;

@Component("content_view")
public class BContentCommand implements BCommand {

	@Autowired
	 private SqlSession sqlSession;
	
	@Override
	public void execute(HttpServletRequest request, Model model)
	{
//		Map<String, Object> map = model.asMap();
//		HttpServletRequest request = (HttpServletRequest)map.get("request");
		
		String bId = request.getParameter("bId");

		IDao dao = sqlSession.getMapper(IDao.class);
		
		dao.upHit(bId);
		BDto dto = dao.content_view(bId);
		
		model.addAttribute("content_view", dto);
		model.addAttribute("curPage", request.getParameter("page"));	
	}

}
