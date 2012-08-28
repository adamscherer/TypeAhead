package com.typeahead.repository;

import cleo.search.TypeaheadElement;

import com.typeahead.entity.TypeAheadResponse;

public interface CityRepository {

	public TypeAheadResponse<TypeaheadElement> getCities(String query, int size);
	
}
