/**
 * Storage for VefveDB.
 */
package com.vefve.db.store;

import java.io.IOException;
import java.io.Serializable;

import com.vefve.db.exceptions.CreateNodeException;

/**
 * Storage interface for VefveDB.
 * 
 * @author vefve
 *
 */
public interface Store<Key extends Serializable & Comparable<Key>, Value extends Serializable> {

	/**
	 * 
	 * @param key
	 * @return Value corresponding to the given key, else null.
	 */
	public Value get(Key key);

	
	/**
	 * 
	 * @param key
	 *            to be stored in the Key-Value Store.
	 *            
	 * @param value
	 *            corresponding to the given key to be stored in the Key-Value
	 *            Store.
	 *            
	 * @return {@code true} if successfully inserted, {@code false} if not.
	 * 
	 * @throws CreateNodeException If unable to create a new file for the B-Tree node.
	 */
	public boolean put(Key key, Value value) throws CreateNodeException;
}
