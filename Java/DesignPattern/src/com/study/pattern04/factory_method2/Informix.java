package com.study.pattern04.factory_method2;

public class Informix extends Database {
	public Informix() {
		name = "Informix";
		rows = 20;
	}
	
	@Override
	public void connectDatabase() {
		System.out.println(this.name + "에 접속");
	}

}
