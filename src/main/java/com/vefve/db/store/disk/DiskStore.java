package com.vefve.db.store.disk;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.vefve.db.Configuration;
import com.vefve.db.store.Store;
import com.vefve.db.store.memory.MemoryStore;
import com.vefve.db.utils.Utils;


/**
 * DiskStore uses a B-Tree to store the Key-Value pairs on the disk.
 * @author vefve
 *
 * @param <K>
 * @param <V>
 */
public class DiskStore<K extends Serializable & Comparable<K>, V extends Serializable> implements Store<K, V> {
	// max children per B-tree node = Branching Factor - 1

	private static final Logger logger = LogManager.getLogger(MemoryStore.class);
	
	/*
	 * Root of the B-Tree. Stores the absolute path to the root node file.
	 */
	private String root; // root of the B-tree
	
	/*
	 * Height of the B-Tree.
	 */
	private int height;
	
	/*
	 * Number of Key-Value pairs in the B-Tree.
	 */
	private int n;
	
	private DiskUtils diskUtils;

	
	/**
	 * Initializes an empty B-tree.
	 */
	public DiskStore() {
		diskUtils = new DiskUtils();
		
		root = diskUtils.writeNodeToDisk(new Node(0));
		
	}



	/**
	 * Returns true if this symbol table is empty.
	 * 
	 * @return {@code true} if this symbol table is empty; {@code false} otherwise
	 */
	public boolean isEmpty() {
		
		return size() == 0;
		
	}

	/**
	 * Returns the number of key-value pairs in this symbol table.
	 * 
	 * @return the number of key-value pairs in this symbol table
	 */
	public int size() {
		
		return n;
		
	}

	/**
	 * Returns the height of this B-tree (for debugging).
	 *
	 * @return the height of this B-tree
	 */
	public int height() {
		
		return height;
		
	}

	/**
	 * Returns the value associated with the given key.
	 *
	 * @param key
	 *            the key
	 * @return the value associated with the given key if the key is in the symbol
	 *         table and {@code null} if the key is not in the symbol table
	 * @throws IllegalArgumentException
	 *             if {@code key} is {@code null}
	 */
	public V get(K key) {
		
		if (key == null) {
			
			logger.info("Key cannot be null.");
			
		}
		
		V v = search(root, key, height);
		
		return v;
	}

	@SuppressWarnings("unchecked")
	private V search(String nodePath, K key, int height) {
		
		Node node = diskUtils.readNodeFromDisk(nodePath);
		
		Entry[] children = node.getChildren();

		// External node
		if (height == 0) {
			
			for (int j = 0; j < node.getChildrenCount(); j++) {
				
				if (Utils.eq(key, children[j].getKey())) {
					
					return (V) children[j].getVal();
					
				}
				
			}
			
		}

		// Internal node
		else {
			
			for (int j = 0; j < node.getChildrenCount(); j++) {
				
				if (j + 1 == node.getChildrenCount() || Utils.less(key, children[j + 1].getKey())) {
					
					// TODO: See if we can remove reference for "node" here so that it can be
					// garbage collected.
					return search(children[j].getNext(), key, height - 1);
					
				}
				
			}
			
		}
		
		return null;
		
	}

	/**
	 * Inserts the key-value pair into the symbol table, overwriting the old value
	 * with the new value if the key is already in the symbol table. If the value is
	 * {@code null}, this effectively deletes the key from the symbol table.
	 *
	 * @param key
	 *            the key
	 * @param val
	 *            the value
	 * @return 
	 * @throws IllegalArgumentException
	 *             if {@code key} is {@code null}
	 */
	public boolean put(K key, V val) {
		
		if (key == null) {
			
			//TODO: Add logging.
			return false;
			
		}
		
		Node _root = diskUtils.readNodeFromDisk(root);
		
		String u = insert(_root, key, val, height);
		
		n++;
		
		if (u == null) {
			
			return true;
			
		}

		/*
		 * Need to split the root.
		 */
		Node newNode = new Node(2);
		
		newNode.getChildren()[0] = new Entry(_root.getChildren()[0].getKey(), null, root);
		
		newNode.getChildren()[1] = new Entry(diskUtils.readNodeFromDisk(u).getChildren()[0].getKey(), null, u);
		
		String newNodePath = diskUtils.writeNodeToDisk(newNode);
		
		root = newNodePath;
		
		height++;
		
		return true;
		
	}

	
	private String insert(Node root, K key, V value, int height) {
		
		int j;
		
		Entry newEntry = new Entry(key, value, null);

		// External node
		if (height == 0) {
			
			for (j = 0; j < root.getChildrenCount(); j++) {
				
				if (Utils.less(key, root.getChildren()[j].getKey()) || Utils.eq(key, root.getChildren()[j].getKey())) {
					
					break;
					
				}
				
			}
			
		}

		// Internal node
		else {
			
			for (j = 0; j < root.getChildrenCount(); j++) {
				
				if ((j + 1 == root.getChildrenCount()) || Utils.less(key, root.getChildren()[j + 1].getKey())) {
					
					String u = insert(diskUtils.readNodeFromDisk(root.getChildren()[j++].getNext()), key, value, height - 1);
					
					if (u == null) {
						
						return null;
						
					}
					
					newEntry.setKey(diskUtils.readNodeFromDisk(u).getChildren()[0].getKey());
					
					newEntry.setNext(u);
					
					break;
					
				}
				
			}
			
		}

		if (root.getChildren()[j] != null && Utils.eq(key, root.getChildren()[j].getKey())) {
			
			root.getChildren()[j] = newEntry;
			
		} else {
			
			for (int i = root.getChildrenCount(); i > j; i--)
				
				root.getChildren()[i] = root.getChildren()[i - 1];
			
			root.getChildren()[j] = newEntry;
			
			root.setChildrenCount(root.getChildrenCount() + 1);
			
		}
		if (root.getChildrenCount() < Configuration.BRANCHING_FACTOR) {
			
			diskUtils.writeNodeToDisk(root);
			
			return null;
			
		} else {
			
			return split(root);
			
		}
		
	}

	
	/*
	 * Split the node in half.
	 */
	private String split(Node root) {
		
		Node newNode = new Node(Configuration.BRANCHING_FACTOR / 2);
		
		root.setChildrenCount(Configuration.BRANCHING_FACTOR / 2);
		
		for (int j = 0; j < Configuration.BRANCHING_FACTOR / 2; j++) {
			
			newNode.getChildren()[j] = root.getChildren()[Configuration.BRANCHING_FACTOR / 2 + j];
			
		}
		
		diskUtils.writeNodeToDisk(root);
		
		return diskUtils.writeNodeToDisk(newNode);
		
	}
	
	
	/**
	 * Returns a string representation of this B-tree (for debugging).
	 *
	 * @return a string representation of this B-tree.
	 */
	public String toString() {
		
		return toString(root, height, "") + "\n";
		
	}

	
	private String toString(String rootPath, int ht, String indent) {
		
		Node h = diskUtils.readNodeFromDisk(rootPath);
		
		StringBuilder s = new StringBuilder();
		
		Entry[] children = h.getChildren();

		if (ht == 0) {
			
			for (int j = 0; j < h.getChildrenCount(); j++) {
				
				s.append(indent + children[j].getKey() + " " + children[j].getVal() + "\n");
				
			}
			
		} else {
			
			for (int j = 0; j < h.getChildrenCount(); j++) {
				
				if (j > 0)
					
					s.append(indent + "(" + children[j].getKey() + ")\n");
				
				s.append(toString(children[j].getNext(), ht - 1, indent + "     "));
				
			}
			
		}
		
		return s.toString();
		
	}

}

