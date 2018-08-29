package com.vefve.db.utils;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author vefve
 *
 */
public class UtilsTest {

	@Test
	void testEq() {
		
		Assert.assertEquals(true, Utils.eq("str1", "str1"));
		
		Assert.assertEquals(false, Utils.eq("str1", "str2"));
		
		Assert.assertEquals(true, Utils.eq(null, null));
		
		Assert.assertEquals(false, Utils.eq("str1", null));
		
	}
	
	
	@Test
	void testLess() {
		
		Assert.assertEquals(false, Utils.less("str1", "str1"));
		
		Assert.assertEquals(true, Utils.less("str1", "str2"));
		
		Assert.assertEquals(false, Utils.less(null, null));
		
		Assert.assertEquals(false, Utils.less("str1", null));
		
	}
	
}
