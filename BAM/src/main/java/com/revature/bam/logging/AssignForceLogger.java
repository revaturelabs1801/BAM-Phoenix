package com.revature.bam.logging;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

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
 * @author Allan / Batch : 1712_dec11th_Java_Steve
 * Logs Classes and Methods related to Assign force
 */
@Aspect
@Component
public class AssignForceLogger {

	/* TODO: Remove Sysouts */
	private static final Logger logger = LogManager.getLogger(AssignForceLogger.class);
	
	/**
	 * Returns the affected class's name and the date and time the method was invoked. 
	 * @throws Throwable 
	 */
	@Around("execution (* com.revature.bam.controller.AssignForceSyncController.*(..))")
	public void classNameAndTime(ProceedingJoinPoint pjp) throws Throwable {
	}
	
	/**
	 * Sections off part of the log to indicate the start of the method call.
	 * @param jp
	 */
	@Before("execution (* com.revature.bam.controller.AssignForceSyncController.*(..))")
	public void beforeMethod(JoinPoint jp) {
	}
	
	/**
	 * Sections off part of the log to indicate the end of the method call.
	 * @param jp
	 */
	@After("execution (* com.revature.bam.controller.AssignForceSyncController.*(..))")
	public void afterMethod(JoinPoint jp) {
	}
}
