/**
 * In-Memory implementation for VefveDB.
 */
package com.vefve.db.store.memory;

import java.util.concurrent.ConcurrentHashMap;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.vefve.db.exceptions.CreateNodeException;

/**
 * Tests for MemoryStore.
 * 
 * @author vefve
 *
 */
public class MemoryStoreTest {
	
	private MemoryStore<String, String> memoryStore;

	@BeforeTest
	void prepare() {
		
		ConcurrentHashMap<String, String> concurrentMap = new ConcurrentHashMap<String, String>();
		
		this.memoryStore = new MemoryStore<String, String>(concurrentMap, 5);
	}
	
	@Test
	void testAddRemoveFull() {
		
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
		
		memoryStore.put("www.cs.princeton.edu", "128.112.136.12");
		
		memoryStore.put("www.cs.princeton.edu", "128.112.136.11");
		
		Assert.assertEquals("128.112.136.11", memoryStore.get("www.cs.princeton.edu"));
		
	}
	
	
	@Test
	void testContainsKey() {
		
		memoryStore.put("www.cs.princeton.edu", "128.112.136.12");
		
		Assert.assertEquals(true, memoryStore.containsKey("www.cs.princeton.edu"));
		
	}
	
	
	@Test
	void testRemove() {
		
		memoryStore.put("www.cs.princeton.edu", "128.112.136.12");
		
		memoryStore.remove("www.cs.princeton.edu");
		
		Assert.assertEquals(false, memoryStore.containsKey("www.cs.princeton.edu"));
		
	}
	
}
