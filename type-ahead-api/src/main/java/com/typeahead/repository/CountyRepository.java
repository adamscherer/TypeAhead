package com.typeahead.repository;

import com.typeahead.entity.County;
import com.typeahead.entity.TypeAheadResponse;

public interface CountyRepository extends IndexRepository {

    public TypeAheadResponse<County> getCountiesByTerm(String query, int size);

}
