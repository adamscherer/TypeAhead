package com.typeahead.repository.impl;

import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import com.annconia.api.json.JsonUtils;
import com.typeahead.entity.State;
import com.typeahead.entity.TypeAheadResponse;
import com.typeahead.repository.StateRepository;

@Repository
public class StateRepositoryImpl extends IndexRepositoryImpl<State> implements StateRepository {

    @Override
    public int load() throws Exception {

        ClassPathResource resource = new ClassPathResource("/data/states.json");
        List<String> lines = IOUtils.readLines(resource.getInputStream());
        for (String line : lines) {
            State value = JsonUtils.fromJson(line, State.class);
            loadIndex(value);
        }

        getIndexer().flush();
        return lines.size();
    }

    public String configPath() {
        return "/search/states.config";
    }

    public String name() {
        return "States";
    }

    protected void loadIndex(State value) throws Exception {
        getIndexer().index(value);
    }

    public TypeAheadResponse<State> getStatesByTerm(String query, int size) {
        return searchTerms(query, size);
    }

}
