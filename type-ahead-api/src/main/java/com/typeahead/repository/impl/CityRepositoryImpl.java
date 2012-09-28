package com.typeahead.repository.impl;

import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import com.annconia.api.json.JsonUtils;
import com.typeahead.entity.City;
import com.typeahead.entity.TypeAheadResponse;
import com.typeahead.repository.CityRepository;

@Repository
public class CityRepositoryImpl extends IndexRepositoryImpl<City> implements CityRepository {

    public void load() throws Exception {

        ClassPathResource resource = new ClassPathResource("/data/cities.json");
        List<String> lines = IOUtils.readLines(resource.getInputStream());
        for (String line : lines) {
            City city = JsonUtils.fromJson(line, City.class);
            loadIndex(city);
        }
        getIndexer().flush();
    }

    public String configPath() {
        return "/search/cities.config";
    }

    public String name() {
        return "Cities";
    }

    protected void loadIndex(City city) throws Exception {
        getIndexer().index(city);
    }

    public TypeAheadResponse<City> getCitiesByTerm(String query, int size) {
        return searchTerms(query, size);
    }

}
