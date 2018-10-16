package com.study.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class HelloBeanTest {
	
	public static void main(String[] args) {
		
		//String configLocation = "classpath:beans.xml";
		
		AnnotationConfigApplicationContext context = 
				new AnnotationConfigApplicationContext(Config.class);
		
		Hello helloA = (Hello)context.getBean("hello");
		helloA.print();
		
		Hello helloB = (Hello)context.getBean("hello1");
		helloB.print();
		
		Printer printer = context.getBean("printerB", Printer.class);
		helloA.setPrinter(printer);
		helloA.print();
		
	
		System.out.println(helloA == helloB);
		
		context.close();
	}
}
