package com.vefve.db.store.disk;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.vefve.db.exceptions.CreateNodeException;

class DiskStoreTest {

	@Test
	void testAddRemove() throws CreateNodeException {
		
		DiskStore<String, String> diskStore = new DiskStore<String, String>();
		
		diskStore.put("www.cs.princeton.edu", "128.112.136.11");
		
		diskStore.put("www.princeton.edu", "128.112.128.15");
		
		diskStore.put("www.yale.edu", "130.132.143.21");
		
		diskStore.put("www.apple.com", "17.112.152.32");
		
		diskStore.put("www.amazon.com", "207.171.182.16");
		
		diskStore.put("www.ebay.com", "66.135.192.87");
		
		
		Assert.assertEquals("128.112.136.11", diskStore.get("www.cs.princeton.edu"));
		
		Assert.assertEquals("128.112.128.15", diskStore.get("www.princeton.edu"));
		
		Assert.assertEquals("130.132.143.21", diskStore.get("www.yale.edu"));
		
		Assert.assertEquals("17.112.152.32", diskStore.get("www.apple.com"));
		
		Assert.assertEquals("207.171.182.16", diskStore.get("www.amazon.com"));
		
		Assert.assertEquals("66.135.192.87", diskStore.get("www.ebay.com"));
		
	}
	
	@Test
	void testDuplicate() throws CreateNodeException {
		
		DiskStore<String, String> diskStore = new DiskStore<String, String>();
		
		diskStore.put("www.cs.princeton.edu", "128.112.136.12");
		
		diskStore.put("www.cs.princeton.edu", "128.112.136.11");
		
		Assert.assertEquals("128.112.136.11", diskStore.get("www.cs.princeton.edu"));
		
	}

}
