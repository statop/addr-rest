package com.statop;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.BDDAssertions.then;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = AddrRestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"management.port=0"})
public class AddrRestApplicationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Test
	public void addressEndpointWorks() throws Exception {
		@SuppressWarnings("rawtypes")
		ResponseEntity<Map> entity = this.testRestTemplate.getForEntity(
				"http://localhost:" + this.port + "/address/44.4647452/7.3553838", Map.class);

		then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
		then(entity.getBody().get("address")).isNotNull();
	}

	@Test
	public void addressEndpointHandlesNonExistingLatLong() throws Exception {
		@SuppressWarnings("rawtypes")
		ResponseEntity<Map> entity = this.testRestTemplate.getForEntity(
				"http://localhost:" + this.port + "/address/44.4647452/4564564", Map.class);

		then(entity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		then(entity.getBody().get("code")).isEqualTo(404);
	}

	@Test
	public void last10EndpointWorks() throws Exception {
		@SuppressWarnings("rawtypes")
		ResponseEntity<Map[]> entity = this.testRestTemplate.getForEntity(
				"http://localhost:" + this.port + "/last10Addresses", Map[].class);

		then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

}
