/**
 * VefveDB implementation
 */
package com.vefve.db;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.vefve.db.exceptions.CreateNodeException;
import com.vefve.db.exceptions.ReadNodeException;
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
	
	public boolean usePersistentStorage;
	
	public String persistentStoragePath;
	
	public long memoryStorageSize;
	
	public float loadFactorThreshold;
	
	public int branchingFactor; 
	
	
	/**
	 * @throws CreateNodeException If unable to create a new file for the B-Tree node.
	 */
	public VefveDB() throws CreateNodeException {
		
		this(true, "/tmp/data/", 5L, 0.7f, 4);
		
	}
	
	
	/**
	 * Constructor to initialize {@code VefveDB}
	 * 
	 * @param usePersistentStorage boolean Whether to use persistent storage or not.
	 * 
	 * @param persistentStoragePath String Location to store the data in.
	 * 
	 * @throws CreateNodeException If unable to create a new file for the B-Tree node.
	 */
	public VefveDB(boolean usePersistentStorage, String persistentStoragePath) throws CreateNodeException {
		
		this(usePersistentStorage, persistentStoragePath, 5L, 0.7f, 4);
		
	}
	
	
	/**
	 * Constructor to initialize {@code VefveDB}
	 * 
	 * @param usePersistentStorage boolean Whether to use persistent storage or not.
	 * 
	 * @param persistentStoragePath String Location to store the data in.
	 * 
	 * @param memoryStorageSize long Max size of the Memory store.
	 * 
	 * @throws CreateNodeException If unable to create a new file for the B-Tree node.
	 */
	public VefveDB(boolean usePersistentStorage, String persistentStoragePath, long memoryStorageSize) throws CreateNodeException {
		
		this(usePersistentStorage, persistentStoragePath, memoryStorageSize, 0.7f, 4);
		
	}
	
	
	/**
	 * Constructor to initialize {@code VefveDB}
	 * 
	 * @param usePersistentStorage boolean Whether to use persistent storage or not.
	 * 
	 * @param persistentStoragePath String Location to store the data in.
	 * 
	 * @param memoryStorageSize long Max size of the Memory store.
	 * 
	 * @param loadFactorThreshold float Ranges between 0 and 1. The load factor after which the memory store is moved to disk store.
	 * 
	 * @throws CreateNodeException If unable to create a new file for the B-Tree node.
	 */
	public VefveDB(boolean usePersistentStorage, String persistentStoragePath, long memoryStorageSize, float loadFactorThreshold) throws CreateNodeException {
		
		this(usePersistentStorage, persistentStoragePath, memoryStorageSize, loadFactorThreshold, 4);
	
	}
	
	
	/**
	 * Constructor to initialize {@code VefveDB}
	 * 
	 * @param usePersistentStorage {@code boolean} Whether to use persistent storage or not.
	 * 
	 * @param persistentStoragePath {@code String} Location to store the data in.
	 * 
	 * @param memoryStorageSize {@code long} Max size of the Memory store.
	 * 
	 * @param loadFactorThreshold {@code float} Ranges between 0 and 1. The load factor after which the memory store is moved to disk store.
	 * 
	 * @param branchingFactor {@code int} Constraint: Must be even and greater than 2. Max children per B-tree node = {@code branchingFactor - 1 }
	 * 
	 * @throws CreateNodeException If unable to create a new file for the B-Tree node.
	 */
	public VefveDB(boolean usePersistentStorage, String persistentStoragePath, long memoryStorageSize, float loadFactorThreshold, int branchingFactor) throws CreateNodeException {
		
		this.usePersistentStorage = usePersistentStorage;
		
		this.persistentStoragePath = persistentStoragePath;
		
		this.memoryStorageSize = memoryStorageSize;
		
		this.loadFactorThreshold = loadFactorThreshold;
		
		this.branchingFactor = branchingFactor;
		
		this.memoryStore = new MemoryStore<K, V>(new ConcurrentHashMap<K, V>(), this.memoryStorageSize);
		
		if (this.usePersistentStorage) {
			
			if (persistentStoragePath == null || branchingFactor <= 2 || branchingFactor % 2 != 0) {
				
				throw new IllegalArgumentException();
				
			}
			
			this.diskStoreLock = new ReentrantReadWriteLock();
			
			this.diskStore = new DiskStore<K, V>(this.diskStoreLock, this.branchingFactor, this.persistentStoragePath);
			
		}
	
	}

	
	/**
	 * Returns the value corresponding to the given key, if present in the DB Store.
	 * 
	 * @param key Key to search in the DB.
	 * 
	 * @return Returns the value corresponding to the given key, if present in the DB Store.
	 * 
	 * @throws ReadNodeException If unable to read a file for the B-Tree node.
	 */
	public V get(K key) throws ReadNodeException {
		
		V returnValue = this.memoryStore.get(key);
		
		if (this.usePersistentStorage && returnValue == null) {

			returnValue = this.diskStore.get(key);
		}
		
		return returnValue;
	}

	
	/**
	 * Insert a Key-Value pair in the DB Store.
	 * 
	 * @param key Key to be inserted.
	 * 
	 * @param value Value to be inserted.
	 * 
	 * @throws CreateNodeException If unable to create a new file for the B-Tree node.
	 * 
	 * @throws ReadNodeException If unable to read a file for the B-Tree node.
	 */
	public void put(K key, V value) throws CreateNodeException, ReadNodeException {

		if (key == null || value == null) {
			
			return;
			
		}
		
		while(true) {
			
			synchronized(this) {
				
				if (this.usePersistentStorage && this.memoryStore.getLoadFactor() >= this.loadFactorThreshold) {
					
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
		
		if (this.usePersistentStorage) {
			
			stringRepresentation += "Disk Store: " + this.diskStore.toString();
			
		}
		
		return stringRepresentation;
		
	}

}
