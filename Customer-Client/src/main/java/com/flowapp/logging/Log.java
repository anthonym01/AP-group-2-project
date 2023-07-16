package com.flowapp.logging;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Log {
	private static final Logger logger = LogManager.getLogger(Log.class);

	// Trace logger
	public static void trace(String message) {
		logger.trace(message);
	}

	public static void trace(String className, String methodName, String message) {
		logger.error("[" + className + " | " + methodName + "()] - " + message);
	}

	// Debug logger
	public static void debug(String message) {
		logger.debug(message);
	}

	public static void debug(String className, String methodName, String message) {
		logger.debug("[" + className + " | " + methodName + "()] - " + message);
	}

	// Info logger
	public static void info(String message) {
		logger.info(message);
	}

	public static void info(String className, String methodName, String message) {
		logger.info("[" + className + " | " + methodName + "()] - " + message);
	}

	// Warn logger
	public static void warn(String message) {
		logger.warn(message);
	}

	public static void warn(String className, String methodName, String message) {
		logger.warn("[" + className + " | " + methodName + "()] - " + message);
	}

	// Error logger
	public static void error(String message) {
		logger.error(message);
	}

	public static void error(String className, String methodName, String message) {
		logger.error("[" + className + " | " + methodName + "()] - " + message);
	}

	// Fatal logger
	public static void fatal(String message) {
		logger.fatal(message);
	}

	public static void fatal(String className, String methodName, String message) {
		logger.fatal("[" + className + " | " + methodName + "()] - " + message);
	}
}
