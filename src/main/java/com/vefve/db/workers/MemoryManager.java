package com.vefve.db.workers;

import java.util.Iterator;
import java.util.Map.Entry;

import com.vefve.db.store.disk.DiskStore;
import com.vefve.db.store.memory.MemoryStore;

public class MemoryManager<K extends Comparable<K>, V> {
	
	private MemoryStore<K, V> memoryStore;
	
	private DiskStore<K, V> diskStore;
	

	public MemoryManager(MemoryStore<K, V> memoryStore, DiskStore<K, V> diskStore) {
		
		this.memoryStore = memoryStore;
		
		this.diskStore = diskStore;
		
	}

	
	public void backupToDisk() {
		
		Iterator<Entry<K, V>> iterator = this.memoryStore.getIterator();
		
		while(iterator.hasNext()) {
			
			Entry<K, V> entry = iterator.next();
			
			diskStore.put(entry.getKey(), entry.getValue());
			
		}
		
		this.memoryStore.removeAll();
		
	}

}
