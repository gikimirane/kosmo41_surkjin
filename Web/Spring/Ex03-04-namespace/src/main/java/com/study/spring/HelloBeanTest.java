package com.study.spring;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class HelloBeanTest {
	
	public static void main(String[] args) {
		
		String configLocation = "classpath:beans.xml";
		
		ApplicationContext context = 
				new GenericXmlApplicationContext(configLocation);
		
		Hello hello = (Hello)context.getBean("hello");
		hello.print();
		
		Printer printer = context.getBean("printerB", Printer.class);
		hello.setPrinter(printer);
		hello.print();
		
		Hello hello2 = context.getBean("hello", Hello.class);
		System.out.println(hello == hello2);
		
		//context.close();
	}
}
