/**
 * 
 */
package com.vefve.db.store.disk;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vefve.db.Configuration;

/**
 * @author vefve
 *
 */
// helper B-tree node data type
public final class Node implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int childrenCount; // number of children
	
	private Entry[] children = new Entry[Configuration.BRANCHING_FACTOR]; // the array of children
	
	private String filePath;

	
	// create a node with k children
	public Node(int k) {
		
		childrenCount = k;

		// Debug
		children[0] = new Entry(null, null, null);
	}

	
	@JsonCreator
	public Node(@JsonProperty("m") int m, @JsonProperty("children") Entry[] children,
			@JsonProperty("filePath") String filePath) {
		
		this.childrenCount = m;
		
		this.children = children;
		
		this.filePath = filePath;
		
	}
	

	/**
	 * @return the childrenCount
	 */
	public int getChildrenCount() {
		
		return childrenCount;
		
	}

	
	/**
	 * @param childrenCount the childrenCount to set
	 */
	public void setChildrenCount(int childrenCount) {
		
		this.childrenCount = childrenCount;
		
	}
	

	/**
	 * @return the children
	 */
	public Entry[] getChildren() {
		
		return children;
		
	}

	
	/**
	 * @param children the children to set
	 */
	public void setChildren(Entry[] children) {
		
		this.children = children;
		
	}

	
	/**
	 * @return the filePath
	 */
	public String getFilePath() {
		
		return filePath;
		
	}

	
	/**
	 * @param filePath the filePath to set
	 */
	public void setFilePath(String filePath) {
		
		this.filePath = filePath;
		
	}
	
}