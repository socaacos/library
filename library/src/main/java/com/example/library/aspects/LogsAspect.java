package com.example.library.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class LogsAspect {
	
	@Pointcut("execution(* com.example.library.controllers.BookController.*(..))")
	public void controllerRoutes() 
	{}	
	@Around("controllerRoutes()")
	public Object logController(ProceedingJoinPoint jp) throws Throwable
	{
		return handleAspect(jp, "controller");	
	}
	
	@Pointcut("execution(* com.example.library.services.BookService.*(..))")
	public void serviceFunctions() 
	{}
	@Around("serviceFunctions()")
	public Object logService(ProceedingJoinPoint jp) throws Throwable
	{
		return handleAspect(jp, "service");
	}
	
	@Pointcut("execution(* com.example.library.services.BookService.*(..))")
	public void repository() 
	{}
	@Around("repository()")
	public Object logRepository(ProceedingJoinPoint jp) throws Throwable
	{
		return handleAspect(jp, "repository");
	}
	
	private Object handleAspect(ProceedingJoinPoint jp, String description) throws Throwable 
	{
		log.info("Started " + description + " function: "+jp.toString());
		log.info("The function which was called in " + description + " is: " + jp.getSignature().getName());
		Object[] args = jp.getArgs();
		log.info("The number of arguments in function is: " + args.length);
		String logInfo = jp.getSignature().getName() + " has arguments : ";
		for (Object object : args){
			if(object == null)
				continue;
			logInfo += object.toString();
		}
		
		log.info(logInfo);
		
		Object retVal = jp.proceed();
		log.info("Finished " + description + " : " + jp.toString());
		//log.info( "Return value in " + description + " is: "+retVal.toString());
		return retVal;
	}
}
