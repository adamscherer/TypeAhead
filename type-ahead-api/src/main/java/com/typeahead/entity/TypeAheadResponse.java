package com.typeahead.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.annconia.util.CollectionUtils;

public class TypeAheadResponse<T> {

	private int total;
	private List<T> values;

	public TypeAheadResponse(Map<String, T> data) {
		this(data, 50);
	}

	public TypeAheadResponse(Map<String, T> data, int size) {
		this.total = data.size();
		this.values = CollectionUtils.safeSubList(new ArrayList<T>(data.values()), size);
	}
	
	public TypeAheadResponse(List<T> data, int size) {
		this.total = data.size();
		this.values = data;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<T> getValues() {
		return values;
	}

	public void setValues(List<T> values) {
		this.values = values;
	}

}
