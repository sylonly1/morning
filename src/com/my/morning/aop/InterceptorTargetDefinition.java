package com.my.morning.aop;

import java.lang.reflect.Method;

public class InterceptorTargetDefinition {
	private Class<?> klass;
	private Method method;
	
	public InterceptorTargetDefinition() {
	}
	
	public InterceptorTargetDefinition(Class<?> klass, Method method) {
		this.klass = klass;
		this.method = method;
	}
	
	public Class<?> getKlass() {
		return klass;
	}
	
	public void setKlass(Class<?> klass) {
		this.klass = klass;
	}
	
	public Method getMethod() {
		return method;
	}
	
	public void setMethod(Method method) {
		this.method = method;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((klass == null) ? 0 : klass.hashCode());
		result = prime * result + ((method == null) ? 0 : method.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InterceptorTargetDefinition other = (InterceptorTargetDefinition) obj;
		if (klass == null) {
			if (other.klass != null)
				return false;
		} else if (!klass.equals(other.klass))
			return false;
		if (method == null) {
			if (other.method != null)
				return false;
		} else if (!method.equals(other.method))
			return false;
		return true;
	}	
}
