package com.my.student;

import com.my.morning.aop.annotion.After;
import com.my.morning.aop.annotion.Aspect;
import com.my.morning.test.Student;
import com.my.morning.aop.annotion.Before;

@Aspect
public class StudentAction {

	@Before(klass = Student.class, method = "fun")
	public boolean Before(int num) {
		System.out.println("前置拦截成功,参数为:" + num);
		return true;
	}
	
	@After(klass = Student.class, method = "fun", parameterTypes = {int.class})
	public String After(String num) {
		System.out.println("后置拦截成功,yes");
		return "fun";
	}
	
}
