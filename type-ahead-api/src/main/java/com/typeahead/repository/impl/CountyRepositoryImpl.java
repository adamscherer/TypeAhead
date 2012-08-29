package com.typeahead.repository.impl;

import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import com.annconia.api.json.JsonUtils;
import com.typeahead.entity.County;
import com.typeahead.entity.TypeAheadResponse;
import com.typeahead.repository.CountyRepository;

@Repository
public class CountyRepositoryImpl extends IndexRepositoryImpl<County> implements CountyRepository {

	@Override
	public void load() throws Exception {

		ClassPathResource resource = new ClassPathResource("/data/counties.json");
		List<String> lines = IOUtils.readLines(resource.getInputStream());
		for (String line : lines) {
			County value = JsonUtils.fromJson(line, County.class);
			loadIndex(value);
			loadTrie(value);
		}

		getIndexer().flush();
	}

	public String configPath() {
		return "/search/counties.config";
	}

	public String name() {
		return "Counties";
	}

	protected void loadIndex(County value) throws Exception {
		getIndexer().index(value);
	}

	protected void loadTrie(County value) throws Exception {
		getTrie().put(StringUtils.lowerCase(value.getState()) + ", " + StringUtils.lowerCase(value.getState()), value);
	}

	public TypeAheadResponse<County> getCounties(String query, int size) {
		return search(query, size);
	}

	public TypeAheadResponse<County> getCountiesByTerm(String query, int size) {
		return searchTerms(query, size);
	}

}
