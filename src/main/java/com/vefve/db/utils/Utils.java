package com.vefve.db.utils;

public class Utils {

	public static boolean less(Comparable k1, Comparable k2) {

		return k1.compareTo(k2) < 0;

	}

	
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
