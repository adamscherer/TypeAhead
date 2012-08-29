package com.typeahead.repository;

import com.typeahead.entity.State;
import com.typeahead.entity.TypeAheadResponse;

public interface StateRepository {

	public TypeAheadResponse<State> getStates(String query, int size);

	public TypeAheadResponse<State> getStatesByTerm(String query, int size);

}
