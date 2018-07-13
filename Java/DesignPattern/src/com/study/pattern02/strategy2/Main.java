package com.study.pattern02.strategy2;

public class Main {

	public static void main(String[] args) {

		GameCharacter character = new GameCharacter();
		character.fire();
		
		//선택상황에 따라 전략적으로 무기를 선택
		character.setWeapon(new Arrow());
		character.fire();
		
		character.setWeapon(new Bullet());
		character.fire();
		
		character.setWeapon(new Missile());
		character.fire();
	}

}
