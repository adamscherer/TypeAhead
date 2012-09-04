package com.typeahead.repository.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.collections.trie.PatriciaTrie;
import org.apache.commons.collections.trie.StringKeyAnalyzer;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.ClassPathResource;

import cleo.search.Element;
import cleo.search.Indexer;
import cleo.search.MultiIndexer;
import cleo.search.collector.Collector;
import cleo.search.collector.SortedCollector;
import cleo.search.selector.ScoredElementSelectorFactory;
import cleo.search.tool.GenericTypeaheadInitializer;
import cleo.search.typeahead.GenericTypeahead;
import cleo.search.typeahead.GenericTypeaheadConfig;
import cleo.search.typeahead.MultiTypeahead;
import cleo.search.typeahead.Typeahead;
import cleo.search.typeahead.TypeaheadConfigFactory;

import com.typeahead.entity.TypeAheadResponse;

@SuppressWarnings("restriction")
public abstract class IndexRepositoryImpl<T extends Element> {

	private Indexer<T> indexer;

	private Typeahead<T> searcher;

	PatriciaTrie<String, T> trie = new PatriciaTrie<String, T>(new StringKeyAnalyzer());

	public abstract void load() throws Exception;

	public abstract String configPath();

	public abstract String name();

	@PostConstruct
	public void init() throws Exception {
		//initCleo();
		load();
	}

	public void initCleo() throws Exception {

		List<Indexer<T>> indexerList = new ArrayList<Indexer<T>>();

		List<Typeahead<T>> searcherList = new ArrayList<Typeahead<T>>();

		GenericTypeahead<T> gta = createTypeahead(new ClassPathResource(configPath()).getFile());
		indexerList.add(gta);
		searcherList.add(gta);

		indexer = new MultiIndexer<T>(name(), indexerList);

		searcher = new MultiTypeahead<T>(name(), searcherList);
	}

	public Indexer<T> getIndexer() {
		return indexer;
	}

	public Typeahead<T> getSearcher() {
		return searcher;
	}

	public PatriciaTrie<String, T> getTrie() {
		return trie;
	}

	public TypeAheadResponse<T> search(String query, int size) {
		return new TypeAheadResponse<T>(getTrie().getPrefixedBy(StringUtils.lowerCase(query)), size);
	}

	public TypeAheadResponse<T> searchTerms(String query, int size) {
		Collector<T> collector = new SortedCollector<T>(size, 100);
		collector = getSearcher().search(0, createTerms(query), collector);

		return new TypeAheadResponse<T>(collector.elements(), collector.size());
	}

	/**
	 * Creates a new GenericTypeahead based on its configuration file.
	 * 
	 * @param configFile
	 * @throws Exception
	 */
	protected GenericTypeahead<T> createTypeahead(File configFile) throws Exception {

		// Create typeahead config
		GenericTypeaheadConfig<T> config = TypeaheadConfigFactory.createGenericTypeaheadConfig(configFile);
		config.setSelectorFactory(new ScoredElementSelectorFactory<T>());

		// Create typeahead initializer
		GenericTypeaheadInitializer<T> initializer = new GenericTypeaheadInitializer<T>(config);

		return (GenericTypeahead<T>) initializer.getTypeahead();
	}

	protected String[] createTerms(String query) {
		return query.replaceAll("\\W+", " ").toLowerCase().split(" ");
	}

}
