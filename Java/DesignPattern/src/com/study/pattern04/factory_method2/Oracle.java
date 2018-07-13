package com.study.pattern04.factory_method2;

public class Oracle extends Database {

	public Oracle() {
		name = "Oracle";
		rows = 20;
	}
	
	@Override
	public void connectDatabase() {
		System.out.println(this.name + "에 접속");

	}

}
