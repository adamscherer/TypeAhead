package com.typeahead.controller.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.junit.BeforeClass;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;
import org.springframework.web.servlet.DispatcherServlet;

import com.annconia.api.json.JsonUtils;

public abstract class AbstractControllerIntegrationTest {

	private static DispatcherServlet dispatcher;

	@BeforeClass
	public static void setDispatchServlet() throws Exception {
		MockServletConfig config = new MockServletConfig("integration");
		config.addInitParameter("contextConfigLocation", "classpath:type-ahead-api-servlet.xml, classpath:context/context.xml, classpath:context/mongo-context.xml, classpath:context/redis-context.xml");
		dispatcher = new DispatcherServlet();
		dispatcher.init(config);
	}

	protected MockHttpServletResponse handleRequest(HttpServletRequest request) {
		try {
			MockHttpServletResponse response = new MockHttpServletResponse();
			dispatcher.service(request, response);

			return response;
		} catch (Throwable ex) {
			throw new RuntimeException("Handle not successful: " + ex);
		}
	}

	protected MockHttpServletRequest createJsonRequest() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.setContentType(MediaType.APPLICATION_JSON_VALUE);
		request.addHeader("Accept", MediaType.APPLICATION_JSON_VALUE);

		return request;
	}

	protected Map<String, Object> toJson(MockHttpServletResponse response) throws Exception {
		Map<String, Object> json = JsonUtils.fromJsonToMap(response.getContentAsString());
		System.out.println("Json: " + json);

		return json;
	}

}
