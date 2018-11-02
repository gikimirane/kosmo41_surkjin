package com.study.spring;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.study.spring.dao.BDao;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	@Autowired
	private SqlSession sqlSession;
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		String formattedDate = dateFormat.format(date);
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
	@RequestMapping("/list")
	public String list(Locale locale,Model model,HttpServletRequest request) {
		BDao dao = sqlSession.getMapper(BDao.class);
		MyMethod my = new MyMethod();
		int curPage=1;
		try {
			curPage = Integer.parseInt(request.getParameter("page"));
		}catch(Exception e) {}
		int start = (curPage-1)*5+1;
		int end = (curPage-1)*5+5;
		
		int totalCount = dao.countpage();
		model.addAttribute("list", dao.listDao(end,start));
		model.addAttribute("page", my.articlePage(curPage,totalCount));
		
		return "list";
	}
	
	@RequestMapping("/content_view")
	public String content_view(Model model,HttpServletRequest request,HttpSession session) {
		
		session.setAttribute("cpage", request.getParameter("page"));
		BDao dao = sqlSession.getMapper(BDao.class);
		dao.upHit(request.getParameter("bId"));
		
		model.addAttribute("content_view", dao.contentview(request.getParameter("bId")));
		model.addAttribute("page", request.getParameter("page"));
		
		return "content_view";
	}
	
	@RequestMapping("/modify_view")
	public String modifyview(Model model,HttpServletRequest request) {
		BDao dao = sqlSession.getMapper(BDao.class);
		model.addAttribute("content_view", dao.contentview(request.getParameter("bId")));
		
		return "modify_view";
	}
	
	@RequestMapping("/modify")
	public String modify(HttpServletRequest request,HttpSession session,RedirectAttributes redirectAttributes) {
		BDao dao = sqlSession.getMapper(BDao.class);
		String bName = request.getParameter("bName");
		String bTitle = request.getParameter("bTitle");
		String bContent = request.getParameter("bContent");
		String bId = request.getParameter("bId");
		dao.modifyDao(bName, bTitle, bContent, bId);
		
		redirectAttributes.addAttribute("page", session.getAttribute("cpage"));
		return "redirect:list";
	}
	
	@RequestMapping("/delete")
	public String delete(HttpServletRequest request,HttpSession session,RedirectAttributes redirectAttributes) {
		
		BDao dao = sqlSession.getMapper(BDao.class);
		dao.deleteDao(request.getParameter("bId"));
		redirectAttributes.addAttribute("page", session.getAttribute("cpage"));
		
		return "redirect:list";
	}
	
	@RequestMapping("/reply_view")
	public String replyview(Model model, HttpServletRequest request) {
		BDao dao = sqlSession.getMapper(BDao.class);
		model.addAttribute("reply_view", dao.contentview(request.getParameter("bId")));
		return "reply_view";
	}
	
	@RequestMapping("/write_view")
	public String writeview(Model model) {
		
		return "write_view";
	}
	
	@RequestMapping("/write")
	public String write(HttpServletRequest request,HttpSession session,RedirectAttributes redirectAttributes) {
		BDao dao = sqlSession.getMapper(BDao.class);
		String bName = request.getParameter("bName");
		String bTitle = request.getParameter("bTitle");
		String bContent = request.getParameter("bContent");
		dao.writeDao(bName, bTitle, bContent);
		redirectAttributes.addAttribute("page", session.getAttribute("cpage") );
		
		return "redirect:list";
	}
	
	@RequestMapping("/reply")
	public String reply(HttpServletRequest request,HttpSession session,RedirectAttributes redirectAttributes) {
		BDao dao = sqlSession.getMapper(BDao.class);
		String bId = request.getParameter("bId");
		String bGroup = request.getParameter("bGroup");
		String bStep = request.getParameter("bStep");
		String bIndent = request.getParameter("bIndent");
		String bName = request.getParameter("bName");
		String bTitle = request.getParameter("bTitle");
		String bContent = request.getParameter("bContent");
		
		//step 이 늘어나지 않아!
		dao.replyShape(bGroup, bStep);
		dao.replyDao(bName, bTitle, bContent, bGroup, bStep, bIndent);
		
		redirectAttributes.addAttribute("page", session.getAttribute("cpage"));
		
		return "redirect:list";
	}
}
