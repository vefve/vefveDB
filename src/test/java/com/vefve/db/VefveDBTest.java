package com.vefve.db;

import java.util.ArrayList;
import java.util.Random;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.vefve.db.exceptions.CreateNodeException;
import com.vefve.db.exceptions.ReadNodeException;

/**
 * @author vefve
 *
 */
public class VefveDBTest {

	@Test(threadPoolSize = 3, invocationCount = 3,  timeOut = 10000)
	void testMultiThreadedLoad() throws CreateNodeException, ReadNodeException {
		
		VefveDB<String, String> vefveDB = new VefveDB<String, String>();
		
        ArrayList<Integer> list = new ArrayList<Integer>();
        
        Random random = new Random();
        
        Integer randomEntry = 0;
        
        for (int i = 0; i <= 500; i++) {
        	
            randomEntry = random.nextInt(1000);
            
            list.add(randomEntry);
            
            vefveDB.put(randomEntry.toString(), randomEntry.toString());
            
        }

        Random r = new Random();
        
        for (int i = 0; i < 500; i++) {
        	
            Integer tmp = r.nextInt(1000);
            
            if (list.contains(tmp)) {
            	
                Assert.assertEquals(tmp.toString(), vefveDB.get(tmp.toString()));
                
            } else {
            	
                Assert.assertEquals(null, vefveDB.get(tmp.toString()));
                
            }
            
        }
        
	}

	
	@Test(expectedExceptions = IllegalArgumentException.class)
	void testInitializationError() throws CreateNodeException, IllegalArgumentException {
		
		VefveDB<String, String> vefveDB = new VefveDB<String, String>(true, null, 5L, 0.7f, 4);
		
	}
}
