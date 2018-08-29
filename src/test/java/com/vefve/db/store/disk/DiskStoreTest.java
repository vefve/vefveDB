package com.vefve.db.store.disk;

import java.util.UUID;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.vefve.db.exceptions.CreateNodeException;
import com.vefve.db.exceptions.ReadNodeException;

class DiskStoreTest {

	@Test
	void testAddRemove() throws CreateNodeException, ReadNodeException {

		ReentrantReadWriteLock diskStoreLock = new ReentrantReadWriteLock();
		
		DiskStore<String, String> diskStore = new DiskStore<String, String>(diskStoreLock, 4, "/tmp/data/");
		
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
	void testDuplicate() throws CreateNodeException, ReadNodeException {

		ReentrantReadWriteLock diskStoreLock = new ReentrantReadWriteLock();
		
		DiskStore<String, String> diskStore = new DiskStore<String, String>(diskStoreLock, 4, "/tmp/data/");
		
		diskStore.put("www.cs.princeton.edu", "128.112.136.12");
		
		diskStore.put("www.cs.princeton.edu", "128.112.136.11");
		
		Assert.assertEquals("128.112.136.11", diskStore.get("www.cs.princeton.edu"));
		
	}
	
	
	@Test(expectedExceptions = CreateNodeException.class)
	void testCreateNodeException() throws CreateNodeException, ReadNodeException {
		
		String uuid = UUID.randomUUID().toString();
		
		ReentrantReadWriteLock diskStoreLock = new ReentrantReadWriteLock();
		
		DiskStore<String, String> diskStore = new DiskStore<String, String>(diskStoreLock, 4, "/tmp/" + uuid + "/");
		
		diskStore.put(uuid, uuid);
	}
	
	@Test
	void testHeightEmpty() throws CreateNodeException, ReadNodeException {

		ReentrantReadWriteLock diskStoreLock = new ReentrantReadWriteLock();
		
		DiskStore<String, String> diskStore = new DiskStore<String, String>(diskStoreLock, 4, "/tmp/data/");
		
		Assert.assertEquals(true, diskStore.isEmpty());
		
		diskStore.put("www.cs.princeton.edu", "128.112.136.11");
		
		diskStore.put("www.princeton.edu", "128.112.128.15");
		
		diskStore.put("www.yale.edu", "130.132.143.21");
		
		diskStore.put("www.apple.com", "17.112.152.32");
		
		diskStore.put("www.amazon.com", "207.171.182.16");
		
		diskStore.put("www.ebay.com", "66.135.192.87");
		
		Assert.assertEquals(1, diskStore.height());
		
	}
	
}
