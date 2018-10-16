package com.study.spring;

public class Student {
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
	
	public void getStudentInfo() {
		System.out.println("이름 : " + getName());
		System.out.println("나이 : " + getAge());
		
		try {
			System.out.println(10/0);
		}catch(Exception e) {
		}
	}
}
