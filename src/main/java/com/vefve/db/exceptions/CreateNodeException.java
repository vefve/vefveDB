/**
 * 
 */
package com.vefve.db.exceptions;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Custom exception for error in creating a new file for the B-Tree node.
 * 
 * @author vefve
 *
 */
public class CreateNodeException extends IOException {

	private static final Logger logger = LogManager.getLogger(CreateNodeException.class);
	
	private final String path;

    public CreateNodeException(String path) {
        this(path, null, null);
    }

    public CreateNodeException(String path,
                            String msg) {
        this(path, msg, null);
    }

    public CreateNodeException(String path,
                            Throwable cause) {
        this(path, null, cause);
    }

    public CreateNodeException(String path,
                            String msg,
                            Throwable cause) {
        super(msg, cause);
        this.path = path;
        logger.error("Cannot create new node at path: " + this.path);
    }

    public String getPath() {
        return path;
    }
}
