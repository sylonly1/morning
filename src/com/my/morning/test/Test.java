package com.my.morning.test;

import com.my.morning.aop.BeanFactory;
import com.my.morning.aop.InterceptorScanner;
import com.my.morning.aop.ProxyBeanFactory;
import com.my.morning.aop.ProxyFactory;

public class Test {

	public static void main(String[] args) {
//		BeanFactory.scanPackage("com.my.morning.test");
//		try {
//			ClassA a = BeanFactory.getBean(ClassA.class);
//			System.out.println(a.toString());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		BeanFactory.scanPackage("com.my.morning.test");
		InterceptorScanner scanner = new InterceptorScanner();
		scanner.interceptorScanner("com.my.student");
			
		ProxyFactory proxy = new ProxyFactory();
		
		Student user = proxy.getCGLProxy(new Student(), Student.class);
		user.fun(20);
	}

}
