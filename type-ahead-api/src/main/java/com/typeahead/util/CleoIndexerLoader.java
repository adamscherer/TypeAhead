package com.typeahead.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.typeahead.repository.CityRepository;
import com.typeahead.repository.CountyRepository;
import com.typeahead.repository.IndexRepository;
import com.typeahead.repository.StateRepository;

/*
 * To run: I created a 'java app' run configuration in eclipse with the following params:
 * 
 * -Xms1g -Xmx1g
 * 
 * @author mrusso
 *
 */
public class CleoIndexerLoader {

    private final StateRepository stateRepository;
    private final CityRepository cityRepository;
    private final CountyRepository countyRepository;

    @Autowired
    public CleoIndexerLoader(@Qualifier StateRepository stateRepos,
            @Qualifier CityRepository cityRepos, @Qualifier CountyRepository countyRepos) {
        this.stateRepository = stateRepos;
        this.cityRepository = cityRepos;
        this.countyRepository = countyRepos;
    }

    protected void loadAllRepositoryData() {
        loadRepositoryData(stateRepository);
        loadRepositoryData(cityRepository);
        loadRepositoryData(countyRepository);
    }

    private void loadRepositoryData(IndexRepository repository) {
        try {
            System.out.println("Loading data for " + repository.getClass());
            int numItems = repository.load();
            System.out.println("Processed " + numItems + " elements");
        } catch (Exception ex) {
            System.out.println("Error caught when repository data");
            ex.printStackTrace();
        }
    }

    public static void main(String... args) {

        ApplicationContext context = new ClassPathXmlApplicationContext(
                new String[] { "context/data-load-context.xml" });

        CleoIndexerLoader indexLoader = new CleoIndexerLoader(
                context.getBean(StateRepository.class), context.getBean(CityRepository.class),
                context.getBean(CountyRepository.class));

        System.out.println("Context created...");
        indexLoader.loadAllRepositoryData();
    }
}
