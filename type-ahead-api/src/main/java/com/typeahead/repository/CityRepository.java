package com.typeahead.repository;

import com.typeahead.entity.City;
import com.typeahead.entity.TypeAheadResponse;

public interface CityRepository {

	public TypeAheadResponse<City> getCities(String query, int size);

	public TypeAheadResponse<City> getCitiesByTerm(String query, int size);

}
