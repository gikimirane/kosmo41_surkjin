package com.study.pattern04.factory_method1.uint;

enum UnitType{
	Marine,
	FireBat
}

//The 'Product' abstract class

public abstract class Unit {
	protected UnitType type;
	protected String name;
	protected int hp;
	protected int exp;
	public abstract void attack();
}
