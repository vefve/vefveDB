/**
 * Exception for vefveDB.
 */
package com.vefve.db.exceptions;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Custom exception for error in reading a file for the B-Tree node.
 * 
 * @author vefve
 *
 */
public class ReadNodeException extends IOException {
	private static final Logger logger = LogManager.getLogger(ReadNodeException.class);

	private final String path;

	
	public ReadNodeException(String path) {
		
		this(path, null, null);
		
	}

	
	public ReadNodeException(String path, String msg) {
		
		this(path, msg, null);
		
	}
	

	public ReadNodeException(String path, Throwable cause) {
		
		this(path, null, cause);
		
	}
	

	public ReadNodeException(String path, String msg, Throwable cause) {
		
		super(msg, cause);
		
		this.path = path;
		
		logger.error("Cannot read node at path: " + this.path);
		
	}
	

	public String getPath() {
		
		return path;
		
	}
}
