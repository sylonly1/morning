package com.my.morning.test;

import com.my.morning.aop.InterceptorScanner;
import com.my.morning.aop.ProxyFactory;

public class AOPTest {

	public static void main(String[] args) {
		InterceptorScanner scanner = new InterceptorScanner();
		scanner.interceptorScanner("com.my.student");
		
		ProxyFactory proxy = new ProxyFactory();
		
		
	}

}
