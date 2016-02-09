package io.pivotal.demo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collections;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ErrorViewWithoutPrincipalApplication.class)
@WebIntegrationTest(randomPort = true)
public class ErrorViewWithoutPrincipalApplicationTests {

	RestTemplate template;

	String host;

	@Autowired
	public void setPort(@Value("${local.server.port}") int port) {
		this.host = "http://localhost:"+port;
	}



	@Test
	public void tryOpenWithoutCredentials() {
		template = new TestRestTemplate();

		ResponseEntity<String> response = template.getForEntity(host + "/open", String.class);
		assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
		System.out.println(response.getBody());
	}



	@Test
	public void tryOpenWithCredentials() {
		template = new TestRestTemplate("bob", "pwd");

		ResponseEntity<String> response = template.getForEntity(host + "/open", String.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		System.out.println(response.getBody());
		assertTrue(response.getBody().contains("bob"));
	}

	@Test
	public void tryCloseWithCredentialsButWithoutRequiredRole() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.TEXT_HTML));
		template = new TestRestTemplate("bob", "pwd");


		ResponseEntity<String> response = template.exchange(host + "/close", HttpMethod.GET, new HttpEntity<String>(headers), String.class);
		System.out.println(response.getBody());

		assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
		assertTrue(response.getBody().contains("bob"));
	}

}
