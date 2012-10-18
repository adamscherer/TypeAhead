package com.typeahead.repository;

import com.typeahead.entity.State;
import com.typeahead.entity.TypeAheadResponse;

public interface StateRepository extends IndexRepository<State> {

	public TypeAheadResponse<State> getStatesByTerm(String query, int size);

}
