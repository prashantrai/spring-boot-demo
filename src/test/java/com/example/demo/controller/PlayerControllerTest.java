package com.example.demo.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.demo.model.Player;
import com.example.demo.model.Team;
import com.example.demo.service.SoccerService;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

//@RunWith(SpringRunner.class) 
//@SpringBootTest
//@AutoConfigureMockMvc

@WebMvcTest(PlayerController.class)  // WebMvcTest annotation is used for unit testing Spring MVC application. This can be used when a test focuses only Spring MVC components. In this test, we want to launch only StudentController. All other controllers and mappings will not be launched when this unit test is executed.
public class PlayerControllerTest {
	
	@Autowired
	private MockMvc mockmvc;
	
	@MockBean 	// to provide mock implementations for any required dependencies.
	private SoccerService soccerService;
	
	
	/*@Before
    public void setUp() throws Exception {
    }*/
	
	@Test
	public void getAllPlayers() throws Exception {
		Player player = getDummyPlayer();
		//List<Player> allPlayers = (List<Player>)(Object) Arrays.asList(player1, palyer2); // https://stackoverflow.com/questions/1917844/how-to-cast-listobject-to-listmyclass

		//coz we have only one dummy player we directly put in a list
		List<Player> allPlayers = new ArrayList<>();
		allPlayers.add(player);
		
		Mockito.when(soccerService.getAllPlayers()).thenReturn(allPlayers);
		
		RequestBuilder reqBuilder = MockMvcRequestBuilders.get("/players")
															.accept(MediaType.APPLICATION_JSON);
		
		mockmvc.perform(reqBuilder)
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
		.andExpect(MockMvcResultMatchers.jsonPath("$[0].name", CoreMatchers.is(player.getName()))); // why $[0]? coz response is a list/array
		
		Mockito.verify(soccerService, VerificationModeFactory.times(1)).getAllPlayers(); //times(1) is default and can be omitted
	}
	
	@Test
	public void addPlayerToTeam() throws Exception {
		Player player = getDummyPlayer();
		Mockito.when(soccerService.addPlayerToTeam(Mockito.any())).thenReturn(player);
		
		ResultActions resultActions = mockmvc.perform(post("/players")
				.contentType(MediaType.APPLICATION_JSON)
				.content(toJson(player)));
		
		resultActions.andExpect(status().isCreated())
					 .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(player.getName())));
				
		Mockito.verify(soccerService).addPlayerToTeam(Mockito.any()); 
	}
	
	public static byte[] toJson(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }
	
	private Player getDummyPlayer() {
		Player player = new Player();
		player.setId(10001L);
		player.setName("Dummy");
		player.setNum(33);
		player.setPosition("Forward");
		player.setTeam(getDummyTeam(11L, "A"));
		
		return player;
	}
	
	private Team getDummyTeam(long id, String name) {
		Team team = new Team();
		team.setId(id);
		team.setName(name);
		return team;
	}
}