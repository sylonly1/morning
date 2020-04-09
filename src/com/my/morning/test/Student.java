package com.my.morning.test;

import com.my.morning.aop.BeanFactory;
import com.my.morning.aop.annotion.Aspect;
import com.my.morning.aop.annotion.Autowired;
import com.my.morning.aop.annotion.Component;

@Component
public class Student {
	private String name;
	private int Id;
	@Autowired
	private ClassA a;
	
	public Student() {
		try {
			a = BeanFactory.getBean(ClassA.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	
	public String fun(int num) {
//		System.out.println("执行Student中的fun()方法:" + num + " " +"ClassA" + a);
		return "fun";
	}
}
