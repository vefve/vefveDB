package com.vefve.db.store.memory;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import com.vefve.db.Configuration;
import com.vefve.db.store.Store;

public class MemoryStore<Key extends Comparable<Key>, Value> implements Store<Key, Value> {

	private ConcurrentHashMap<Key, Value> dbStore;

	public MemoryStore(ConcurrentHashMap<Key, Value> dbStore) {

		this.dbStore = dbStore;

	}

	
	@Override
	public Value get(Key key) {

		return dbStore.get(key);

	}

	
	@Override
	public boolean put(Key key, Value value) {
		
		synchronized(this) {
			
			if (!this.isFull()) {
				
				this.dbStore.put(key, value);
				
				return true;
				
			}
			
			return false;
			
		}
	}
	
	
	public void remove(Key key) {
			
		// The case where the key is not present in the dbStore is already handled by remove method.
		this.dbStore.remove(key);
			
	}
	
	
	public void removeAll() {
		
		this.dbStore.clear();
		
	}
	

	public boolean containsKey(Key key) {

		return this.dbStore.containsKey(key);

	}

	
	public Iterator<Entry<Key, Value>> getIterator() {
		return this.dbStore.entrySet().iterator();
	}
	
	
	/**
	 * 
	 * @return Whether the dbStore is full or not. If the number of keys in the
	 *         dbStore and the MAX_STORAGE_SIZE are greater than Integer.MAX_VALUE
	 *         then this will return false.
	 */
	public boolean isFull() {

		if (this.dbStore.mappingCount() < Configuration.MEMORY_STORAGE_SIZE) {

			return false;

		}

		return true;

	}
	
	
	public float getLoadFactor() {
		
		return (float)this.dbStore.mappingCount() / Configuration.MEMORY_STORAGE_SIZE;
		
	}
}
