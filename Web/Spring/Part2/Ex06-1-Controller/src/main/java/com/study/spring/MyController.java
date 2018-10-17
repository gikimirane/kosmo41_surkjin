package com.study.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MyController {
	
	private static final Logger logger = LoggerFactory.getLogger(MyController.class);
	
	@RequestMapping("/board/view")
	public String view() {
			
		return "/board/view";
	}
	
	@RequestMapping("/board/content")
	public String content(Model model) {
			
		model.addAttribute("id", 30);
		return "/board/content";
	}
	
	@RequestMapping("/board/reply")
	public ModelAndView reply() {
			
		ModelAndView mv = new ModelAndView();
		mv.addObject("id", 30);
		mv.setViewName("/board/reply");
		return mv;
	}
}
