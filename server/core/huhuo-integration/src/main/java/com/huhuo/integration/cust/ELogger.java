package com.huhuo.integration.cust;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * alternative for org.slf4j.Logger that provide more convenient usage
 * @author wuyuxuan
 */
public class ELogger {

	private Logger logger;

	@SuppressWarnings("rawtypes")
	public ELogger(Class clazz) {
		super();
		logger = LoggerFactory.getLogger(clazz);
	}
	public ELogger(String name) {
		super();
		logger = LoggerFactory.getLogger(name);
	}
	/**
	 * get slf4j Logger
	 * @return
	 */
	public Logger getLogger() {
		return logger;
	}
	/**
	 * Log a message at the TRACE level according to the specified format and
	 * arguments.
	 * <p>
	 * This form avoids superfluous object creation when the logger is disabled
	 * for the TRACE level.
	 * </p>
	 * @param format the format string
	 * @param argArray an array of arguments
	 * @since 1.4
	 */
	public void trace(String format, Object... argArray) {
		logger.trace(format, argArray);
	}
	
	/**
	 * Log the stack trace of @param throwable
	 * @param throwable
	 * @param flags the flags start before the Exception stack trace
	 */
	public void trace(Throwable throwable, String... flags) {
		if(flags != null && flags.length != 0) {
			String flgMsg = "";
			for(String flag : flags) {
				flgMsg += flag;
			}
			logger.trace(flgMsg);
		}
		logger.trace(ExceptionUtils.getStackTrace(throwable));
	}

	/**
	 * Log a message at the DEBUG level according to the specified format and
	 * arguments.
	 * <p>
	 * This form avoids superfluous object creation when the logger is disabled
	 * for the DEBUG level.
	 * </p>
	 * @param format the format string
	 * @param argArray an array of arguments
	 */
	public void debug(String format, Object... argArray) {
		logger.debug(format, argArray);
	}

	/**
	 * Log the stack trace of @param throwable
	 * @param throwable
	 * @param flags the flags start before the Exception stack trace
	 */
	public void debug(Throwable throwable, String... flags) {
		if(flags != null && flags.length != 0) {
			String flgMsg = "";
			for(String flag : flags) {
				flgMsg += flag;
			}
			logger.debug(flgMsg);
		}
		logger.debug(ExceptionUtils.getStackTrace(throwable));
	}

	/**
	 * Log a message at the INFO level according to the specified format and
	 * arguments.
	 * <p>
	 * This form avoids superfluous object creation when the logger is disabled
	 * for the INFO level.
	 * </p>
	 * 
	 * @param format the format string
	 * @param argArray an array of arguments
	 */
	public void info(String format, Object... argArray) {
		logger.info(format, argArray);
	}

	/**
	 * Log the stack trace of @param throwable
	 * @param throwable
	 * @param flags the flags start before the Exception stack trace
	 */
	public void info(Throwable throwable, String... flags) {
		if(flags != null && flags.length != 0) {
			String flgMsg = "";
			for(String flag : flags) {
				flgMsg += flag;
			}
			logger.info(flgMsg);
		}
		logger.info(ExceptionUtils.getStackTrace(throwable));
	}
	
	/**
	 * Log a message at the WARN level according to the specified format and
	 * arguments.
	 * <p>
	 * This form avoids superfluous object creation when the logger is disabled
	 * for the WARN level.
	 * </p>
	 * @param format the format string
	 * @param argArray an array of arguments
	 */
	public void warn(String format, Object... argArray) {
		logger.warn(format, argArray);
	}

	/**
	 * Log the stack trace of @param throwable
	 * @param throwable
	 * @param flags the flags start before the Exception stack trace
	 */
	public void warn(Throwable throwable, String... flags) {
		if(flags != null && flags.length != 0) {
			String flgMsg = "";
			for(String flag : flags) {
				flgMsg += flag;
			}
			logger.warn(flgMsg);
		}
		logger.warn(ExceptionUtils.getStackTrace(throwable));
	}
	
	/**
	 * Log a message at the ERROR level according to the specified format and
	 * arguments.
	 * <p>
	 * This form avoids superfluous object creation when the logger is disabled
	 * for the ERROR level.
	 * </p>
	 * @param format the format string
	 * @param argArray an array of arguments
	 */
	public void error(String format, Object... argArray) {
		logger.error(format, argArray);
	}
	
	/**
	 * Log the stack trace of @param throwable
	 * @param throwable
	 * @param flags the flags start before the Exception stack trace
	 */
	public void error(Throwable throwable, String... flags) {
		if(flags != null && flags.length != 0) {
			String flgMsg = "";
			for(String flag : flags) {
				flgMsg += flag;
			}
			logger.error(flgMsg);
		}
		logger.error(ExceptionUtils.getStackTrace(throwable));
	}
}
