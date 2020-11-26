package com.pws.javafeatures.exception;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Logger;

/**
 * @author panws
 * @since 2017-09-13
 */
public class LoggingException {

	public static void main(String[] args) throws Exception {

		try {
			throw new LogException();
		} catch (LogException e) {
			System.err.println("Caught " + e);
			throw new Exception(e);
		}

	}

}

class LogException extends Exception {

	private static final Logger LOGGER = Logger.getLogger("LoggingException");

	public LogException() {
		StringWriter trace = new StringWriter();
		printStackTrace(new PrintWriter(trace));
		LOGGER.severe(trace.toString());
	}

}


