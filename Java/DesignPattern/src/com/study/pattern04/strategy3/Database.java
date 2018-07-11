package com.study.pattern04.strategy3;

public abstract class Database {

	public String name;
	public double rows;
	
	public abstract void connectDatabase();

}
