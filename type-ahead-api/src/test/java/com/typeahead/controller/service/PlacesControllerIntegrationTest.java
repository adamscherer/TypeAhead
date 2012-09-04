package com.typeahead.controller.service;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

public class PlacesControllerIntegrationTest extends AbstractControllerIntegrationTest {

	@Test
	public void getWithApiKey() throws Exception {
		MockHttpServletRequest request = createJsonRequest();
		request.setMethod("GET");
		request.setRequestURI("/county");
		request.addParameter("q", "T");

		MockHttpServletResponse response = handleRequest(request);

		Map<String, Object> json = toJson(response);
		Assert.assertEquals(200, response.getStatus());
	}

}
