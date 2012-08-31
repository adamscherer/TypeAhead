package com.typeahead.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.typeahead.entity.Account;


public interface AccountRepository extends AccountRepositoryExtension, PagingAndSortingRepository<Account, String> {

}
