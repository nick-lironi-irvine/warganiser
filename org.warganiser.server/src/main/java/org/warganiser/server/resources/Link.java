package org.warganiser.server.resources;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Represents JSON-API style HATEOAS links
 */
public class Link {

	private String url;
	private Map<String, Object> meta;

	public Link(final String url){
		this(url, new HashMap<>());
	}

	public Link(final String url, final Map<String,Object> meta){
		this.url = url;
		this.meta = meta;
	}

	public Link(final String url, final Object... args){
		this(String.format(url,args));
	}

	public Link addMeta(String key, Object value){
		this.meta.put(key, value);
		return this;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Map<String, Object> getMeta() {
		return meta;
	}

	public void setMeta(Map<String, Object> meta) {
		this.meta = meta;
	}

	/**
	 * Conditionally format the response depending on the content of the meta attribute
	 */
	@JsonValue
	@JsonRawValue
	public String toJson() throws IOException{
		StringWriter writer = new StringWriter();
		JsonGenerator jsonGenerator = new JsonFactory().createGenerator(writer).setCodec(new ObjectMapper());
		if (meta.isEmpty()) {
			jsonGenerator.writeString(url);
		} else {
			jsonGenerator.writeStartObject();
			jsonGenerator.writeStringField("href",url);
			jsonGenerator.writeObjectField("meta", meta);
			jsonGenerator.writeEndObject();
		}
		jsonGenerator.close();
		return writer.toString();
	}
}
