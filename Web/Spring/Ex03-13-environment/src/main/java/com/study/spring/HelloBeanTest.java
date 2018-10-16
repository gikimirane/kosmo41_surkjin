package com.study.spring;
import java.io.IOException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.io.support.ResourcePropertySource;

public class HelloBeanTest {
	
	public static void main(String[] args) {
		
		//String configLocation = "classpath:beans.xml";
		
		ConfigurableApplicationContext context1 = new GenericXmlApplicationContext();
		ConfigurableEnvironment env = context1.getEnvironment();
		MutablePropertySources propertySource = env.getPropertySources();
		
		try {
			propertySource.addLast(new ResourcePropertySource("classpath:config/database.properties"));
			System.out.println(env.getProperty("db.username"));
			System.out.println(env.getProperty("db.password"));
		} catch(IOException e) {}
		
		GenericXmlApplicationContext context2 = (GenericXmlApplicationContext) context1;
		context2.load("classpath:beans.xml");
		context2.refresh();
		
		Hello hello = (Hello)context2.getBean("hello");
		
		System.out.println(hello.getDriverClass());
		System.out.println(hello.getUrl());
		System.out.println(hello.getUsername());
		System.out.println(hello.getPassword());
		
		System.out.println("-------------------");
		
		System.out.println(env.getProperty("db.username"));
		System.out.println(env.getProperty("db.password"));
		
		context2.close();
		context1.close();
	}
}
