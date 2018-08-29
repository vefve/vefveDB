package com.vefve.db.store;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.vefve.db.exceptions.CreateNodeException;
import com.vefve.db.exceptions.ReadNodeException;
import com.vefve.db.store.disk.DiskStore;
import com.vefve.db.store.memory.MemoryStore;

/**
 * @author vefve
 *
 */
public class MemoryManagerTest {

	@Test
	void testMemoryManager() throws CreateNodeException, ReadNodeException {
		
		ConcurrentHashMap<String, String> concurrentHashMap = new ConcurrentHashMap<String, String>();
		
		ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
		
		MemoryStore<String, String> memStore = new MemoryStore<String, String>(concurrentHashMap, 4);
		
		DiskStore<String, String> diskStore = new DiskStore<String, String>(lock, 4, "/tmp/data/");
		
		MemoryManager<String, String> memManager = new MemoryManager<String, String>(memStore, diskStore);
		
		memStore.put("www.cs.princeton.edu", "128.112.136.11");
		
		memStore.put("www.princeton.edu", "128.112.128.15");
		
		memStore.put("www.yale.edu", "130.132.143.21");
		
		memStore.put("www.apple.com", "17.112.152.32");
		
		Assert.assertEquals(4, memStore.size());
		
		Assert.assertEquals(0, diskStore.size());
		
		memManager.backupToDisk();
		
		Assert.assertEquals(0, memStore.size());
		
		Assert.assertEquals(4, diskStore.size());
	}
}
