package com.study.pattern03.strategy2;

public class GameCharacter {
	//접근점(각종 무기의 단일통로)
	private Weapon weapon;
	
	//무기교환이 가능하게
	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}

	//기능 사용
	public void fire() {
		if(weapon == null) {
			System.out.println("무기를 착용하세요");
		}else {
			weapon.shoot();
		}
	}
}
