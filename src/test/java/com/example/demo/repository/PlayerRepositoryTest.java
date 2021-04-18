package com.example.demo.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.demo.model.Player;
import com.example.demo.model.Team;

/*
 * References:  
 * https://howtodoinjava.com/spring-boot2/testing/datajpatest-annotation/
 * https://www.baeldung.com/spring-boot-testing

 @DataJpaTest provides some standard setup needed for testing the persistence layer:
	configuring H2, an in-memory database
	setting Hibernate, Spring Data, and the DataSource
	performing an @EntityScan
	turning on SQL logging
 * */
@DataJpaTest  //Jnit 5, When using JUnit 4, this annotation should be used in combination with @RunWith(SpringRunner.class)  
public class PlayerRepositoryTest {

	@Autowired
	private PlayerRepository playerRepository;
	
	@Autowired
	private TeamRepository teamRepository;  
	
	/* TestEntityManager is an alternative to the standard JPA EntityManager 
	 * that provides methods commonly used when writing tests.*/
	//@Autowired
    //private TestEntityManager entityManager; // not used in this test class 
	
	
	@BeforeEach
	public void setup() {
		Team team = getDummyTeam(1L, "A");
		teamRepository.save(team);
	}
	
	/* Add new player */
	@Test
	public void testRepository_Save() { // https://www.codejava.net/frameworks/spring-boot/junit-tests-for-spring-data-jpa
		
//		Team team = getDummyTeam(1L, "A");  // moved to setup method
//		teamRepository.save(team);
		
		Player player = getDummyPlayer();
		playerRepository.save(player);
		
//		entityManager.persist(player);
//	    entityManager.flush();
	    
	    // when
	    Player found = playerRepository.findByName(player.getName());

	    Assertions.assertNotNull(found.getName());
	    
	    assertThat(found.getName()).isEqualTo(player.getName());
	}
	
	@Test
	public void whenFindByName_thenReturnPlayer() {
	    // given
//		Team team = getDummyTeam(1L, "A");  // moved to setup method
//		teamRepository.save(team);
		
		Player player = getDummyPlayer();
		playerRepository.save(player);
	    //entityManager.persist(player);
	    //entityManager.flush();
		//Player player_persisted = entityManager.persistAndFlush(player);

	    // when
	    Player found = playerRepository.findByName(player.getName());

	    // then
	    assertThat(found.getName())
	      .isEqualTo(player.getName());
	    
	    Assertions.assertEquals(found.getName(), player.getName());
	}
	
	
	// Utils
	private Player getDummyPlayer() {
		Player player = new Player();
		player.setId(10001L);
		player.setName("Dummy");
		player.setNum(33);
		player.setPosition("Forward");
		player.setTeam(getDummyTeam(1L, "A"));
		
		return player;
	}
	
	private Team getDummyTeam(long id, String name) {
		Team team = new Team();
		team.setId(id);
		team.setName(name);
		return team;
	}
	
}