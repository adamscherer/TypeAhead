package com.typeahead.interceptor;

import com.annconia.api.ApiException;

public class CreditsRequiredException extends ApiException {

	private static final long serialVersionUID = 1L;

	public CreditsRequiredException(String msg) {
		super(msg);
	}

}
