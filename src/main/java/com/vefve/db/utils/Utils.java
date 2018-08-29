/**
 * Utilities for VefveDB.
 */
package com.vefve.db.utils;

public class Utils {

	/**
	 * Checks if k1 is less than k2.
	 * 
	 * @param k1 Key to compare. Needs to be comparable.
	 * @param k2 Key to compare. Needs to be comparable.
	 * @return {@code true} if {@code k1 < k2} else {@code false}.
	 */
	public static boolean less(Comparable k1, Comparable k2) {

		return k1.compareTo(k2) < 0;

	}

	
	/**
	 * Checks if k1 is equal to k2.
	 * @param k1 Key to compare. Needs to be comparable.
	 * @param k2 Key to compare. Needs to be comparable.
	 * @return {@code true} if {@code k1 == k2} else {@code false}
	 */
	public static boolean eq(Comparable k1, Comparable k2) {

		if (k1 == null && k2 == null) {

			return true;

		}

		if (k1 == null || k2 == null) {

			return false;

		}

		return k1.compareTo(k2) == 0;

	}

}
