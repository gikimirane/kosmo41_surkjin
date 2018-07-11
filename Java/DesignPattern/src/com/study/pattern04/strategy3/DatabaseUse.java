package com.study.pattern04.strategy3;

public class DatabaseUse {

	//접근점
	private Database db;
	
	//데이터베이스 교환가능하게
	public void setDatabase(Database db) {
		this.db = db;
	}
	//기능 사용
	public void connect() {
		
		if (db == null) {
			System.out.println("데이타베이스를 선택하세요.");
		}else {
			//Delegate 메서드 호출
			db.connectDatabase();
		}
	}
}
