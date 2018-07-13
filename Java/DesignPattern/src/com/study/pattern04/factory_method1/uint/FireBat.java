package com.study.pattern04.factory_method1.uint;

public class FireBat extends Unit {

	public FireBat(){
		type = UnitType.FireBat;
		name = "FireBat";
		hp = 80;
		exp = 60;
		
		System.out.println(this.name + " 생성 !!!");
	}
	
	@Override
	public void attack() {
		System.out.println(this.name + " 공격 !!!");

	}

}
