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
 * @author Allan Poindexter / Batch 1712_dec11th_Java_Steve
 * 
 * Generic Logger to indicate the start and end of Controllers and Methods.
 */
@Aspect
@Component
public class ServiceAndControllerLogger {

	private static final Logger logger = LogManager.getLogger(ServiceAndControllerLogger.class);

	/**
	 * Writes the start of a Controller method call.
	 * 
	 * @author Allan Poindexter / Batch 1712_dec11th_Java_Steve
	 * @param jp
	 * 		-The Controller Method
	 */
	@Before("execution (* com.revature.bam.controller.*.*(..))")
	public void beforeControllerMethod(JoinPoint jp) {
		logger.debug("\r\n\n\n");
		logger.debug("START CONTROLLER - " + jp.getSignature().getDeclaringTypeName() + "."  + jp.getSignature().getName() + "()");
	}
	
	/**
	 * Writes the end of a Controller method call.
	 * 
	 * @author Allan Poindexter / Batch 1712_dec11th_Java_Steve
	 * @param jp
	 * 		-The Controller Method
	 */
	@After("execution (* com.revature.bam.controller.*.*(..))")
	public void afterControllerMethod(JoinPoint jp) {
		logger.debug("END CONTROLLER - " + jp.getSignature().getDeclaringTypeName() + "."  + jp.getSignature().getName() + "()");
		logger.debug("\r\n\n\n");
	}
	
	
	/**
	 * Writes the start of a Service method call.
	 * 
	 * @author Allan Poindexter / Batch 1712_dec11th_Java_Steve
	 * @param jp
	 * 		-The Service Method
	 */
	@Before("execution (* com.revature.bam.service.*.*(..))")
	public void beforeServiceMethod(JoinPoint jp) {
		logger.debug("START SERVICE - " + jp.getSignature().getDeclaringTypeName() + "."  + jp.getSignature().getName() + "()");
	}
	
	/**
	 * Writes the end of a Service method call.
	 * 
	 * @author Allan Poindexter / Batch 1712_dec11th_Java_Steve
	 * @param jp
	 * 		-The Service Method
	 */
	@After("execution (* com.revature.bam.service.*.*(..))")
	public void afterServiceMethod(JoinPoint jp) {
		logger.debug("END SERVICE - " + jp.getSignature().getDeclaringTypeName() + "."  + jp.getSignature().getName() + "()");
	}
	
	/**
	 * Displays the run time for Service or Controller methods.
	 * For the Controller, it will display the total runtime.
	 * 
	 * @author Allan Poindexter / Batch 1712_dec11th_Java_Steve
	 * @param pjp
	 * 		- The Service or Controller Methods
	 * @return
	 * 		- object. This is required, or else the controller will return a blank JSON object,
	 * 				  or a service will throw a NullPointerException.
	 * @throws
	 * 		- Throwable. This is required when using PreceedingJoinPoint.proceed()
	 */
	@Around("execution (* com.revature.bam.controller.*.*(..)) ||"
		  + "execution (* com.revature.bam.service.*.*(..))")
	public Object classNameAndTime(ProceedingJoinPoint pjp) throws Throwable {
		long start = System.currentTimeMillis();
		Object object = pjp.proceed();
		long end = System.currentTimeMillis();
		logger.debug("Total time: " + (end-start) + " milliseconds.");
		return object;
	}
}