package com.example.demo;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;


/* 
 * You should also write some tests that assert the behavior of your application. 
 * 
 * To do that, you could start the application and listen for a connection (as it would do in production) 
 * and then send an HTTP request and assert the response. 
 * 
 * The following listing (from src/test/java/com/example/testingweb/HttpRequestTest.java) shows how to do so:
 */

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class HttpRequestTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void greetingShouldReturnDefaultMessage() throws Exception {
		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/",
				String.class)).contains("Hello!");
	}
	
	// It's not really requred if you have test cases for controllers using MockMvc
	//This class is not tested, avoid this in interview
	
	@Test
	public void greetingShouldReturnPalyersList() throws Exception {
		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/players",
				ResponseEntity.class)).isNotNull();
	}
}


