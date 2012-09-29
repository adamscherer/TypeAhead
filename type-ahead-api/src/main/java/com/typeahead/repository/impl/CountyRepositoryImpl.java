package com.typeahead.repository.impl;

import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import com.annconia.api.json.JsonUtils;
import com.typeahead.entity.County;
import com.typeahead.entity.TypeAheadResponse;
import com.typeahead.repository.CountyRepository;

@Repository
public class CountyRepositoryImpl extends IndexRepositoryImpl<County> implements CountyRepository {

    @Override
    public int load() throws Exception {

        ClassPathResource resource = new ClassPathResource("/data/counties.json");
        List<String> lines = IOUtils.readLines(resource.getInputStream());
        for (String line : lines) {
            County value = JsonUtils.fromJson(line, County.class);
            loadIndex(value);
        }

        getIndexer().flush();
        return lines.size();
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

    public TypeAheadResponse<County> getCountiesByTerm(String query, int size) {
        return searchTerms(query, size);
    }

}
