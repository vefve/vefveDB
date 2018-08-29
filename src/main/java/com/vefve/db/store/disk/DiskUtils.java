/**
 * Persistent Storage implementation for VefveDB.
 */
package com.vefve.db.store.disk;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.vefve.db.exceptions.CreateNodeException;
import com.vefve.db.exceptions.ReadNodeException;

/**
 * Contains utilities for disk operations.
 * 
 * @author vefve
 */
public class DiskUtils {
	
	private String persistentStoragePath;
	
	private static final Logger logger = LogManager.getLogger(DiskUtils.class);

	
	public DiskUtils(String persistentStoragePath) {
		
		this.persistentStoragePath = persistentStoragePath;
		
	}
	
	/**
	 * Reads and deserializes a node specified by the {@code filePath}.
	 * 
	 * @param filePath File path of the node.
	 * 
	 * @return Deserialized node.
	 * 
	 * @throws ReadNodeException If unable to read a file for the B-Tree node.
	 */
	public Node readNodeFromDisk(String filePath) throws ReadNodeException {
		
		if (filePath == null) {
			
			return null;
			
		}
		
		try {
			
			File file = new File(filePath);
			
			ObjectMapper objectMapper = new ObjectMapper();
			
			SimpleModule module = new SimpleModule();
			
			module.addDeserializer(Entry.class, new EntryDeserializer());
			
			objectMapper.registerModule(module);
			
			return objectMapper.readValue(file, Node.class);
			
		} catch (JsonParseException e) {

			throw new ReadNodeException(filePath);
			
		} catch (JsonMappingException e) {

			throw new ReadNodeException(filePath);
			
		} catch (IOException e) {

			throw new ReadNodeException(filePath);
			
		}
		
	}

	
	/**
	 * Serializes and writes a node to the disk
	 * 
	 * @param node Node to save.
	 * 
	 * @return filePath File path of the node saved.
	 * 
	 * @throws CreateNodeException If unable to create a new file for the B-Tree node.
	 */
	public String writeNodeToDisk(Node node) throws CreateNodeException {

		String filePath;
		
		if (node.getFilePath() != null) {
			
			filePath = node.getFilePath();
			
		} else {
			
			String rootUUID = UUID.randomUUID().toString();
			
			try {
				File rootFile = new File(this.persistentStoragePath + rootUUID);
				
				rootFile.createNewFile();
				
				filePath = rootFile.getAbsolutePath();
				
				node.setFilePath(filePath);
				
			} catch (IOException e) {
				
				throw new CreateNodeException(this.persistentStoragePath + rootUUID);
			}
			
		}
		
		File file = new File(filePath);
		
		try {
			
			ObjectMapper objectMapper = new ObjectMapper();
			
			objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
			
			objectMapper.writeValue(file, node);
			
			return filePath;
			
		} catch (JsonGenerationException e) {
			
			e.printStackTrace();
			
			return null;
			
		} catch (JsonMappingException e) {

			e.printStackTrace();
			
			return null;
			
		} catch (IOException e) {
			
			e.printStackTrace();
			
			return null;
			
		}
		
	}
	
}
