package org.warganiser.server.resources;

import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * A wrapper class for all DTOs that provides a common location and methods for adding HATEOAS style links
 */
public class AbstractResourceWrapper<T> {

	public static final String SELF = "self";
	public static final String PARENT = "parent";
	public static final String CREATE = "create";
	
	@JsonProperty(value = "_links")
	protected Map<String,Link> links;

	public AbstractResourceWrapper() {
		super();
		this.links = new HashMap<>();
	}

	@JsonIgnore
	public Map<String,Link> getLinks() {
		return links;
	}

	public AbstractResourceWrapper<T> addLink(String name, Link link) {
		this.links.put(name, link);
		return this;
	}
	
	public AbstractResourceWrapper<T> addLink(String name, String url) {
		this.links.put(name, new Link(url));
		return this;
	}
	
	public AbstractResourceWrapper<T> addLink(String name, String url, Object... params) {
		this.links.put(name, new Link(url, params));
		return this;
	}

}