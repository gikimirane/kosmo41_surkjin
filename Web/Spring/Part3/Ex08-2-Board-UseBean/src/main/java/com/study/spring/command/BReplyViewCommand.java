package com.study.spring.command;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import com.study.spring.dao.BDao;
import com.study.spring.dto.BDto;

public class BReplyViewCommand implements BCommand {

	@Override
	public void execute(Model model)
	{
		Map<String, Object>map = model.asMap();
		HttpServletRequest request = (HttpServletRequest)map.get("request");
		String bId = request.getParameter("bId");
		BDao dao = BDao.getInstance();
		BDto dto = dao.reply_view(bId);
		
		request.setAttribute("reply_view", dto);	
	}

}
