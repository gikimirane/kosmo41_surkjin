package com.study.pattern01.singleton;

public class MySingletonClass {

//	public static MySingletonClass sc = new MySingletonClass();
	private static MySingletonClass sc = null;
	private int i=0;
		
	private MySingletonClass() {
	}

	public static MySingletonClass getInstance() {
		if (sc==null) {
			sc = new MySingletonClass();
		}
		return sc;
	}
	
	public int getI() {
		return i;
	}
		
	public void setI(int i) {
		this.i = i;
	}
}
