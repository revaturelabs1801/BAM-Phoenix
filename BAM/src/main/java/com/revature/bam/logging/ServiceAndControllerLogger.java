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
		json += addEvent("start", "controller") + ",";
		json += addKey("controllerClass") + addValue(jp.getSignature().getDeclaringTypeName());
		json += addKey("controllerMethod") + addEndValue(jp.getSignature().getName());
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
		String json = "";
		json += addEvent("end", "controller") + ",";
		json += addKey("controllerClass") + addValue(jp.getSignature().getDeclaringTypeName());
		json += addKey("controllerMethod") + addEndValue(jp.getSignature().getName());
		logger.debug(json);
	}

	/**
	 * Writes the exception thrown by a Controller method.
	 * 
	 * @author David Graves / Batch 1712_dec11th_Java_Steve
	 * 		   Allan Poindexter / Batch 1712_dec11th_Java_Steve
	 * @param jp
	 *            -The Controller Method
	 * @param ex
	 *            -The Exception Thrown
	 */
	@AfterThrowing(pointcut = "execution (* com.revature.bam.controller.*.*(..))", throwing = "ex")
	public void afterControllerThrows(JoinPoint jp, Exception ex) throws Exception {
		String json = "";
		json += addKey("controllerException") + addEndValue(jp.getSignature().getDeclaringTypeName() + "." +
															jp.getSignature().getName() + "() -- " + ex.getCause().getMessage());
		logger.info(json);
	}

	/**
	 * Writes the status code of Controller methods.
	 * 
	 * @author David Graves / Batch 1712_dec11th_Java_Steve
	 * 		   Allan Poindexter / Batch 1712_dec11th_Java_Steve
	 * @param jp
	 *            -The Controller Method
	 * @param retVal
	 *            -The ResponseEntity returned
	 */
	@AfterReturning(pointcut = "execution (* com.revature.bam.controller.*.*(..))", returning = "retVal")
	public void afterControllerReturns(JoinPoint jp, ResponseEntity<?> retVal) {
		String json = "";
		json += addKey("controllerClass") + addValue(jp.getSignature().getDeclaringTypeName());
		json += addKey("controllerMethod") + addValue(jp.getSignature().getName());
		json += addKey("returnedControllerStatus") + addEndValue(String.valueOf(retVal.getStatusCodeValue()));
		logger.info(json);
		if (retVal.hasBody()) {
			json = "";
			json += addKey("controllerClass") + addValue(jp.getSignature().getDeclaringTypeName());
			json += addKey("controllerMethod") + addValue(jp.getSignature().getName());
			json += addKey("returnedControllerValue") + jsonify(retVal.getBody().toString());
			logger.info(json);
		}
	}

	/**
	 * Writes the exception thrown by a Service method.
	 * 
	 * @author David Graves / Batch 1712_dec11th_Java_Steve
	 * 		   Allan Poindexter / Batch 1712_dec11th_Java_Steve
	 * @param jp
	 *            -The Service Method
	 * @param ex
	 *            -The Exception Thrown
	 */
	@AfterThrowing(pointcut = "execution (* com.revature.bam.service.*.*(..))", throwing = "ex")
	public void afterServiceThrows(JoinPoint jp, Exception ex) throws Exception {
		String json = "";
		json += addKey("serviceException") + addEndValue(jp.getSignature().getDeclaringTypeName() + "." +
														 jp.getSignature().getName() + "() -- " + ex.getCause().getMessage());
		logger.info(json);
	}

	/**
	 * Writes the returned value of Service methods.
	 * 
	 * @author David Graves / Batch 1712_dec11th_Java_Steve
	 * 		   Allan Poindexter / Batch 1712_dec11th_Java_Steve
	 * @param jp
	 *            -The Service Method
	 * @param retVal
	 *            -The value returned
	 */
	@AfterReturning(pointcut = "execution (* com.revature.bam.service.*.*(..))", returning = "retVal")
	public void afterServiceReturns(JoinPoint jp, Object retVal) {
		String json = "";
		json += addKey("serviceClass") + addValue(jp.getSignature().getDeclaringTypeName());
		json += addKey("serviceMethod") + addValue(jp.getSignature().getName());
		json += addKey("returnedServiceValue") + jsonify(retVal.toString());
		logger.info(json);
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
		String json = "";
		json += addEvent("start", "service") + ",";
		json += addKey("serviceClass") + addValue(jp.getSignature().getDeclaringTypeName());
		json += addKey("serviceMethod") + addEndValue(jp.getSignature().getName());
		logger.debug(json);
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
		String json = "";
		json += addEvent("end", "service") + ",";
		json += addKey("serviceClass") + addValue(jp.getSignature().getDeclaringTypeName());
		json += addKey("serviceMethod") + addEndValue(jp.getSignature().getName());
		logger.debug(json);
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
		
		String json = "";
		json += addKey("totalRuntime") + addValue((end-start) + " milliseconds");
		json += addKey("class") + addValue(pjp.getSignature().getDeclaringTypeName());
		json += addKey("method") + addEndValue(pjp.getSignature().getName());
		logger.debug(json);
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
		String json = "";
		json += addKey("requestMethod") + addValue("GET");
		json += addKey("class") + addValue(jp.getSignature().getDeclaringTypeName());
		json += addKey("method") + addEndValue(jp.getSignature().getName());
		logger.info(json);
	}

	/**
	 * @author Charlie Harris / Batch 1712_dec11th_Java_Steve
	 * 		   Allan Poindexter / Batch 1712_dec11_Java_Steve
	 * Turns [str] into a JSON string
	 * @param str
	 *            Assumed to in auto-generated toString() format
	 * @return a JSON string
	 */
	private String jsonify(String str) {
		StringBuilder json = new StringBuilder();
		String className = str.substring(1, str.indexOf(" "));

		json.append("{" + quotify(className) + ":");
		json.append("{");
		
		//Remove Class name
		str = str.substring(str.indexOf(" ") + 1);
		
		//Remove square brackets
		str = str.substring(1, str.length() -1);
		//Replace all remaining square brackets with curly braces. 
		str = str.replace('[', '{');
		str = str.replace(']', '}');
		
		//Get key value pairs
		String[] mappings = str.split(",");
		
		//Insert key value pairs into json string
		for(String mapping: mappings) {
			mapping = mapping.trim();
			mapping = mapping.replace('=', ':');
			
			//Put quotes around key
			String key = concatCamelCase(className, mapping.substring(0, mapping.indexOf(":")));
			key = quotify(key);
			
			//Put quotes around value
			String val = "";
			if (mapping.indexOf(":") < mapping.length() - 1) {
				val = mapping.substring(mapping.indexOf(":") + 1);
				if (val.indexOf('}') > -1) {
					val = val.replace("}", "");
					val = quotify(val);
					val += "}";
				} else if (val.indexOf('{') > -1) { //Start of another class
					
					String nestedClassName = val.substring(0, val.indexOf('{'));
					nestedClassName = nestedClassName.trim();
					nestedClassName = "{" + quotify(nestedClassName) + ":";
					
					StringBuilder nestedBuilder = new StringBuilder(val);
					nestedBuilder.replace(0, val.indexOf('{'), nestedClassName);
					
					val = nestedBuilder.toString();
				} else {
					val = quotify(val);
				}
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
	 * Concatenates two camelCased strings into a single camelCased string
	 * @param str1 Should be camelCased
	 * @param str2 Should be camelCased
	 * @return str1 concatenated with str2 camelCased, 
	 * 		e.g. --> concatCamelCase("helloWorld", "goodbyeWorld") --> "helloWorldGoodbyeWorld"
	 * @author Charlie Harris | 1712-dec11-java-Steve
	 */
	private String concatCamelCase(String str1, String str2) {
		if (str1.length() == 0) return str2;
		if (str2.length() == 0) return str1;
		
		//Make first letter of str1 lowercase
		if (str1.length() > 1) str1 = Character.toLowerCase(str1.charAt(0)) + str1.substring(1);
		else str1 = str1.toLowerCase();
		
		//Make first letter of str2 uppercase
		if (str2.length() > 1) str2 = Character.toUpperCase(str2.charAt(0)) + str2.substring(1);
		else str2 = str2.toUpperCase();
		
		return str1 + str2;
	}

	/**
	 * @author Charlie Harris / Batch 1712_dec11th_Java_Steve
	 * Surrounds [str] in quotes, e.g. hello --> "hello"
	 * @param str
	 * @return str in quotations
	 */
	private String quotify(String str) {
		return "\"" + str + "\"";
	}

	/**
	 * @author Allan Poindexter / Batch 1712_dec11th_Java_Steve
	 * Creates an Event Key in json.
	 * @param type - start, end, etc.
	 * @param name - name of the event: controller, servce, etc.
	 * @return an event:type JSON string
	 */
	private String addEvent(String type, String name) {
		return "\"" + type + "Event" + "\":\"" + name + "\"";
	}
	
	/**
	 * @author Allan Poindexter / Batch 1712_dec11th_Java_Steve
	 * Adds quotation marks to a JSON key
	 * @param key
	 * @return key in quotations
	 */
	private String addKey(String key) {
		return "\"" + key + "\":";
	}
	
	/**
	 * @author Allan Poindexter / Batch 1712_dec11th_Java_Steve
	 * Adds quotation marks to a JSON value, and a comma
	 * @param value
	 * @return value in quotations
	 */
	private String addValue(String value) {
		return "\"" + value + "\",";
	}
	
	/**
	 * @author Allan Poindexter / Batch 1712_dec11th_Java_Steve
	 * Calls quotify to add quotations to a value. This indicate the end of a JSON string.
	 * @param value
	 * @return value in quotations
	 */
	private String addEndValue(String value) {
		return quotify(value);
	}
}