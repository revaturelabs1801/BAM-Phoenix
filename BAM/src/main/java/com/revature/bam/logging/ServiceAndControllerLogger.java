package com.revature.bam.logging;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * @author Allan Poindexter, David Graves / Batch 1712_dec11th_Java_Steve
 * 
 *         Generic Logger to indicate the start and end of Controllers and
 *         Methods.
 */
@Aspect
@Component
public class ServiceAndControllerLogger {

	private static final Logger logger = LogManager.getLogger(ServiceAndControllerLogger.class);

	private final String START_EVENT = "\"startEvent\":";
	private final String END_EVENT = "\"endEvent\":";
	private final String RUN_TIME = "\"totalRuntime\":";
	private final String EXCEPTION = "\"exceptionThrown\":";
	private final String CTRL_STATUS = "\"returnedControllerStatus\":";
	private final String CTRL_VALUE = "\"returnedControllerValue\":";
	private final String SRVC_STATUS = "\"returnedServiceStatus\":";
	private final String SRVC_VALUE = "\"returnedServiceValue\":";
	private final String REQ_METHOD = "\"requestMethod\":";
	
	/**
	 * Writes the start of a Controller method call.
	 * 
	 * @author Allan Poindexter / Batch 1712_dec11th_Java_Steve
	 * @param jp
	 *            -The Controller Method
	 */
	@Before("execution (* com.revature.bam.controller.*.*(..))")
	public void beforeControllerMethod(JoinPoint jp) {
		String json = "";
		json += addEvent("start", "CONTROLLER") + ",";
		json += quotify("CLASS") + ":" + quotify(jp.getSignature().getDeclaringTypeName())+ ",";
		json += quotify("METHOD") + quotify(jp.getSignature().getName());
		logger.debug(json);
	}

	/**
	 * Writes the end of a Controller method call.
	 * 
	 * @author Allan Poindexter / Batch 1712_dec11th_Java_Steve
	 * @param jp
	 *            -The Controller Method
	 */
	@After("execution (* com.revature.bam.controller.*.*(..))")
	public void afterControllerMethod(JoinPoint jp) {
		logger.debug(END_EVENT + "CONTROLLER - " + jp.getSignature().getDeclaringTypeName() + "."
				+ jp.getSignature().getName() + "()");
	}

	/**
	 * Writes the exception thrown by a Controller method.
	 * 
	 * @author David Graves / Batch 1712_dec11th_Java_Steve
	 * @param jp
	 *            -The Controller Method
	 * @param ex
	 *            -The Exception Thrown
	 */
	@AfterThrowing(pointcut = "execution (* com.revature.bam.controller.*.*(..))", throwing = "ex")
	public void afterControllerThrows(JoinPoint jp, Exception ex) throws Exception {
		logger.info(EXCEPTION + "CONTROLLER " + jp.getSignature().getName() + "(). " + ex.getCause().getMessage());
	}

	/**
	 * Writes the status code of Controller methods.
	 * 
	 * @author David Graves / Batch 1712_dec11th_Java_Steve
	 * @param jp
	 *            -The Controller Method
	 * @param retVal
	 *            -The ResponseEntity returned
	 */
	@AfterReturning(pointcut = "execution (* com.revature.bam.controller.*.*(..))", returning = "retVal")
	public void afterControllerReturns(JoinPoint jp, ResponseEntity<?> retVal) {
		logger.info("CONTROLLER " + jp.getSignature().getName() + "() " + CTRL_STATUS + retVal.getStatusCodeValue());
		if (retVal.hasBody()) {
			logger.info("CONTROLLER " + jp.getSignature().getName() + "() " + CTRL_VALUE + retVal.getBody());
		}
	}

	/**
	 * Writes the exception thrown by a Service method.
	 * 
	 * @author David Graves / Batch 1712_dec11th_Java_Steve
	 * @param jp
	 *            -The Service Method
	 * @param ex
	 *            -The Exception Thrown
	 */
	@AfterThrowing(pointcut = "execution (* com.revature.bam.service.*.*(..))", throwing = "ex")
	public void afterServiceThrows(JoinPoint jp, Exception ex) throws Exception {
		logger.info(EXCEPTION + "SERVICE" + jp.getSignature().getName() + "(). " + ex.getCause().getMessage());
	}

	/**
	 * Writes the returned value of Service methods.
	 * 
	 * @author David Graves / Batch 1712_dec11th_Java_Steve
	 * @param jp
	 *            -The Service Method
	 * @param retVal
	 *            -The value returned
	 */
	@AfterReturning(pointcut = "execution (* com.revature.bam.service.*.*(..))", returning = "retVal")
	public void afterServiceReturns(JoinPoint jp, Object retVal) {
		logger.info("SERVICE " + jp.getSignature().getName() + "() " + SRVC_VALUE + retVal);
	}

	/**
	 * Writes the start of a Service method call.
	 * 
	 * @author Allan Poindexter / Batch 1712_dec11th_Java_Steve
	 * @param jp
	 *            -The Service Method
	 */
	@Before("execution (* com.revature.bam.service.*.*(..))")
	public void beforeServiceMethod(JoinPoint jp) {
		logger.debug(START_EVENT + "SERVICE - " + jp.getSignature().getDeclaringTypeName() + "."
				+ jp.getSignature().getName() + "()");
	}

	/**
	 * Writes the end of a Service method call.
	 * 
	 * @author Allan Poindexter / Batch 1712_dec11th_Java_Steve
	 * @param jp
	 *            -The Service Method
	 */
	@After("execution (* com.revature.bam.service.*.*(..))")
	public void afterServiceMethod(JoinPoint jp) {
		logger.debug(END_EVENT + "SERVICE - " + jp.getSignature().getDeclaringTypeName() + "."
				+ jp.getSignature().getName() + "()");
	}

	/**
	 * Displays the run time for Service or Controller methods. For the Controller,
	 * it will display the total runtime.
	 * 
	 * @author Allan Poindexter / Batch 1712_dec11th_Java_Steve
	 * @param pjp
	 *            - The Service or Controller Methods
	 * @return - object. This is required, or else the controller will return a
	 *         blank JSON object, or a service will throw a NullPointerException.
	 * @throws -
	 *             Throwable. This is required when using
	 *             PreceedingJoinPoint.proceed()
	 */
	@Around("execution (* com.revature.bam.controller.*.*(..)) ||" + "execution (* com.revature.bam.service.*.*(..))")
	public Object classNameAndTime(ProceedingJoinPoint pjp) throws Throwable {
		long start = System.currentTimeMillis();
		Object object = pjp.proceed();
		long end = System.currentTimeMillis();
		logger.debug(RUN_TIME + (end - start) + " milliseconds.");
		return object;
	}

	/**
	 * Indicates a GET mapped method has fired. POST equivalent does not exist. See
	 * Issue on GitHub.
	 * 
	 * @author Allan Poindexter / Batch 1712_dec11th_Java_Steve
	 * @param classRequestMapping
	 * @param getMapping
	 */
	@Pointcut("@target(classRequestMapping) && @annotation(getMapping) && execution(* com.revature.bam.controller.*.*(..))")
	public void controller(RequestMapping classRequestMapping, GetMapping getMapping) {
	}

	@Before("controller(classRequestMapping, getMapping)")
	public void advice(JoinPoint jp, RequestMapping classRequestMapping, GetMapping getMapping) {
		logger.info(REQ_METHOD + "GET -- " + jp.getSignature().getDeclaringTypeName());
	}

	/**
	 * @author Charlie Harris / BATCH 1712_dec11th_Java_Steve
	 * Turns [str] into a JSON string
	 * @param str
	 *            Assumed to in auto-generated toString() format
	 * @return
	 */
	private String jsonify(String str) {
		StringBuilder json = new StringBuilder();
		String className = str.substring(0, str.indexOf(" "));

		json.append(quotify(className) + ": ");
		json.append("{");

		//Remove Class name
		str = str.substring(str.indexOf(" ") + 1);

		//Remove square brackets
		str = str.substring(1, str.length() -1);

		//Get key value pairs
		String[] mappings = str.split(",");

		//Insert key value pairs into json string
		for(String mapping: mappings) {
			mapping = mapping.trim();
			mapping = mapping.replace('=', ':');

			//Put quotes around key
			String key = className + mapping.substring(0, mapping.indexOf(":"));
			key = quotify(key);

			//Put quotes around value
			String val = "";
			if (mapping.indexOf(":") < mapping.length() - 1) {
				val = mapping.substring(mapping.indexOf(":") + 1);
				val = quotify(val);
			}

			mapping = key + ":" + val + ",";
			json.append(mapping);
		}

		//Remove final comma
		json.deleteCharAt(json.length() -1);

		//Add closing bracket
		json.append("}");

		return json.toString();
	}

	/**
	 * Surrounds [str] in quotes, e.g. hello --> "hello"
	 * @param str
	 * @return
	 */
	private String quotify(String str) {
		return "\"" + str + "\"";
	}

	private String addEvent(String type, String name) {
		return "\"" + type + "Event" + "\":\"" + name + "\"";
	}
}