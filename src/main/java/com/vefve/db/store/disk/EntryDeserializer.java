/**
 * 
 */
package com.vefve.db.store.disk;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

/**
 * @author vefve
 *
 */
public class EntryDeserializer extends StdDeserializer<Entry> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EntryDeserializer() {
		this(null);
	}

	public EntryDeserializer(Class<?> vc) {
		super(vc);
	}

	@Override
	public Entry deserialize(JsonParser jp, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		JsonNode node = jp.getCodec().readTree(jp);
		Comparable key = node.get("key").asText();
		//TODO: Handle this as Object. See if binary serialization helps.
		Object val = node.get("val").asText();
		String next = node.get("next").asText();
		return new Entry(key, val, next.equals("null") ? null : next.toString());
	}
}