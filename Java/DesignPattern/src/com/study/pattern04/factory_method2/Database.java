package com.study.pattern04.factory_method2;

public abstract class Database {
	public String name;
	public double rows;
	
	public abstract void connectDatabase();
	
	public void insertData() {
		System.out.println(name + "에 데이터를 추가");
	}
	
	public void selectData() {
		System.out.println(name + "에서 데이터를 " + rows + " 개 조회");
	}
}
