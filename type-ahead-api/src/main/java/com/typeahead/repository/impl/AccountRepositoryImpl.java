package com.typeahead.repository.impl;

import org.springframework.stereotype.Repository;

import com.typeahead.repository.AccountRepositoryExtension;

@Repository
public class AccountRepositoryImpl implements AccountRepositoryExtension {

	@Override
	public int getRemainingCredits() {
		return 0;
	}

}