package com.study.spring.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.study.spring.command.BCommand;
import com.study.spring.command.BContentCommand;
import com.study.spring.command.BDeleteCommand;
import com.study.spring.command.BListCommand;
import com.study.spring.command.BModifyCommand;
import com.study.spring.command.BReplyCommand;
import com.study.spring.command.BReplyViewCommand;
import com.study.spring.command.BWriteCommand;

@Controller
public class BController {
	
	@Autowired
	private ApplicationContext context;
	
	BCommand command = null;
	
	@RequestMapping("/list")
	public String list(Model model) {
		System.out.println("list()");
		//command = new BListCommand();
		command = context.getBean(BListCommand.class);
		command.execute(model);
		return "list";
	}
	
	@RequestMapping("/write_view")
	public String write_view(Model model) {
		System.out.println("write_view()");
		
		return "write_view";
	}
	
	@RequestMapping("/write")
	public String write(HttpServletRequest request, Model model) {
		System.out.println("write()");
		model.addAttribute("request", request);
//		command = new BWriteCommand();
		command = (BWriteCommand)context.getBean("writeHandler");
		command.execute(model);
		return "redirect:list";
	}
	
	@RequestMapping("/content_view")
	public String content_view(HttpServletRequest request, Model model) {
		System.out.println("content_view()");
		
		model.addAttribute("request", request);
//		command = new BContentCommand();
		command = (BCommand) context.getBean("contentHandler");
		command.execute(model);
		return "content_view";
	}
	
	@RequestMapping("/modify_view")
	public String modify_view(HttpServletRequest request, Model model) {
		System.out.println("modify_view()");
		
		model.addAttribute("request", request);
//		command = new BContentCommand();
		command = context.getBean(BContentCommand.class);
		command.execute(model);
		return "modify_view";
	}
	
	@RequestMapping(value="/modify", method=RequestMethod.POST)
	public String modify(HttpServletRequest request, Model model) {
		System.out.println("modify()");
		model.addAttribute("request", request);
//		command = new BModifyCommand();
		command = context.getBean(BModifyCommand.class);
		command.execute(model);
		return "redirect:list";
	}
	
	@RequestMapping("/delete")
	public String delete(HttpServletRequest request, Model model) {
		System.out.println("delete()");
		model.addAttribute("request", request);
//		command = new BDeleteCommand();
		command = (BDeleteCommand)context.getBean("deleteHandler");
		command.execute(model);
		return "redirect:list";
	}
	
	@RequestMapping("/reply_view")
	public String reply_view(HttpServletRequest request, Model model) {
		System.out.println("reply_view()");
		model.addAttribute("request", request);
//		command = new BReplyViewCommand();
		command = context.getBean(BReplyViewCommand.class);
		command.execute(model);
		return "reply_view";
	}
	
	@RequestMapping("/reply")
	public String reply(HttpServletRequest request, Model model) {
		System.out.println("reply()");
		model.addAttribute("request", request);
//		command = new BReplyCommand();
		command = context.getBean(BReplyCommand.class);
		command.execute(model);
		return "redirect:list";
	}
	
}
