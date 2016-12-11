package com.apmcore.restclient;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mmdh.Application;
import com.mmdh.models.Organization;
import com.mmdh.repos.OrganizationRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest
public class OrganizationTest {

	// Required to Generate JSON content from Java objects
	public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	// Required to delete the data added for tests.
	// Directly invoke the APIs interacting with the DB
	@Autowired
	private OrganizationRepository orgRepository;

	// Test RestTemplate to invoke the APIs.
	private RestTemplate restTemplate = new TestRestTemplate();

	@Test
	public void testGetBookDetailsApi() {
		// Make a call to the API to get details of the Organization
		Organization apiResponse = restTemplate.getForObject("http://localhost:8080/v1/organizations/1", Organization.class);

		// Verify that the data from the API and data saved in the DB are same
		assertNotNull(apiResponse);
		assertEquals("Proctor", apiResponse.getName());
	}

}