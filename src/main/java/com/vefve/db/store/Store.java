package com.vefve.db.store;

/**
 * @author vefve
 *
 */
public interface Store<Key extends Comparable<Key>, Value> {

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
	 */
	public boolean put(Key key, Value value);
}
