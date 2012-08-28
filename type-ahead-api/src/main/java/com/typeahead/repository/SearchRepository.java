package com.typeahead.repository;

import com.typeahead.entity.TypeAheadResponse;

public interface SearchRepository {

	public TypeAheadResponse getCities(String query, int size);
	
}
