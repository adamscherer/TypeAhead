package com.typeahead.repository.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.core.io.ClassPathResource;

import cleo.search.Indexer;
import cleo.search.MultiIndexer;
import cleo.search.SimpleElement;
import cleo.search.collector.Collector;
import cleo.search.collector.SortedCollector;
import cleo.search.selector.ScoredElementSelectorFactory;
import cleo.search.store.ArrayStoreElement;
import cleo.search.store.MultiArrayStoreElement;
import cleo.search.tool.GenericTypeaheadInitializer;
import cleo.search.typeahead.GenericTypeahead;
import cleo.search.typeahead.GenericTypeaheadConfig;
import cleo.search.typeahead.MultiTypeahead;
import cleo.search.typeahead.Typeahead;
import cleo.search.typeahead.TypeaheadConfigFactory;

import com.typeahead.entity.TypeAheadResponse;

@SuppressWarnings("restriction")
public abstract class IndexRepositoryImpl<T extends SimpleElement> {

	private Indexer<T> indexer;

	private Typeahead<T> searcher;

	private ArrayStoreElement<T> elementStore;

	public abstract int load() throws Exception;

	public abstract String configPath();

	public abstract String name();

	@PostConstruct
	public void init() throws Exception {
		initCleo();
	}

	public void initCleo() throws Exception {

		List<Indexer<T>> indexerList = new ArrayList<Indexer<T>>();
		List<Typeahead<T>> searcherList = new ArrayList<Typeahead<T>>();
		List<ArrayStoreElement<T>> storeList = new ArrayList<ArrayStoreElement<T>>();

		GenericTypeahead<T> gta = createTypeahead(new ClassPathResource(configPath()).getFile());
		indexerList.add(gta);
		searcherList.add(gta);
		storeList.add(gta.getElementStore());

		indexer = new MultiIndexer<T>(name(), indexerList);
		searcher = new MultiTypeahead<T>(name(), searcherList);
		elementStore = new MultiArrayStoreElement<T>(storeList);
	}

	public Indexer<T> getIndexer() {
		return indexer;
	}

	public Typeahead<T> getSearcher() {
		return searcher;
	}
	
	public final ArrayStoreElement<T> getElementStore() {
        return elementStore;
    }

	public List<T> getElements() {
		ArrayStoreElement<T> store = getElementStore();
		List<T> list = new ArrayList<T>(store.capacity());

		int start = store.getIndexStart();
		int end = start + store.length();
		for (int i = start; i < end; i++) {
			T element = getElementStore().getElement(i);
			if (element != null) {
				list.add(element);
			}
		}

		return list;
	}

	public T getElement(int elementId) {
		T element = getElementStore().getElement(elementId);
		if (element != null) {
			return element;
		} else {
			return null;
		}
	}

	public T deleteElement(int elementId) throws Exception {
		T oldElement = getElementStore().getElement(elementId);

		if (oldElement != null) {
			SimpleElement newElement = (SimpleElement) oldElement.clone();
			newElement.setTerms(new String[0]);
			//getIndexer().index(newElement);
		}

		if (oldElement != null) {
			return oldElement;
		} else {
			return null;
		}
	}

	public T updateElement(T element) throws Exception {
		T oldElement = getElementStore().getElement(element.getElementId());
		getIndexer().index(element);

		if (oldElement != null) {
			return oldElement;
		} else {
			return null;
		}
	}

	public boolean insertElement(T element) throws Exception {
		int elementId = element.getElementId();
		if (getElementStore().hasIndex(elementId)) {
			return getIndexer().index(element);
		}

		return false;
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
