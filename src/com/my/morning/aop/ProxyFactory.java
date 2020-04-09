package com.my.morning.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class ProxyFactory {
	private MecProxy mecProxy;
	
	public ProxyFactory() {
		// TODO Auto-generated constructor stub
	}

	public MecProxy getMecProxy() {
		return mecProxy;
	}
	
	public <T> T getCGLProxy(Object object, Class<?> klass) {
		T proxy = cglProxy(object, klass);
		mecProxy = new MecProxy();
		mecProxy.setObject(object);
		mecProxy.setProxy(proxy);
		
		return (T) proxy;
	}
	
	public <T> T getJDKProxy(Object object, Class<?> klass) {
		T proxy = jdkProxy(object, klass);
		mecProxy = new MecProxy();
		mecProxy.setObject(object);
		mecProxy.setProxy(proxy);
		
		return (T) proxy;
		
	}
	
	@SuppressWarnings("unchecked")
	private <T> T cglProxy(Object object, Class<?> klass) {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(klass);
		MethodInterceptor methodInterceptor = new MethodInterceptor() {
			
			@Override
			public Object intercept(Object arg0, Method arg1, Object[] arg2,
					MethodProxy arg3) throws Throwable {
				return doInvoker(klass, object, arg1, arg2);
			}
		};
		enhancer.setCallback(methodInterceptor);
		
		return (T) enhancer.create();
		
	}
	
	@SuppressWarnings("unchecked")
	private <T> T jdkProxy(Object object, Class<?> klass) {
		ClassLoader classLoader = klass.getClassLoader();
		Class<?>[] interfaces = klass.getInterfaces();
		
		InvocationHandler invocationHandler = new InvocationHandler() {
			
			@Override
			public Object invoke(Object proxy, Method method, Object[] args)
					throws Throwable {
				// TODO 同样是搞事情
				return null;
			}
		};
		
		return (T) Proxy.newProxyInstance(classLoader, interfaces, invocationHandler);
	}
	
	private Object doInvoker(Class<?> klass, Object object, Method method, Object[] args) {
		Object result = null;
		if(mecProxy.doBefore(klass, method, args) == false) {
			return null;
		}
	//	 System.out.println("object " + object + " " + "args " + args);
		 try {
			result = method.invoke(object, args);
			mecProxy.doAfter(klass, method, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		 return result;
	}
}
