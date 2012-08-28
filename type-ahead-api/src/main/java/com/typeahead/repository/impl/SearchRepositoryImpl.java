package com.typeahead.repository.impl;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.collections.trie.PatriciaTrie;
import org.apache.commons.collections.trie.StringKeyAnalyzer;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import cleo.search.collector.Collector;
import cleo.search.collector.SortedCollector;

import com.annconia.api.json.JsonUtils;
import com.typeahead.entity.Hometown;
import com.typeahead.entity.TypeAheadResponse;
import com.typeahead.repository.SearchRepository;

@SuppressWarnings("restriction")
@Repository
public class SearchRepositoryImpl implements SearchRepository {

	PatriciaTrie<String, Hometown> CITIES_TRIE = new PatriciaTrie<String, Hometown>(new StringKeyAnalyzer());

	@PostConstruct
	public void init() throws Exception {
		ClassPathResource resource = new ClassPathResource("zips.json");
		List<String> lines = IOUtils.readLines(resource.getInputStream());
		for (String line : lines) {
			Hometown hometown = JsonUtils.fromJson(line, Hometown.class);
			String key = StringUtils.upperCase(hometown.getCity() + " " + hometown.getState());
			if (CITIES_TRIE.containsKey(key)) {
				Hometown existing = CITIES_TRIE.get(key);
				existing.setPop(existing.getPop() + hometown.getPop());
			} else {
				CITIES_TRIE.put(key, hometown);
			}
		}
	}

	public TypeAheadResponse getCities(String query, int size) {
		return new TypeAheadResponse(CITIES_TRIE.getPrefixedBy(StringUtils.upperCase(query)), size);
	}

}
