package com.example.demo.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.demo.model.Player;
import com.example.demo.model.Team;
import com.example.demo.repository.PlayerRepository;
import com.example.demo.repository.TeamRepository;

/*
 * In JUnit 5, the @RunWith annotation has been replaced by the more powerful @ExtendWith annotation. 
 * However, the @RunWith annotation can still be used in JUnit5 for the sake of the backward compatibility.
 * 
 * https://www.baeldung.com/junit-5-runwith
 * -------- 
 * 
 * If you are using Junit version < 5, so you have to use @RunWith(SpringRunner.class) or @RunWith(MockitoJUnitRunner.class) etc.
 * If you are using Junit version = 5, so you have to use @ExtendWith(SpringExtension.class) or @ExtendWith(MockitoExtension.class) etc.
 * 
 * OKTA Blog: 
 * https://developer.okta.com/blog/2019/03/28/test-java-spring-boot-junit5
 * 
 */

@ExtendWith(SpringExtension.class)
public class SoccerServiceImplTest {
	
	/* As you write a test that doesn't need any dependencies from the Spring Boot container, 
	 * the classic/plain Mockito is the way to follow : it is fast and favors the isolation of the tested component. 
	 * 
	 * If your test needs to rely on the Spring Boot container and you want also to 
	 * add or mock one of the container beans : @MockBean from Spring Boot is the way.
	 */

	// We didn't use @MockBean as there was no need to rely on the Spring Boot container 
	
	@InjectMocks
	private SoccerServiceImpl soccerServiceImpl;  //service to be testd
	
	@Mock
	private PlayerRepository playerRepository;
	
	@Mock
	private TeamRepository teamRepository;
	
	
	// https://github.com/eugenp/tutorials/blob/master/spring-boot-modules/spring-boot-testing/src/test/java/com/baeldung/boot/testing/EmployeeServiceImplIntegrationTest.java
	
	@BeforeEach
    public void setUp() {
        Player player = getDummyPlayer();
        Team team = getDummyTeam(1L, "A");
        List<Player> allPlayers = new ArrayList<>();
        allPlayers.add(getDummyPlayer());
        
        Mockito.when(playerRepository.findByTeamId(Mockito.anyLong())).thenReturn(allPlayers);
        Mockito.when(playerRepository.findByName(Mockito.anyString())).thenReturn(player);

        Mockito.when(teamRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(team));
    }
	
	@Test
	public void getPlayerByName_whenValidNameProvided() throws Exception {
		String name = "Dummy";
		Player player = soccerServiceImpl.getPlayerByName(name);
		
		assertThat(player.getName()).isEqualTo(name);
	}
	
	@Test // this test is failing
	public void getAllTeamPlayers() throws Exception {
		String name = "Dummy";
		List<Player> found_players = soccerServiceImpl.getAllTeamPlayers(1L);
		int size = found_players.size();
		assertThat(size).isEqualTo(1);
		assertThat(found_players.get(0).getName()).isEqualTo(name);
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
