package com.study.pattern04.factory_method2;

public class MySql extends Database {

	public MySql() {
		name = "MySql";
		rows = 20;
	}
	@Override
	public void connectDatabase() {
		// TODO Auto-generated method stub
		System.out.println(this.name + "에 접속");
	}

}
