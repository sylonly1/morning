package com.my.morning.aop;

import java.lang.reflect.Method;
import java.util.List;

public class MecProxy {
	private Object object;
	private Object proxy;
	private boolean injection;
	
	public boolean isInjection() {
		return injection;
	}

	public void setInjection(boolean injection) {
		this.injection = injection;
	}

	public MecProxy() {
	}
	
	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	@SuppressWarnings("unchecked")
	public <T> T getProxy() {
		return (T) proxy;
	}

	public <T> T setProxy(T proxy) {
		this.proxy = (T) proxy;
		return proxy;
	}
	 
	public boolean doBefore(Class<?> klass, Method method, Object[] args)  {
		InterceptorFactory intercepterFactory = new InterceptorFactory();
		InterceptorTargetDefinition itd = new InterceptorTargetDefinition(klass, method);
		List<InterceptorMethodDefinition> intercepterList = 
				intercepterFactory.getBeforeInterceptor(itd);
		if(intercepterList == null) {
			return true;
		}
		for(InterceptorMethodDefinition imd : intercepterList) {
			Method intercepterMethod = imd.getMethod();
			Object intercepterObject = imd.getObject();
			boolean result = false;
			try {
				result = (Boolean) intercepterMethod.invoke(intercepterObject, args);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(result == false) {
				return false;
			}
		}
		return true;
	}
	
	public Object doAfter(Class<?> klass, Method method, Object result) {
		InterceptorFactory intercepterFactory = new InterceptorFactory();
		InterceptorTargetDefinition itd = new InterceptorTargetDefinition(klass, method);
		List<InterceptorMethodDefinition> interceptorList = intercepterFactory.getAfterInterceptor(itd);
		if(interceptorList == null) {
			return result;
		}
		for(InterceptorMethodDefinition imd : interceptorList) {
			Method interceptorMethod = imd.getMethod();
			Object interceptorObject = imd.getObject();
			try {
				result = interceptorMethod.invoke(interceptorObject, result);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	public void doException(Class<?> klass, Method method, Throwable th) {
		InterceptorFactory intercepterFactory = new InterceptorFactory();
		InterceptorTargetDefinition itd = new InterceptorTargetDefinition(klass, method);
		List<InterceptorMethodDefinition> interceptorList = intercepterFactory.getExceptionInterceptor(itd);
		if(interceptorList == null) {
			return;
		}
		for(InterceptorMethodDefinition imd : interceptorList) {
			Method intercepterMethod = imd.getMethod();
			Object intercepObject = imd.getObject();
			try {
				intercepterMethod.invoke(intercepObject, th);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
