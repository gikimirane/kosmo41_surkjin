package com.study.pattern03.strategy2;

public class Bullet implements Weapon {

	@Override
	public void shoot() {
		System.out.println("총알 공격");

	}

}
