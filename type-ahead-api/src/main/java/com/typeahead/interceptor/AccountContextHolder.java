package com.typeahead.interceptor;

import org.springframework.core.NamedThreadLocal;

public abstract class AccountContextHolder {

	private static final ThreadLocal<AccountContext> contextHolder = new NamedThreadLocal<AccountContext>("Performance context");

	public static AccountContext get() {
		return contextHolder.get();
	}

	public static void set(AccountContext context) {
		contextHolder.set(context);
	}

	public static void reset() {
		contextHolder.remove();
	}
}
