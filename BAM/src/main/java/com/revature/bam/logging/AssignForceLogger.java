package com.revature.bam.logging;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * 
 * @author Allan
 * Logs Classes and Methods related to Assign force
 */
@Aspect
@Component
public class AssignForceLogger {

	/* TODO: Remove Sysouts */
	
	/**
	 * Returns the affected class's name and the date and time the method was invoked. 
	 */
	private void classNameAndTime() {
		String sdf = new SimpleDateFormat("MM-dd-yyyy, hh:mm:ss.SSS a ").format(new Date());
		System.out.print(sdf);
		System.out.print(" -- AssignForceSyncController -- ");
	}
	
	@Before("execution (* com.revature.bam.controller.AssignForceSyncController.*(..))")
	public void beforeMethod(JoinPoint joinPoint) {
		classNameAndTime();
		System.out.println("Before " + joinPoint.getSignature().getName() + " method.");
	}
	
	@After("execution (* com.revature.bam.controller.AssignForceSyncController.*(..))")
	public void afterMethod(JoinPoint joinPoint) {
		classNameAndTime();
		System.out.println("After " + joinPoint.getSignature().getName() + " method.");
	}
}
