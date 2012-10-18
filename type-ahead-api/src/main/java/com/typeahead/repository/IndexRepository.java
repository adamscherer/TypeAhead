package com.typeahead.repository;

import java.util.List;

import com.typeahead.entity.TypeAheadResponse;

import cleo.search.SimpleElement;

public interface IndexRepository<T extends SimpleElement> {
    public int load() throws Exception;
    public List<T> getElements();
    public T getElement(int elementId);
    public T deleteElement(int elementId) throws Exception;
    public T updateElement(T element) throws Exception;
    public boolean insertElement(T element) throws Exception;
    public TypeAheadResponse<T> searchTerms(String query, int size);
}
