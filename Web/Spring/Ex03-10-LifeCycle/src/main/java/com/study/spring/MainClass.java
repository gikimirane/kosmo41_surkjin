package com.study.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class MainClass {

	public static void main(String[] args) {
		
		
		GenericXmlApplicationContext context = 
				new GenericXmlApplicationContext();
		
		context.load("classpath:beans.xml");
		context.refresh();
		
		Student student = context.getBean("student", Student.class);
	
		System.out.println("이름 : " + student.getName());
		System.out.println("나이 : " + student.getAge());
		context.close();
	}
}
