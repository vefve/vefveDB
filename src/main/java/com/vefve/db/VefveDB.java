package com.vefve.db;

import com.vefve.db.store.disk.DiskStore;
import com.vefve.db.store.memory.MemoryStore;

public class VefveDB<Key extends Comparable<Key>, Value> {

	private MemoryStore<Key, Value> memoryStore;
	
	private DiskStore diskStore;

	
	public Value get(Key key) {
		return null;
	}

	public void put(Key key, Value value) {
		
	}

}
