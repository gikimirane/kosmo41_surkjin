package com.study.pattern04.strategy3;

public class Oracle extends Database {

	public Oracle() {
		name = "Oracle";
		rows = 10;
	}
	
	@Override
	public void connectDatabase() {
		System.out.println(name + "에 접속했습니다.");
	}

}
