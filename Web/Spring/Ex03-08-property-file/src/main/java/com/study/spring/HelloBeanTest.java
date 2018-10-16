package com.study.spring;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class HelloBeanTest {
	
	public static void main(String[] args) {
		
		String configLocation = "classpath:beans.xml";
		
		ApplicationContext context = 
				new GenericXmlApplicationContext(configLocation);
		
		Hello hello = (Hello)context.getBean("hello");
		
		System.out.println(hello.getDriverClass());
		System.out.println(hello.getUrl());
		System.out.println(hello.getUsername());
		System.out.println(hello.getPassword());
		
		//context.close();
	}
}
