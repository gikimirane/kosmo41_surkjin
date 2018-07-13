package com.study.pattern02.strategy3;

public class MySql extends Database {

	public MySql() {
		name = "MySql";
		rows = 20;
	}
	
	@Override
	public void connectDatabase() {
		System.out.println(name + "에 접속했습니다.");
	}

}
