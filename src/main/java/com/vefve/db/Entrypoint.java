/**
 * VefveDB implementation
 */
package com.vefve.db;

import com.vefve.db.exceptions.CreateNodeException;
import com.vefve.db.exceptions.ReadNodeException;

/**
 * Entrypoint to debug the DB.
 * 
 * @author vefve
 *
 */
public class Entrypoint {

	/**
	 * @param args
	 * @throws CreateNodeException If unable to create a new file for the B-Tree node.
	 * @throws ReadNodeException If unable to read a file for the B-Tree node.
	 */
	public static void main(String[] args) throws CreateNodeException, ReadNodeException {
		
		VefveDB<String, String> vefveDB = new VefveDB<String, String>();

		vefveDB.put("www.cs.princeton.edu", "128.112.136.12");
		vefveDB.put("www.cs.princeton.edu", "128.112.136.11");
		vefveDB.put("www.princeton.edu", "128.112.128.15");
		vefveDB.put("www.yale.edu", "130.132.143.21");
		vefveDB.put("www.simpsons.com", "209.052.165.60");
		vefveDB.put("www.apple.com", "17.112.152.32");
		vefveDB.put("www.amazon.com", "207.171.182.16");
		vefveDB.put("www.ebay.com", "66.135.192.87");
		vefveDB.put("www.cnn.com", "64.236.16.20");
		vefveDB.put("www.google.com", "216.239.41.99");
		vefveDB.put("www.nytimes.com", "199.239.136.200");
		vefveDB.put("www.microsoft.com", "207.126.99.140");
		vefveDB.put("www.dell.com", "143.166.224.230");
		vefveDB.put("www.slashdot.org", "66.35.250.151");
		vefveDB.put("www.espn.com", "199.181.135.201");
		vefveDB.put("www.weather.com", "63.111.66.11");
		vefveDB.put("www.yahoo.com", "216.109.118.65");
		vefveDB.put("www.apple.com", null);

		System.out.println("cs.princeton.edu:  " + vefveDB.get("www.cs.princeton.edu"));
		System.out.println("gmail.com:         " + vefveDB.get("www.gmail.com"));
		System.out.println("simpsons.com:      " + vefveDB.get("www.simpsons.com"));
		System.out.println("apple.com:         " + vefveDB.get("www.apple.com"));
		System.out.println("ebay.com:          " + vefveDB.get("www.ebay.com"));
		System.out.println("dell.com:          " + vefveDB.get("www.dell.com"));
		System.out.println();

//		System.out.println("size:    " + vefveDB.size());
//		System.out.println("height:  " + vefveDB.height());
		System.out.println(vefveDB);
		System.out.println();
	}

}
