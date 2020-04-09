package com.my.morning.aop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InterceptorFactory {
	public static final Map<InterceptorTargetDefinition, List<InterceptorMethodDefinition>> beforeMap;
	public static final Map<InterceptorTargetDefinition, List<InterceptorMethodDefinition>> afterMap;
	public static final Map<InterceptorTargetDefinition, List<InterceptorMethodDefinition>> exceptionMap;
	
	static {
		beforeMap = new HashMap<>();
		afterMap = new HashMap<>();
		exceptionMap = new HashMap<>();
	}
	
	protected InterceptorFactory() {
	}
	
	private void addInterceptor(Map<InterceptorTargetDefinition, List<InterceptorMethodDefinition>> map,
			InterceptorTargetDefinition target,
			InterceptorMethodDefinition method) {
		List<InterceptorMethodDefinition> methodList = map.get(target);
		
		if(methodList == null) {
			methodList = new ArrayList<InterceptorMethodDefinition>();
			map.put(target, methodList);
		}
		methodList.add(method);
	}
	
	public void addBeforeIntercrptor(InterceptorTargetDefinition target,
			InterceptorMethodDefinition method) {
		addInterceptor(beforeMap, target, method);
	}
	
	public void addAfterInterceptor(InterceptorTargetDefinition target,
			InterceptorMethodDefinition method) {
		addInterceptor(afterMap, target, method);
	}
	
	public void addExceptionInterceptor(InterceptorTargetDefinition target,
			InterceptorMethodDefinition method) {
		addInterceptor(exceptionMap, target, method);
	}
	
	public List<InterceptorMethodDefinition> getBeforeInterceptor(InterceptorTargetDefinition target) {
		return beforeMap.get(target);
	}
	
	public List<InterceptorMethodDefinition> getAfterInterceptor(InterceptorTargetDefinition target) {
		return afterMap.get(target);
	}
	
	public List<InterceptorMethodDefinition> getExceptionInterceptor(InterceptorTargetDefinition target) {
		return exceptionMap.get(target);
	}
}
