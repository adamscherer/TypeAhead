package com.typeahead.repository;

import com.typeahead.entity.County;
import com.typeahead.entity.TypeAheadResponse;

public interface CountyRepository {

	public TypeAheadResponse<County> getCounties(String query, int size);

	public TypeAheadResponse<County> getCountiesByTerm(String query, int size);

}
