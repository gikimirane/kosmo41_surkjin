package com.study.pattern.factory1;

public class Firebat implements Unit {

	 public Firebat() {
		System.out.println("파이어뱃 생성!!!");	
		}
	
	@Override
	public void move() {
		System.out.println("파이어뱃 이동!!!");	

	}

}
