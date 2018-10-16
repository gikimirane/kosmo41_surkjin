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
	
//	@Pointcut("execution(public void get*(..))")            //pubic void getMethod
//	@Pointcut("execution(* com.study.spring.*.*())")		//com.study.spring의 파라미터 없는 모두 
//	@Pointcut("execution(* com.study.spring..*.*())")		//com.study.spring와 하위의 파라미터 없는 모두 
//	@Pointcut("execution(* com.study.spring.Worker.*.*())") //com.study.spring.Worker안의 모두 
	
//	@Pointcut("within(com.study.spring.*)")					//com.study.spring안의 모두
//	@Pointcut("within(com.study.spring..*)")				//com.study.spring와 하위 모두
//	@Pointcut("within(com.study.spring.Worker)")			//com.study.spring.Worker 모두
	
//	@Pointcut("bean(student)")								//student 빈에만 적용
	
	@Pointcut("bean(*ker)")									//~ker로 끝나는 빈에만 적용
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

	@Before("pointcutMethod()")
	public void beforeAdvice(JoinPoint joinpoint) {
		System.out.println("beforeAdvice()");
	}
	
	@AfterReturning("pointcutMethod()")
	public void afterReturningAdvice() {
		System.out.println("afterReturningAdvice()");
	}
	
	@AfterThrowing("pointcutMethod()")
	public void afterThrowingAdvice() {
		System.out.println("afterThrowingAdvice()");
	}
	
	@After("pointcutMethod()")
	public void afterAdvice() {
		System.out.println("afterAdvice()");
	}
}
