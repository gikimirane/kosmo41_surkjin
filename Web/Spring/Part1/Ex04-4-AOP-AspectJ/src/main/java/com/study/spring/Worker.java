package com.study.spring;

public class Worker {
	private String name;
	private int age;

	public String getName() {
		return name;
	}

	public int getAge() {
		return age;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAge(int age) {
		this.age = age;
	}
	
	public void getWorkerInfo() {
		System.out.println("이름 : " + getName());
		System.out.println("나이 : " + getAge());
	}
}
