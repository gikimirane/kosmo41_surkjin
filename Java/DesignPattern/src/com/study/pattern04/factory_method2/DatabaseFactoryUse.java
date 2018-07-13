package com.study.pattern04.factory_method2;

public class DatabaseFactoryUse extends DatabaseFactory {

	public DBType dbType = DBType.MySql;
	
	@Override
	public Database setDatabase() {
		// 사정에 의해 어떤 DB를 다시 사용예측 불가
		//재사용시 대비
		
		if(dbType == DBType.MySql) {
			System.out.println("MySql use....");
			return new MySql();
		}
		else if(dbType == DBType.Oracle) {
			System.out.println("Oracle use....");
			return new Oracle();
		}else if(dbType == DBType.Informix) {
			System.out.println("Informix use....");
			return new Informix();
		}
		return null;
	}

}
