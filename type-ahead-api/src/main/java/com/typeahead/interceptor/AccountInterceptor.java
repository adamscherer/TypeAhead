package com.typeahead.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.annconia.api.util.RequestContextHolder;
import com.annconia.util.PerfLogger;
import com.annconia.util.PerfLogger.LogType;
import com.annconia.util.StringUtils;

public class AccountInterceptor extends HandlerInterceptorAdapter {

	private String apiKeyName = "apiKey";

	// inject the actual template 
	@Autowired
	private RedisTemplate<String, String> template;

	// inject the template as ListOperations
	@Autowired
	private ListOperations<String, String> listOps;

	@Autowired
	private StringRedisTemplate redisTemplate;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		AccountContext context = new AccountContext();
		AccountContextHolder.set(context);

		return true;
	}

	public void afterCompletion(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse, Object obj, Exception exception) throws Exception {

		long totalTime = AccountContextHolder.get().totalTime();
		PerfLogger.log(httpservletrequest.getRequestURI(), LogType.WEB, totalTime);
		AccountContextHolder.reset();
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

}
