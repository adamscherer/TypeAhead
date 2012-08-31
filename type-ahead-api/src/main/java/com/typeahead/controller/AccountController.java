package com.typeahead.controller;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.annconia.api.controller.ApiPagingAndSortingController;
import com.typeahead.entity.Account;
import com.typeahead.repository.AccountRepository;

@SuppressWarnings("restriction")
@Controller
public class AccountController extends ApiPagingAndSortingController<Account, AccountRepository> {

	@Autowired(required = false)
	private AccountRepository repository;

	@PostConstruct
	public void init() {
		setRepository(repository);
	}

}
