package com.typeahead.controller.app;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.annconia.api.controller.ApiPagingAndSortingController;
import com.annconia.security.DefaultSecurityManager;
import com.annconia.security.entity.PasswordUtility;
import com.annconia.security.entity.SecurityUser;
import com.annconia.util.GuidGenerator;
import com.typeahead.entity.Account;
import com.typeahead.repository.AccountRepository;

@SuppressWarnings("restriction")
@Controller
@RequestMapping("/account")
public class AccountController extends ApiPagingAndSortingController<Account, AccountRepository> {

	@Autowired(required = false)
	private AccountRepository repository;

	@Autowired(required = false)
	private DefaultSecurityManager manager;

	@PostConstruct
	public void init() {
		setRepository(repository);
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Object> register(@RequestParam String email, @RequestParam String password, @RequestParam String confirmPassword) throws Exception {

		SecurityUser user = new SecurityUser(email, PasswordUtility.encodePassword(password));
		manager.createUser(user);

		Account account = new Account();
		account.setApiKey(GuidGenerator.generate());
		account.setOwnerId(user.getId());
		repository.save(account);

		return new ResponseEntity<Object>(account, HttpStatus.OK);
	}

	@RequestMapping(value = "/reset", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Object> resetApiKey() throws Exception {

		String apiKey = GuidGenerator.generate();

		return new ResponseEntity<Object>(apiKey, HttpStatus.OK);
	}

	@RequestMapping(value = "/forgot", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Object> forgot(@RequestParam String email) throws Exception {

		String apiKey = GuidGenerator.generate();

		return new ResponseEntity<Object>(apiKey, HttpStatus.OK);
	}

}
