package com.revature.bam.logging;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * 
 * @author Allan Poindexter / Batch : 1712_dec11th_Java_Steve
 * 
 * Each method call, section off a portion in the log and display the runtime.
 * For readability's sake Controller calls will be separated by three lines.
 * Lastly, print the runtime.
 */
@Aspect
@Component
public class ServiceAndControllerLogger {

	private static final Logger logger = LogManager.getLogger(ServiceAndControllerLogger.class);
	
	@Before("execution (* com.revature.bam.controller.*.*(..))")
	public void beforeControllerMethod(JoinPoint jp) {
		logger.debug("\r\n\n\n");
		logger.debug("START CONTROLLER - " + jp.getSignature().getDeclaringTypeName() + "."  + jp.getSignature().getName() + "()");
	}
	
	@After("execution (* com.revature.bam.controller.*.*(..))")
	public void afterControllerMethod(JoinPoint jp) {
		logger.debug("END CONTROLLER - " + jp.getSignature().getDeclaringTypeName() + "."  + jp.getSignature().getName() + "()");
		logger.debug("\r\n\n\n");
	}
	
	
	@Before("execution (* com.revature.bam.service.*.*(..))")
	public void beforeServiceMethod(JoinPoint jp) {
		logger.debug("START SERVICE - " + jp.getSignature().getDeclaringTypeName() + "."  + jp.getSignature().getName() + "()");
	}
	
	@After("execution (* com.revature.bam.service.*.*(..))")
	public void afterServiceMethod(JoinPoint jp) {
		logger.debug("END SERVICE - After " + jp.getSignature().getDeclaringTypeName() + "."  + jp.getSignature().getName() + "()");
	}
	
	@Around("execution (* com.revature.bam.controller.*.*(..)) ||"
		  + "execution (* com.revature.bam.service.*.*(..))")
	public void classNameAndTime(ProceedingJoinPoint pjp) throws Throwable {
		long start = System.currentTimeMillis();
		pjp.proceed();
		long end = System.currentTimeMillis();
		logger.debug("Total time: " + (end-start) + " milliseconds.");
	}
}