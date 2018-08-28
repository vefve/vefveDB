package com.vefve.db.store;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Map.Entry;

import com.vefve.db.store.disk.DiskStore;
import com.vefve.db.store.memory.MemoryStore;

/**
 * 
 * @author vefve
 */
public class MemoryManager<K extends Serializable & Comparable<K>, V extends Serializable> {

	private MemoryStore<K, V> memoryStore;

	private DiskStore<K, V> diskStore;

	public MemoryManager(MemoryStore<K, V> memoryStore, DiskStore<K, V> diskStore) {

		this.memoryStore = memoryStore;

		this.diskStore = diskStore;

	}

	/**
	 * Backs up the data in the {@code memoryStore} to the {@code diskStore} and
	 * empties the {@code memoryStore}
	 */
	public void backupToDisk() {

		synchronized (this) {
			
			Iterator<Entry<K, V>> iterator = this.memoryStore.getIterator();
	
			while (iterator.hasNext()) {
	
				Entry<K, V> entry = iterator.next();
	
				diskStore.put(entry.getKey(), entry.getValue());
	
			}
	
			this.memoryStore.removeAll();
			
		}

	}

}
