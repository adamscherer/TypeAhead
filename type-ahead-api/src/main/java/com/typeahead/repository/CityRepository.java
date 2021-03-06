package com.typeahead.repository;

import com.typeahead.entity.City;
import com.typeahead.entity.TypeAheadResponse;

public interface CityRepository extends IndexRepository<City> {

    public TypeAheadResponse<City> getCitiesByTerm(String query, int size);

}
