package com.typeahead.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

public class AccountContext {

	protected static final Logger logger = LoggerFactory.getLogger(AccountContext.class);

	StopWatch monitor = new StopWatch();

	public AccountContext() {
		this.monitor = new StopWatch();
		monitor.start();
	}

	public AccountContext(StopWatch monitor) {
		this.monitor = monitor;
		monitor.start();
	}

	public StopWatch getMonitor() {
		return monitor;
	}

	public void stop() {
		monitor.stop();
	}

	public long totalTime() {
		monitor.stop();

		return monitor.getTotalTimeMillis();
	}

}
