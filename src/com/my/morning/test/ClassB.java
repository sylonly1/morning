package com.my.morning.test;

import com.my.morning.aop.annotion.Autowired;
import com.my.morning.aop.annotion.Component;

@Component
public class ClassB {
	@Autowired
	public ClassC classc;
	@Autowired(name = "我是B")
	public String name;
	
	public ClassB() {
	}

	public ClassC getClassc() {
		return classc;
	}

	public void setClassc(ClassC classc) {
		this.classc = classc;
	}

	@Override
	public String toString() {
		return "ClassB: ->" + name + classc + " ";
	}

}
