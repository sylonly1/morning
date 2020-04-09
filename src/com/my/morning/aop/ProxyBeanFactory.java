package com.my.morning.aop;

import java.util.HashMap;
import java.util.Map;

public class ProxyBeanFactory {
	private static final Map<String, String> beanNameMap;
	private static final Map<String, MecProxy> beanMap;
	
	static {
		beanMap = new HashMap<>();
		beanNameMap = new HashMap<>();
	}
	
	public ProxyBeanFactory() {
		
	}
	
	public void addBeanName(String beanName, String className) 
			throws Exception {
		String orgClassName = beanNameMap.get(beanName);
		if(orgClassName != null){
			//TODO 抛出名称重复异常
		}
		beanNameMap.put(beanName, className);
	}
	
	public String getBeanClassName(String beanName) {
		return beanNameMap.get(beanName);
	}
	
	public void createCGLProxy(Object object) throws Exception {
		cglProxy(object, object.getClass());
	}
	
	public void createCGLProxy(Class<?> klass) throws Exception {
		cglProxy(klass.newInstance(), klass);
	}
	
	public MecProxy getMecProxy(String className) {
		return beanMap.get(className);
	}
	
	public <T> T getProxy(Class<?> klass) {
		MecProxy mecProxy = beanMap.get(klass.getName());
		if(mecProxy == null) {
			return null;
		}
		return mecProxy.getProxy();
	}
	
	private void cglProxy(Object object, Class<?> klass) throws Exception {
		String className = klass.getName();
		MecProxy mecProxy = beanMap.get(className);
		if(mecProxy != null) {
			//TODO 抛出异常
			return;
		}
		
		ProxyFactory proxyFactory = new ProxyFactory();
		proxyFactory.getCGLProxy(object, klass);
		beanMap.put(className,proxyFactory.getMecProxy());
	}
	
	public <T> T createJDKProxy(Object object) throws Exception {
		return jdkProxy(object, object.getClass());
	}
	 
	public <T> T createJDKProxy(Class<?> klass) throws Exception {
		return jdkProxy(klass.newInstance(), klass);
	}
	
	private <T> T jdkProxy(Object object, Class<?> klass) {
		String className = klass.getName();
		MecProxy mecProxy = beanMap.get(className);
		if(mecProxy != null) {
			return mecProxy.getProxy();
		}
		
		ProxyFactory proxyFactory = new ProxyFactory();
		T proxy = proxyFactory.getJDKProxy(object, klass);
		beanMap.put(className, proxyFactory.getMecProxy());
		
		return proxy;
	}
}
