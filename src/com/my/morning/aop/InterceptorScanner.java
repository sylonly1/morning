package com.my.morning.aop;

import java.lang.reflect.Method;

import com.mec.util.PackageScanner;
import com.my.morning.aop.annotion.After;
import com.my.morning.aop.annotion.Aspect;
import com.my.morning.aop.annotion.Before;

public class InterceptorScanner {
	public InterceptorScanner() {
	}
	
	public void interceptorScanner(String packagename) {
		new PackageScanner() {
			
			@Override
			public void dealClass(Class<?> klass) {
				if(!klass.isAnnotationPresent(Aspect.class))
					return;
				try {
					Object object = klass.newInstance();
					Method[] methods = klass.getDeclaredMethods();
					for(Method method : methods) {
						if(method.isAnnotationPresent(Before.class)) {
							Before before = method.getAnnotation(Before.class);
							dealBeforeInterceptor(klass, object, method, before);
						} else if(method.isAnnotationPresent(After.class)) {
							After after = method.getAnnotation(After.class);
							dealAfterInterceptor(klass, object, method, after);
						} else if(method.isAnnotationPresent(com.my.morning.aop.annotion.Exception.class)) {
							com.my.morning.aop.annotion.Exception exception = method.getAnnotation(com.my.morning.aop.annotion.Exception.class);
							dealExceptionInterceptor(klass, object, method, exception);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.packageScan(packagename);
	}
	
	private static void dealBeforeInterceptor(Class<?> klass, Object object,
			Method method, Before before) throws Exception {
		Class<?> returnType = method.getReturnType();
		if(!returnType.equals(boolean.class)) {
			System.out.println("前置拦截(" + method + ")返回值类型只能是boolean");
		}
			Class<?> targetKlass = before.klass();
			Method targetMethod = targetKlass.getMethod(before.method(), method.getParameterTypes());
			
			InterceptorMethodDefinition imd = new InterceptorMethodDefinition(
					klass, method, object);
			InterceptorTargetDefinition itd = new InterceptorTargetDefinition(targetKlass, targetMethod);
			InterceptorFactory interceptorFactory = new InterceptorFactory();
			interceptorFactory.addBeforeIntercrptor(itd, imd);
//			System.out.println("Scanner" + itd.getKlass()+ " " + itd.getMethod());
	}
	
	private static void dealAfterInterceptor(Class<?> klass, Object object,
			Method method, After after) throws Exception {
		Class<?> intercepterReturnType = method.getReturnType();
		Class<?> targetKlass = after.klass();
		
		Method targetMethod = targetKlass.getMethod(after.method(), after.parameterTypes());
		Class<?> targetReturnType = targetMethod.getReturnType();
		
		if(!intercepterReturnType.equals(targetReturnType)) {
			System.out.println("后置拦截(" + method + ")返回值类型不是" + targetReturnType);
		}
			
			InterceptorMethodDefinition imd = new InterceptorMethodDefinition(
					klass, method, object);
			InterceptorTargetDefinition itd = new InterceptorTargetDefinition(targetKlass, targetMethod);
			InterceptorFactory interceptorFactory = new InterceptorFactory();
			interceptorFactory.addAfterInterceptor(itd, imd);
//			System.out.println(interceptorFactory);
	}
	
	private static void dealExceptionInterceptor(Class<?> klass, Object object,
			Method method, com.my.morning.aop.annotion.Exception exception) throws Exception {
		Class<?> returnType = method.getReturnType();
		if(!returnType .equals(void.class)) {
			System.out.println("异常拦截器(" + method + ")返回值只能是void");
		}
		Class<?> targetKlass = exception.klass();
		Method targetMethod = targetKlass.getMethod(exception.method(), method.getParameterTypes());
		Class<?>[] paraType = method.getParameterTypes();
		if(paraType.length != 1 || !paraType[0].equals(Throwable.class)) {
			System.out.println("有异常");
		}
		
		InterceptorMethodDefinition imd = new InterceptorMethodDefinition(
				klass, method, object);
		InterceptorTargetDefinition itd = new InterceptorTargetDefinition(targetKlass, targetMethod);
		InterceptorFactory interceptorFactory = new InterceptorFactory();
		interceptorFactory.addExceptionInterceptor(itd, imd);
	}
}
