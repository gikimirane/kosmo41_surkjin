package com.study.pattern04.factory_method2;

enum DBType{
	MySql,
	Oracle,
	Informix
}

public abstract class DatabaseFactory {

	public abstract Database setDatabase();
}
