package com.study.pattern02.strategy1;

public class BObj {
	
	AInteface ainter;
	public  BObj() {
		ainter = new AImplement();
	}
	
	void someFunc() {
		//다음기능이 필요합니다.  A 님만들어주세요
		//기능구현 위임 - Delegate했다
//		System.out.println("AAA");
//		System.out.println("AAA");
//		System.out.println("AAA");

		//Delegate 사용
//		ainter.funcA(;)
	}
}
