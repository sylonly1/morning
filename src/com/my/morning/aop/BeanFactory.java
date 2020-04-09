package com.my.morning.aop;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

import com.mec.util.PackageScanner;
import com.my.morning.aop.annotion.Autowired;
import com.my.morning.aop.annotion.Bean;
import com.my.morning.aop.annotion.Component;

public class BeanFactory {
	public BeanFactory() {
	}
	
	public static void scanPackage(String packageName) {
		ProxyBeanFactory proxyBeanFactory = new ProxyBeanFactory();
		List<BeanMethodDefination> methodList = new ArrayList<>();
		
		new PackageScanner() {
			
			@Override
			public void dealClass(Class<?> klass) {
					if(!klass.isAnnotationPresent(Component.class)) {
						return;
					}
					Component component = klass.getAnnotation(Component.class);
					String name = component.name().trim();

					try {
						createBean(proxyBeanFactory, klass, null, name);
						String className = klass.getName();
						Object object = proxyBeanFactory.getMecProxy(className).getObject();
						
						Method[] methods = klass.getDeclaredMethods();
						for(Method method : methods) {
							if(!method.isAnnotationPresent(Bean.class)) {
								continue;
							}
							invokeBeanMethod(proxyBeanFactory, object, method, methodList);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
			}
		}.packageScan(packageName);
		
		for(BeanMethodDefination beanMethod : methodList) {
			Class<?> returnType = beanMethod.getReturnType();
			Object object = beanMethod.getObject();
			Method method = beanMethod.getMethod();
			Parameter[] parameters = beanMethod.getParameters();
			String name = beanMethod.getNick();
			
			try {
				Object result = invokeMuliParaMethod(method, parameters, object, proxyBeanFactory);
				createBean(proxyBeanFactory, returnType, result, name);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private static void invokeBeanMethod(ProxyBeanFactory proxyBeanFactory, 
			Object object, Method method, List<BeanMethodDefination> methodList) 
					throws Exception {
		Class<?> klass = method.getReturnType();
		if(klass.equals(void.class)) {
			return;
		}
		
		Bean bean = method.getAnnotation(Bean.class);
		String name = bean.name().trim();
		
		Parameter[] parameters = method.getParameters();
		if(parameters.length <= 0) {
			Object result = method.invoke(object);
			createBean(proxyBeanFactory, klass, result, name);
		} else {
			methodList.add(new BeanMethodDefination(
					method, klass, parameters, object, name));
		}
		
	}
	
	private static void createBean(ProxyBeanFactory proxyBeanFactory, 
			Class<?> klass, Object object, String name) throws Exception {
		if(object != null) {
			proxyBeanFactory.createCGLProxy(object);
		} else {
			proxyBeanFactory.createCGLProxy(klass);
		}
		if(name.length() > 0) {
			proxyBeanFactory.addBeanName(name, klass.getName());
		}
	}
	
	private static Object invokeMuliParaMethod(Method method, Parameter[] parameters,
			Object object, ProxyBeanFactory proxyBeanFactory) throws Exception {
		int paraCount = parameters.length;
		
		Object[] paras = new Object[paraCount];
		for(int index = 0;index < paraCount;index++) {
			Parameter parameter = parameters[index];
			String className = parameter.getType().getName();
			MecProxy mecProxy = proxyBeanFactory.getMecProxy(className);
			Object beanObject = mecProxy.getObject();
			if(beanObject != null) {
				paras[index] = beanObject;
			}
		}
		
		return method.invoke(object, paras);
	}
	
	private static void injectBean(ProxyBeanFactory proxyBeanFactory,
			Class<?> klass, Object object) {
		Field[] fields = klass.getDeclaredFields();
		for(Field field : fields) {
			if(!field.isAnnotationPresent(Autowired.class)) {
				continue;
			}
			MecProxy selfProxy = proxyBeanFactory.getMecProxy(klass.getName());
			selfProxy.setInjection(true);
			
			Autowired autowired = field.getAnnotation(Autowired.class);
			Object value = autowired.name();
//			System.out.println("name" + autowired.name());
			MecProxy fieldProxy = null;
			Class<?> beanClass = field.getType();
			if(beanClass.isPrimitive() || beanClass.equals(String.class)) {
				try {
					field.set(object, value);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}else {
				fieldProxy = proxyBeanFactory.getMecProxy(beanClass.getName());
//				System.out.println(beanClass.getName() + " ");
				if(!fieldProxy.isInjection()) {
					injectBean(proxyBeanFactory, beanClass, fieldProxy.getObject());
					try {
						field.set(object, fieldProxy.getProxy());
					} catch (IllegalArgumentException | IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			}
			field.setAccessible(true);
		}
	}
	
	public static <T> T getBean(Class<?> klass) throws Exception {
		ProxyBeanFactory proxyBeanFactory = new ProxyBeanFactory();
		
		MecProxy mecProxy = proxyBeanFactory.getMecProxy(klass.getName());
		if(!mecProxy.isInjection()) {
			injectBean(proxyBeanFactory, klass, mecProxy.getObject());
		}
		T proxyBean = mecProxy.getProxy();
		
		return proxyBean;
	}
}
