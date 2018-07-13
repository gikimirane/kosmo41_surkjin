package com.study.pattern04.factory_method2;

public class Main {

	public static void main(String[] args) {

		DatabaseFactory factory = new DatabaseFactoryUse();
		
		Database db = factory.setDatabase();
		
		db.connectDatabase();
		
		db.insertData();
		db.selectData();
	}

}
