package com.study.spring;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class Hello {
	
	private List<String> names;
	private Set<Integer> nums;
	private Map<String, Integer> ages;
	
	public List<String> getNames() {
		return names;
	}
	public Set<Integer> getNums() {
		return nums;
	}
	public Map<String, Integer> getAges() {
		return ages;
	}
	public void setNames(List<String> names) {
		this.names = names;
	}
	public void setNums(Set<Integer> nums) {
		this.nums = nums;
	}
	public void setAges(Map<String, Integer> ages) {
		this.ages = ages;
	}
	
	
}
