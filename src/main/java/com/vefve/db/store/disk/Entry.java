/**
 * Persistent Storage implementation for VefveDB.
 */
package com.vefve.db.store.disk;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Structure of each Entry in the B-Tree.
 * 
 * @author vefve
 *
 */
// internal nodes: only use key and next
// external nodes: only use key and value
public class Entry implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Comparable key;
	
	private Object val;
	
	/*
	 * Helper field to iterate over array entries
	 */
	private String next;

	
	/*
	 * This is needed by Jackson to Deserialize.
	 */
	@JsonCreator
	public Entry() {

	}

	
	@JsonCreator
	public Entry(@JsonProperty("key") Comparable key, @JsonProperty("val") Object val,
			@JsonProperty("next") String next) {
		
		this.key = key;
		
		this.val = val;
		
		this.next = next;
		
	}

	
	/**
	 * @return the key
	 */
	public Comparable getKey() {
		
		return key;
		
	}

	
	/**
	 * @param key the key to set
	 */
	public void setKey(Comparable key) {
		
		this.key = key;
		
	}

	
	/**
	 * @return the val
	 */
	public Object getVal() {
		
		return val;
		
	}

	
	/**
	 * @param val the val to set
	 */
	public void setVal(Object val) {
		
		this.val = val;
		
	}

	
	/**
	 * @return the next
	 */
	public String getNext() {
		
		return next;
		
	}

	
	/**
	 * @param next the next to set
	 */
	public void setNext(String next) {
		
		this.next = next;
		
	}
	
}
