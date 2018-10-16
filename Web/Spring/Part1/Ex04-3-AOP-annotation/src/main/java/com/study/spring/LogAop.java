package com.study.spring;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class LogAop {
	
	@Pointcut("within(com.study.spring.*)")
	private void pointcutMethod() {
		
	}

	@Around("pointcutMethod()")
	public Object loggerAop(ProceedingJoinPoint joinpoint) throws Throwable {
		String signatureStr = joinpoint.getSignature().toShortString();
		System.out.println( signatureStr + " is start.");
		
		long st = System.currentTimeMillis();
		
		try {
			Object obj = joinpoint.proceed();
			return obj;
		} finally {
			long et = System.currentTimeMillis();;
			
			System.out.println( signatureStr + " is finished.");
			System.out.println( signatureStr + " 경과시간 : " + (et - st));
		}
	}

	@Before("within(com.study.spring.*)")
	public void beforeAdvice(JoinPoint joinpoint) {
		System.out.println("beforeAdvice()");
	}
	
	@AfterReturning("within(com.study.spring.*)")
	public void afterReturningAdvice() {
		System.out.println("afterReturningAdvice()");
	}
	
	@AfterThrowing("within(com.study.spring.*)")
	public void afterThrowingAdvice() {
		System.out.println("afterThrowingAdvice()");
	}
	
	@After("within(com.study.spring.*)")
	public void afterAdvice() {
		System.out.println("afterAdvice()");
	}
}
