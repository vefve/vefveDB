/**
 * 
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
import com.vefve.db.Configuration;
import com.vefve.db.exceptions.CreateNodeException;

/**
 * @author vefve
 *
 * Contains utilities for disk operations.
 */
public class DiskUtils {
	
	private static final Logger logger = LogManager.getLogger(DiskUtils.class);

	public Node readNodeFromDisk(String filePath) {
		
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

			e.printStackTrace();
			
		} catch (JsonMappingException e) {

			e.printStackTrace();
			
		} catch (IOException e) {

			e.printStackTrace();
		}
		return null;
	}

	public String writeNodeToDisk(Node node) throws CreateNodeException {

		String filePath;
		
		if (node.getFilePath() != null) {
			
			filePath = node.getFilePath();
			
		} else {
			
			String rootUUID = UUID.randomUUID().toString();
			
			try {
				File rootFile = new File(Configuration.PERSISTANT_STORAGE_PATH + rootUUID);
				
				rootFile.createNewFile();
				
				filePath = rootFile.getAbsolutePath();
				
				node.setFilePath(filePath);
				
			} catch (IOException e) {
				
				throw new CreateNodeException(Configuration.PERSISTANT_STORAGE_PATH + rootUUID);
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
