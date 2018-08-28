package com.vefve.db.store.memory;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vefve.db.Configuration;
import com.vefve.db.store.Store;

public class MemoryStore<K extends Serializable & Comparable<K>, V extends Serializable> implements Store<K, V> {

	private ConcurrentHashMap<K, V> dbStore;
	
	private static final Logger logger = LogManager.getLogger(MemoryStore.class);
	

	/**
	 * Creates a new MemoryStore using the provided dbStore.
	 * 
	 * @param dbStore Used to initialize the MemoryStore.
	 */
	public MemoryStore(ConcurrentHashMap<K, V> dbStore) {

		this.dbStore = dbStore;

	}

	
	/**
	 * Returns the value to which the specified key is mapped, or null if this
	 * dbStore contains no mapping for the key.
	 * 
	 * @param key
	 *            The key whose associated value is to be returned.
	 * 
	 * @return The value to which the specified key is mapped, or null if this
	 *         dbStore contains no mapping for the key.
	 */
	@Override
	public V get(K key) {

		return dbStore.get(key);

	}

	
	/**
	 * Maps the specified key to the specified value in this dbStore. Neither the
	 * key nor the value can be null.
	 * 
	 * @param key
	 *            Key with which the specified value is to be associated.
	 *            
	 * @param value
	 *            Value to be associated with the specified key.
	 */
	@Override
	public boolean put(K key, V value) {

		synchronized (this) {

			if (!this.isFull()) {

				this.dbStore.put(key, value);

				return true;

			}

			return false;

		}
	}

	
	/**
	 * Removes the key (and its corresponding value) from this dbStore. This method does nothing if the key is not in the dbStore.
	 * 
	 * @param key The key that needs to be removed.
	 */
	public void remove(K key) {

		this.dbStore.remove(key);

	}

	
	/**
	 * Removes all of the mappings from this dbStore.
	 */
	public void removeAll() {

		this.dbStore.clear();

	}

	
	/**
	 * 
	 * @param key Possible key
	 * 
	 * @return {@code true} if and only if the specified object is a key in this dbStore; {@code false} otherwise.
	 */
	public boolean containsKey(K key) {

		return this.dbStore.containsKey(key);

	}

	
	/**
	 * Returns an iterator over the key-value pairs in this dbStore.
	 * 
	 * @return Returns an iterator over the key-value pairs in this dbStore.
	 */
	public Iterator<Entry<K, V>> getIterator() {
		return this.dbStore.entrySet().iterator();
	}

	
	/**
	 * 
	 * @return Whether the dbStore is full or not.
	 */
	public boolean isFull() {

		if (this.dbStore.mappingCount() < Configuration.MEMORY_STORAGE_SIZE) {

			return false;

		}

		return true;

	}

	
	/**
	 * Returns the load factor (table density) for this dbStore.
	 * 
	 * @return Returns the load factor (table density) for this dbStore.
	 */
	public float getLoadFactor() {

		return (float) this.dbStore.mappingCount() / Configuration.MEMORY_STORAGE_SIZE;

	}
	
	
	/**
	 * Returns a string representation of this dbStore.
	 * 
	 * @return A string representation of this dbStore.
	 */
	@Override
	public String toString() {
		
		return this.dbStore.toString();
		
	}
}
