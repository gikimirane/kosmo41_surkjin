package com.study.spring;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/mc/simple")
public class SimpleConvertController {
	
	@RequestMapping(method = RequestMethod.GET)
	public String simpleForm() {
				
		return "/mc/simple";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public String simple(@RequestBody String body) {
				
		return body;
	}
}
