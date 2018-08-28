package com.vefve.db;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;

import com.vefve.db.exceptions.CreateNodeException;
import com.vefve.db.store.MemoryManager;
import com.vefve.db.store.disk.DiskStore;
import com.vefve.db.store.memory.MemoryStore;

public class VefveDB<K extends Serializable & Comparable<K>, V extends Serializable> {

	private MemoryStore<K, V> memoryStore;
	
	private DiskStore<K, V> diskStore;
	
	
	public VefveDB() throws CreateNodeException {
		
		this.memoryStore = new MemoryStore<K, V>(new ConcurrentHashMap<K, V>());
		
		if (Configuration.USE_PERSISTENT_STORAGE) {
		
			this.diskStore = new DiskStore<K, V>();
		
		}
		
	}

	
	public V get(K key) {
		
		V returnValue = this.memoryStore.get(key);
		
		if (Configuration.USE_PERSISTENT_STORAGE && returnValue == null) {

			returnValue = this.diskStore.get(key);
		}
		
		return returnValue;
	}

	
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
	
	
	public String toString() {
		String stringRepresentation  = "Memory Store: " + this.memoryStore.toString() + "\n";
		if (Configuration.USE_PERSISTENT_STORAGE) {
			stringRepresentation += "Disk Store: " + this.diskStore.toString();
		}
		return stringRepresentation;
		
	}

}
