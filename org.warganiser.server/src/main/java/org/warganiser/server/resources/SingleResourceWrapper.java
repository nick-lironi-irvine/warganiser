package org.warganiser.server.resources;

public class SingleResourceWrapper<T> extends AbstractResourceWrapper<T> {
	
	private T data;

	public SingleResourceWrapper(){
		this(null);	
	}

	public SingleResourceWrapper(final T data){
		super();
		this.data = data;
	}

	public T getData() {
		return data;
	}
}
