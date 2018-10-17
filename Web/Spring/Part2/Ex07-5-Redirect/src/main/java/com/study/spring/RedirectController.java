package com.study.spring;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class RedirectController {
	
	
	@RequestMapping("studentConfirm")
	public String studentRedirect(HttpServletRequest httpServletRequest) {
		
		String id = httpServletRequest.getParameter("id");
		if(id.equals("abc")){
				return "redirect:studentOk";
		}
		
		return "redirect:studentNg";
	}
	
	@RequestMapping("studentOk")
	public String studentOk(Model model) {
		return "student/studentOk";
	}
	
	@RequestMapping("studentNg")
	public String studentNg(Model model) {
		return "student/studentNg";
	}
}
