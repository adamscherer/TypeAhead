package com.typeahead.interceptor;

public class CreditsRequiredException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CreditsRequiredException() {
		super();
	}

	public CreditsRequiredException(String msg) {
		super(msg);
	}

}
