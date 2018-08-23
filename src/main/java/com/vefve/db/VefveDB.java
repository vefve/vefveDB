package com.vefve.db;

import java.util.concurrent.ConcurrentHashMap;

import com.vefve.db.store.disk.DiskStore;
import com.vefve.db.store.memory.MemoryStore;

public class VefveDB<K extends Comparable<K>, V> {

	private MemoryStore<K, V> memoryStore;
	
	private DiskStore<K, V> diskStore;
	
	
	public VefveDB() {
		
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

	
	public void put(K key, V value) {

		if (key == null || value == null) {
			
			return;
			
		}
		
		while(true) {
			
			synchronized(this) {
				
				if (this.memoryStore.getLoadFactor() >= Configuration.LOAD_FACTOR_THRESHOLD) {
					
					// TODO: Trigger memory manager

				} else {
				
					this.memoryStore.put(key, value);
					
					break;
				
				}
				
			}
			
			//TODO: Wait for memory manager to finish
			
		}
		
	}

}
