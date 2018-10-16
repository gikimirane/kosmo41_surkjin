package com.study.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class MainClass {

	public static void main(String[] args) {
		
		
		GenericXmlApplicationContext ctx = 
				new GenericXmlApplicationContext();
		
		ctx.load("classpath:beans.xml");
		ctx.refresh();
		ctx.close();
	}
}
