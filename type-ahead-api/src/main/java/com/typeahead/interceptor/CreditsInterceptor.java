package com.typeahead.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.annconia.api.util.MvcUtils;
import com.annconia.api.util.RequestContextHolder;
import com.annconia.util.StringUtils;
import com.typeahead.repository.redis.RedisRepository;

public class CreditsInterceptor extends HandlerInterceptorAdapter {

	private String apiKeyName = "apiKey";

	// inject the actual template 
	@Autowired
	private RedisRepository repository;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		if (isCreditRequired(handler)) {
			repository.validateCredits(getApiKey(), 1000);
		}

		return true;
	}

	private String getApiKey() {
		String sessionKey = RequestContextHolder.get().getParameter(apiKeyName);
		if (StringUtils.isNotEmpty(sessionKey)) {
			return sessionKey;
		}

		sessionKey = RequestContextHolder.get().getHeader(apiKeyName);
		if (StringUtils.isNotEmpty(sessionKey)) {
			return sessionKey;
		}

		return RequestContextHolder.get().getCookieValue(apiKeyName);

	}

	protected boolean isCreditRequired(Object handler) {
		return MvcUtils.findAnnotationOnMethodOrController(handler, CreditLimit.class) != null;
	}

}
