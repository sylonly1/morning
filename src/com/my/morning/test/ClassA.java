package com.my.morning.test;

import com.my.morning.aop.annotion.Autowired;
import com.my.morning.aop.annotion.Component;

@Component
public class ClassA {
	@Autowired
	public ClassB classb;
	@Autowired(name = "我是A")
	public String name;
	
	public ClassA() {
	}

	public ClassB getClassb() {
		return classb;
	}

	public void setClassb(ClassB classb) {
		this.classb = classb;
	}

	@Override
	public String toString() {
		return "ClassA: ->" + name + classb + " ";
	}

}
