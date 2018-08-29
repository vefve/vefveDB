/**
 * 
 */
package com.vefve.db.store.memory;

import java.util.concurrent.ConcurrentHashMap;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.vefve.db.exceptions.CreateNodeException;
import com.vefve.db.store.disk.DiskStore;

/**
 * @author vefve
 *
 */
public class MemoryStoreTest {

	@Test
	void testAddRemoveFull() {
		
		ConcurrentHashMap<String, String> concurrentMap = new ConcurrentHashMap<String, String>();
		
		MemoryStore<String, String> memoryStore = new MemoryStore<String, String>(concurrentMap);
		
		memoryStore.put("www.cs.princeton.edu", "128.112.136.11");
		
		memoryStore.put("www.princeton.edu", "128.112.128.15");
		
		memoryStore.put("www.yale.edu", "130.132.143.21");
		
		memoryStore.put("www.apple.com", "17.112.152.32");
		
		memoryStore.put("www.amazon.com", "207.171.182.16");
		
		memoryStore.put("www.ebay.com", "66.135.192.87");
		
		
		Assert.assertEquals("128.112.136.11", memoryStore.get("www.cs.princeton.edu"));
		
		Assert.assertEquals("128.112.128.15", memoryStore.get("www.princeton.edu"));
		
		Assert.assertEquals("130.132.143.21", memoryStore.get("www.yale.edu"));
		
		Assert.assertEquals("17.112.152.32", memoryStore.get("www.apple.com"));
		
		Assert.assertEquals("207.171.182.16", memoryStore.get("www.amazon.com"));

		Assert.assertEquals(null, memoryStore.get("www.ebay.com"));
		
	}
	
	@Test
	void testDuplicate() throws CreateNodeException {
		
		DiskStore<String, String> diskStore = new DiskStore<String, String>();
		
		diskStore.put("www.cs.princeton.edu", "128.112.136.12");
		
		diskStore.put("www.cs.princeton.edu", "128.112.136.11");
		
		Assert.assertEquals("128.112.136.11", diskStore.get("www.cs.princeton.edu"));
		
	}
	
}
