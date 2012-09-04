package com.typeahead.controller.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.annconia.api.ApiException;
import com.annconia.api.JsonView;
import com.annconia.api.json.JsonErrorResponse;

/**
 * Handles all exceptions from the API package.
 * 
 * @author Adam Scherer
 *
 */
public class ServiceExceptionCatcher implements HandlerExceptionResolver {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * From HandlerExceptionResolver interface. Catches HANDLER exceptions.
	 */
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		logger.error("API exception", ex);

		ModelAndView mv = new ModelAndView();

		if (ex instanceof ApiException) {
			mv.setView(new JsonView(new JsonErrorResponse(((ApiException) ex).getMessageCode())));
			return mv;
		}

		mv.setView(new JsonView(new JsonErrorResponse("general.error")));

		return mv;
	}

}
