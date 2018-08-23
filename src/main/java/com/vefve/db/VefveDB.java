package com.vefve.db;

import java.util.concurrent.ConcurrentHashMap;

import com.vefve.db.store.disk.DiskStore;
import com.vefve.db.store.memory.MemoryStore;

public class VefveDB<Key extends Comparable<Key>, Value> {

	private MemoryStore<Key, Value> memoryStore;
	
	private DiskStore<Key, Value> diskStore;
	
	
	public VefveDB() {
		
		this.memoryStore = new MemoryStore<Key, Value>(new ConcurrentHashMap<Key, Value>());
		
		this.diskStore = new DiskStore<Key, Value>();
		
	}

	
	public Value get(Key key) {
		
		Value returnValue = this.memoryStore.get(key);
		
		return returnValue;
	}

	
	public void put(Key key, Value value) {

		this.memoryStore.put(key, value);
		
	}

}
