package org.warganiser.server.resources;

import java.util.ArrayList;
import java.util.List;

public class ListResourceWrapper<T> extends AbstractResourceWrapper<T>{
	
	private List<SingleResourceWrapper<T>> data;

	public ListResourceWrapper(String selfUrl){
		super();
		this.data = new ArrayList<>();
		addLink(SELF, selfUrl);
	}

	public List<SingleResourceWrapper<T>> getData() {
		return data;
	}
	
	public AbstractResourceWrapper<T> addData(SingleResourceWrapper<T> data) {
		this.data.add(data);
		return this;
	}

}
