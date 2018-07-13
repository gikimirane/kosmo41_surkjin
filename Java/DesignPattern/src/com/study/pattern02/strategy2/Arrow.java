package com.study.pattern02.strategy2;

public class Arrow implements Weapon {

	@Override
	public void shoot() {
		System.out.println("화살 공격");
	}

}
