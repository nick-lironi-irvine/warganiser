package org.warganiser.server.resources;

import java.util.ArrayList;
import java.util.List;

public class ListResourceWrapper<T> extends AbstractResourceWrapper<T>{
	
	private List<SingleResourceWrapper<T>> data = new ArrayList<>();

	public ListResourceWrapper(){
	}
	
	public ListResourceWrapper(String selfUrl){
		addLink(SELF, selfUrl);
	}
	
	public ListResourceWrapper(String selfUrl, Object... args){
		addLink(SELF, String.format(selfUrl, args));
	}

	public List<SingleResourceWrapper<T>> getData() {
		return data;
	}
	
	public AbstractResourceWrapper<T> addData(SingleResourceWrapper<T> data) {
		this.data.add(data);
		return this;
	}

}
