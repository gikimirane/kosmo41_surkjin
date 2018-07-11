package com.study.pattern01.singleton;

public class ClassOne {
	
	public ClassOne(){
		MySingletonClass single = MySingletonClass.getInstance();
		System.out.println("ClassOne");
		
		int num =single.getI();
		System.out.println("i = " + num);
		
		single.setI(200);
		System.out.println("i = " + single.getI());
	}
}
