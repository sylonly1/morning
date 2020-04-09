package com.my.morning.test;

import com.my.morning.aop.annotion.Autowired;
import com.my.morning.aop.annotion.Component;

@Component
public class ClassC {
	@Autowired
	public ClassA classa;
	@Autowired(name = "我是C")
	public String name;
	
	public ClassC() {
	}

	public ClassA getClassa() {
		return classa;
	}

	public void setClassa(ClassA classa) {
		this.classa = classa;
	}

	@Override
	public String toString() {
		return "ClassC: ->" + name + " ";
	}

}
