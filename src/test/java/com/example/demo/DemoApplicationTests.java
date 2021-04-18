package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.controller.PlayerController;


/**
 * https://spring.io/guides/gs/testing-web/
 * 
 * */

@SpringBootTest
class DemoApplicationTests {

	@Autowired
	private PlayerController playerController;
	
	@Test
	void contextLoads() {
		assertThat(playerController).isNotNull();	
	}

}
