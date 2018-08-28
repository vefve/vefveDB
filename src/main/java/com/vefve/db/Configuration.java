package com.vefve.db;

public class Configuration {
	
	public static final long MEMORY_STORAGE_SIZE = 5;
	
	public static final boolean USE_PERSISTENT_STORAGE = true;
	
	public static final float LOAD_FACTOR_THRESHOLD = 0.70f;
	
	/**
	 * Constraint: Must be even and greater than 2
	 */
	public static final int BRANCHING_FACTOR = 4;
	
	public static final String PERSISTANT_STORAGE_PATH = "/tmp/data/";

}
