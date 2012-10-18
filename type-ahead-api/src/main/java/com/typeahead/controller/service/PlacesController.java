package com.typeahead.controller.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.typeahead.repository.CityRepository;
import com.typeahead.repository.CountyRepository;
import com.typeahead.repository.IndexRepository;
import com.typeahead.repository.StateRepository;

@SuppressWarnings("restriction")
@Controller
public class PlacesController extends TypeAheadController {

	@Autowired(required = false)
	private CityRepository cityRepository;

	@Autowired(required = false)
	private CountyRepository countyRepository;

	@Autowired(required = false)
	private StateRepository stateRepository;

	private Map<String, IndexRepository<?>> repositories = new HashMap<String, IndexRepository<?>>();

	@PostConstruct
	public void init() {
		repositories.put("city", cityRepository);
		repositories.put("county", countyRepository);
		repositories.put("state", stateRepository);
	}

	// @CreditLimit
	@RequestMapping(value = "/{repo}", params = "q")
	@ResponseBody
	public ResponseEntity<Object> searcByTerms(@PathVariable("repo") String repo, @RequestParam String q, @RequestParam(defaultValue = "50") int size) throws Exception {
		return new ResponseEntity<Object>(repositories.get(repo).searchTerms(q, size), HttpStatus.OK);
	}

	@RequestMapping(value = "/{repo}/{id}")
	@ResponseBody
	public ResponseEntity<Object> getById(@PathVariable("repo") String repo, @PathVariable("id") int id) throws Exception {
		return new ResponseEntity<Object>(repositories.get(repo).getElement(id), HttpStatus.OK);
	}

}
