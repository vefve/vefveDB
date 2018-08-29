package com.vefve.db.store.disk;

import java.util.UUID;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.vefve.db.exceptions.ReadNodeException;

/**
 * @author vefve
 *
 */
public class DiskUtilsTest {

	@Test(expectedExceptions = ReadNodeException.class)
	void testReadNodeFromDiskError() throws ReadNodeException {
		
		String uuid = UUID.randomUUID().toString();
		
		DiskUtils diskUtils = new DiskUtils("/tmp/data");
		
		try {
			
			Assert.assertNull(diskUtils.readNodeFromDisk(null));
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
		
		diskUtils.readNodeFromDisk(uuid);
		
	}
}
