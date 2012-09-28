package com.typeahead.controller.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.typeahead.controller.service.TypeAheadController;
import com.typeahead.repository.CityRepository;
import com.typeahead.repository.CountyRepository;
import com.typeahead.repository.StateRepository;

@Controller
public class MonitoringController extends TypeAheadController {

    @Autowired(required = false)
    private CityRepository cityRepository;

    @Autowired(required = false)
    private CountyRepository countyRepository;

    @Autowired(required = false)
    private StateRepository stateRepository;

    @RequestMapping(value = "/city", params = "q")
    @ResponseBody
    public ResponseEntity<Object> cityTerms(@RequestParam String q,
            @RequestParam(defaultValue = "50") int size) throws Exception {
        return new ResponseEntity<Object>(cityRepository.getCitiesByTerm(q, size), HttpStatus.OK);
    }

    @RequestMapping(value = "/county", params = "q")
    @ResponseBody
    public ResponseEntity<Object> countyTerms(@RequestParam String q,
            @RequestParam(defaultValue = "50") int size) throws Exception {
        return new ResponseEntity<Object>(countyRepository.getCountiesByTerm(q, size),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/state", params = "q")
    @ResponseBody
    public ResponseEntity<Object> stateTerms(@RequestParam String q,
            @RequestParam(defaultValue = "50") int size) throws Exception {
        return new ResponseEntity<Object>(stateRepository.getStatesByTerm(q, size), HttpStatus.OK);
    }
}
