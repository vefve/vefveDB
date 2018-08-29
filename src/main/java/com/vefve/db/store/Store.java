package com.vefve.db.store;

import java.io.IOException;
import java.io.Serializable;

import com.vefve.db.exceptions.CreateNodeException;

/**
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
	 * @param value
	 *            corresponding to the given key to be stored in the Key-Value
	 *            Store.
	 * @return 
	 * @throws IOException 
	 */
	public boolean put(Key key, Value value) throws CreateNodeException;
}
