package com.typeahead.repository.impl;

import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import com.annconia.api.json.JsonUtils;
import com.typeahead.entity.City;
import com.typeahead.entity.TypeAheadResponse;
import com.typeahead.repository.CityRepository;

@SuppressWarnings("restriction")
@Repository
public class CityRepositoryImpl extends IndexRepositoryImpl<City> implements CityRepository {

	@PostConstruct
	public void load() throws Exception {

		ClassPathResource resource = new ClassPathResource("zips.json");
		List<String> lines = IOUtils.readLines(resource.getInputStream());
		for (String line : lines) {
			City city = JsonUtils.fromJson(line, City.class);
			city.setTimestamp(System.currentTimeMillis());
			loadIndex(city);
			loadTrie(city);
		}

		getIndexer().flush();
	}

	private int id = 1;

	protected void loadIndex(City city) throws Exception {
		city.setElementId(id++);
		city.setTerms(createTerms(StringUtils.lowerCase(city.getCity())));
		getIndexer().index(city);
	}

	protected void loadTrie(City city) throws Exception {
		String key = StringUtils.lowerCase(city.getCity() + " " + city.getState());
		if (getTrie().containsKey(key)) {
			City existing = getTrie().get(key);
			existing.setPop(existing.getPop() + city.getPop());
		} else {
			getTrie().put(key, city);
		}
	}

	public TypeAheadResponse<City> getCities(String query, int size) {
		return search(query, size);
	}

	public TypeAheadResponse<City> getCitiesByTerm(String query, int size) {
		return searchTerms(query, size);
	}

}
