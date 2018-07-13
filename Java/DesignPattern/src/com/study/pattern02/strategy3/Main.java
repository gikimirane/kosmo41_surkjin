package com.study.pattern02.strategy3;

public class Main {

	public static void main(String[] args) {
		//데이터베이스를 전략적으로 선택하여 사용
		DatabaseUse myDB = new DatabaseUse();
		myDB.connect();
		
		myDB.setDatabase(new MySql());
		myDB.connect();

		myDB.setDatabase(new Oracle());
		myDB.connect();
		
		myDB.setDatabase(new Informix());
		myDB.connect();
	}

}
