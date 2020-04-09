package com.my.morning.aop;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class BeanMethodDefination {
	private Method method;
	private Class<?> returnType;
	private Parameter[] parameters;
	private Object object;
	private String nick;
	
	public BeanMethodDefination() {
	}
	
	public BeanMethodDefination(Method method, Class<?> returnType, 
			Parameter[] parameters, Object object, String nick) {
		this.method = method;
		this.returnType = returnType;
		this.parameters = parameters;
		this.object = object;
		this.nick = nick;
	}

	public Method getMethod() {
		return method;
	}

	public Class<?> getReturnType() {
		return returnType;
	}

	public Parameter[] getParameters() {
		return parameters;
	}

	public Object getObject() {
		return object;
	}

	public String getNick() {
		return nick;
	}
	
}
