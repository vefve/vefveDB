/**
 * VefveDB implementation
 */
package com.vefve.db;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.vefve.db.exceptions.CreateNodeException;
import com.vefve.db.store.MemoryManager;
import com.vefve.db.store.disk.DiskStore;
import com.vefve.db.store.memory.MemoryStore;

/**
 * VefveDB implementation
 * 
 * @author vefve
 *
 * @param <K> Key type. Has to be Serializable and Comparable.
 * @param <V> Value type. Has to be Serializable.
 */
public class VefveDB<K extends Serializable & Comparable<K>, V extends Serializable> {

	private MemoryStore<K, V> memoryStore;
	
	private DiskStore<K, V> diskStore;
	
	private ReentrantReadWriteLock diskStoreLock;
	
	
	public VefveDB() throws CreateNodeException {
		
		this.memoryStore = new MemoryStore<K, V>(new ConcurrentHashMap<K, V>());
		
		if (Configuration.USE_PERSISTENT_STORAGE) {
		
			this.diskStoreLock = new ReentrantReadWriteLock();
			
			this.diskStore = new DiskStore<K, V>(this.diskStoreLock);
		
		}
		
	}

	
	/**
	 * Returns the value corresponding to the given key, if present in the DB Store.
	 * @param key Key to search in the DB.
	 * 
	 * @return Returns the value corresponding to the given key, if present in the DB Store.
	 */
	public V get(K key) {
		
		V returnValue = this.memoryStore.get(key);
		
		if (Configuration.USE_PERSISTENT_STORAGE && returnValue == null) {

			returnValue = this.diskStore.get(key);
		}
		
		return returnValue;
	}

	
	/**
	 * Insert a Key-Value pair in the DB Store.
	 * 
	 * @param key Key to be inserted.
	 * @param value Value to be inserted.
	 * @throws CreateNodeException If unable to create a new file for the B-Tree node.
	 */
	public void put(K key, V value) throws CreateNodeException {

		if (key == null || value == null) {
			
			return;
			
		}
		
		while(true) {
			
			synchronized(this) {
				
				if (Configuration.USE_PERSISTENT_STORAGE && this.memoryStore.getLoadFactor() >= Configuration.LOAD_FACTOR_THRESHOLD) {
					
					MemoryManager<K, V> memoryManager = new MemoryManager<K, V>(memoryStore, diskStore);
					
					memoryManager.backupToDisk();

				} else {
				
					this.memoryStore.put(key, value);
					
					break;
				
				}
				
			}
			
		}
		
	}
	
	
	/**
	 * Returns String representation of the current state of the DB Store.
	 */
	public String toString() {
		
		String stringRepresentation  = "Memory Store: " + this.memoryStore.toString() + "\n";
		
		if (Configuration.USE_PERSISTENT_STORAGE) {
			
			stringRepresentation += "Disk Store: " + this.diskStore.toString();
			
		}
		
		return stringRepresentation;
		
	}

}
