package com.typeahead.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.annconia.api.controller.ApiController;
import com.typeahead.repository.CityRepository;
import com.typeahead.repository.SearchRepository;

@Controller
@RequestMapping("/search")
public class SearchController extends ApiController {

	@Autowired(required = false)
	private SearchRepository repository;
	
	@Autowired(required = false)
	private CityRepository cityRepository;

	@RequestMapping(value = "/city", params = "q")
	@ResponseBody
	public ResponseEntity<Object> hometown(@RequestParam String q, @RequestParam(defaultValue = "50") int size) throws Exception {
		return new ResponseEntity<Object>(repository.getCities(q, size), HttpStatus.OK);
	}

	@RequestMapping(value = "/city2", params = "q")
	@ResponseBody
	public ResponseEntity<Object> cities(@RequestParam String q, @RequestParam(defaultValue = "50") int size) throws Exception {
		return new ResponseEntity<Object>(cityRepository.getCities(q, size), HttpStatus.OK);
	}
	
}
